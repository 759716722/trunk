package com.wyb.sys.service.impl;

import com.wyb.sys.dao.SysUserMapper;
import com.wyb.sys.dao.SysUserRoleMapper;
import com.wyb.sys.entity.SessionUser;
import com.wyb.sys.entity.SysUser;
import com.wyb.sys.service.UserSV;
import com.wyb.utils.ExceptionAPI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wyb on 2017/12/25.
 */
@Service
public class UserSVImpl implements UserSV {

    @Resource
    private SysUserMapper userMapper;
    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Override
    public void saveUser(SysUser info,SessionUser user) throws Exception {
        if(info==null){
            throw new ExceptionAPI("保存信息不能为空");
        }
        SysUser oldUser = null;
        if(StringUtils.isNotBlank(info.getName())){
            oldUser = userMapper.selectByUserName(info.getName());
        }
        if(info.getId()==null || info.getId()<1){
            if(oldUser!=null){
                throw new ExceptionAPI("该账号已存在，请核查。");
            }
            info.setCreateId(user.getId());
            info.setCreateName(user.getLoginName());
            info.setCreateDate(new Date());
            userMapper.insert(info);
        }else{
            SysUser adminUser = userMapper.selectByPrimaryKey(info.getId());
            if("admin".equals(adminUser.getName())){
                throw new ExceptionAPI("管理员账号，不允许修改。");
            }
            if(oldUser!=null && !(info.getId().equals((oldUser.getId())))){
                throw new ExceptionAPI("该账号已存在，请核查。");
            }
            info.setModifyId(user.getId());
            info.setModifyName(user.getLoginName());
            info.setModifyDate(new Date());
            userMapper.updateByPrimaryKeySelective(info);
        }
    }

    @Override
    public Map getUserByPage(String name, String loginName,String state, int start, int end) throws Exception {
        List list = userMapper.selectByPage(name,loginName,state,start,end);
        int count = userMapper.countByPage(name,loginName,state);
        Map resultMap = new HashMap();
        resultMap.put("dataList",list);
        resultMap.put("dataCount",count);
        return resultMap;
    }

    @Override
    public List<SysUser> getUserByOrCond(String searchParam) throws Exception {
        return userMapper.selectByOrCond(searchParam);
    }

    @Override
    public void deleteUser(String id) throws Exception {
        SysUser user = getUserById(id);
        if(user == null){
            throw new ExceptionAPI("用户不存在");
        }
        if("admin".equals(user.getName())){
            throw new ExceptionAPI("管理员不能删除");
        }
        userMapper.deleteByPrimaryKey(Long.parseLong(id));
        userRoleMapper.deleteByUserId(Long.parseLong(id));
    }

    @Override
    public SysUser getUserById(String id) throws Exception {
        return userMapper.selectByPrimaryKey(Long.parseLong(id));
    }

    @Override
    public SysUser getUserByAccount(String userName) throws Exception {
        return userMapper.selectByUserName(userName);
    }
}
