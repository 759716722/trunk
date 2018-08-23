package com.jwei.rad.service.impl;

import com.jwei.rad.dao.RadProductDetailMapper;
import com.jwei.rad.dao.RadTypeConfigMapper;
import com.jwei.rad.dao.RadWordbookMapper;
import com.jwei.rad.entity.RadWordbook;
import com.jwei.rad.service.WordbookSV;
import com.jwei.sys.entity.SessionUser;
import com.jwei.utils.Constant;
import com.jwei.utils.ExceptionAPI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wyb on 2018/6/26.
 */
@Service
public class WordbookSVImpl implements WordbookSV {
    @Resource
    private RadWordbookMapper wordbookMapper;
    @Resource
    private RadTypeConfigMapper typeConfigMapper;
    @Resource
    private RadProductDetailMapper detailMapper;

    @Override
    public void saveWordbook(RadWordbook info, SessionUser user) throws Exception {
        if(info==null){
            throw new ExceptionAPI("保存信息不能为空");
        }
        RadWordbook old = wordbookMapper.selectByTypeAndName(info.getType(),info.getName());
        if(info.getId()==null || info.getId()<1){
            if(old!=null){
                throw new ExceptionAPI("该记录已存在，请核查。");
            }
            info.setCreateId(user.getId());
            info.setCreateName(user.getLoginName());
            info.setCreateDate(new Date());
            wordbookMapper.insert(info);
        }else{
            if(old!=null && !info.getId().equals(old.getId())){
                throw new ExceptionAPI("该记录已存在，请核查。");
            }
            info.setModifyId(user.getId());
            info.setModifyName(user.getLoginName());
            info.setModifyDate(new Date());
            wordbookMapper.updateByPrimaryKeySelective(info);
        }
    }

    @Override
    public void updateWordbook(List<RadWordbook> list) throws Exception {
        wordbookMapper.batchUpdateById(list);
    }

    @Override
    public Map getWordbookByPage(Byte type, String name, int start, int end) throws Exception {
        List dataList = wordbookMapper.selectByPage(type, name, start, end);
        int dataCount = wordbookMapper.countByPage(type, name);
        Map dataMap = new HashMap();
        dataMap.put("dataList",dataList);
        dataMap.put("dataCount",dataCount);
        return dataMap;
    }

    @Override
    public List<RadWordbook> getWordbookByCond(Byte type, String name) throws Exception {
        return wordbookMapper.selectByCond(type, name);
    }

    @Override
    public RadWordbook getWordbookById(String id) throws Exception {
        return wordbookMapper.selectByPrimaryKey(Integer.parseInt(id));
    }

    @Override
    public void deleteWordbook(String id) throws Exception {
        RadWordbook book = wordbookMapper.selectByPrimaryKey(Integer.valueOf(id));

        if(Constant.Rad_Wordbook_Config.equals(book.getType())){
            typeConfigMapper.deleteByConfigId(book.getId());
            detailMapper.deleteByConfigId(book.getId());
        }if(Constant.Rad_Wordbook_Type.equals(book.getType())){
            typeConfigMapper.deleteByTypeId(book.getId());
        }

        wordbookMapper.deleteByPrimaryKey(book.getId());
    }
}
