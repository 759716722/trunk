package com.jwei.rad.action;

import com.jwei.rad.entity.RadWordbook;
import com.jwei.rad.service.WordbookSV;
import com.jwei.sys.entity.SessionUser;
import com.jwei.utils.BaseAction;
import com.jwei.utils.ExceptionAPI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wyb on 2018/6/26.
 */
@RestController
@RequestMapping("/rad/wordbook")
public class WordbookAction extends BaseAction{
    @Resource
    private WordbookSV wordbookSV;

    @RequestMapping("/saveWordbook.do")
    public Map<String,Object> saveWordbook(@RequestBody RadWordbook info){
        Map<String,Object> resultMap = new HashMap<>();
        SessionUser user = getSessionUser();
        try{
            if(info.getType()==null || info.getName()==null){
                throw new ExceptionAPI("关键参数不能为空");
            }
            wordbookSV.saveWordbook(info,user);
            resultMap.put("flag","Y");
            resultMap.put("data","success");

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/saveWordbookSort.do")
    public Map<String,Object> saveWordbookSort(@RequestBody List<RadWordbook> list){
        Map<String,Object> resultMap = new HashMap<>();
        SessionUser user = getSessionUser();
        try{
            if(list==null || list.isEmpty()){
                throw new ExceptionAPI("List列表不能为空");
            }
            for(RadWordbook one :list){
                if(one.getId()==null || one.getSeq()==null){
                    throw new ExceptionAPI("关键参数不能为空");
                }
            }
            wordbookSV.updateWordbook(list);
            resultMap.put("flag","Y");
            resultMap.put("data","success");

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/getWordbookByPage.do")
    public Map<String,Object> getWordbookByPage(Byte type,String name,int onePageNum,int pageNo){
        Map<String,Object> resultMap = new HashMap<>();
        try{

            if(StringUtils.isBlank(name)){
                name = null;
            }
            int start = onePageNum*(pageNo-1)+1;
            int end = onePageNum*pageNo;

            Map dataMap = wordbookSV.getWordbookByPage(type,name,start,end);

            resultMap.put("flag","Y");
            resultMap.put("data",toJsonString(dataMap));

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/getWordbookByCond.do")
    public Map<String,Object> getWordbookByCond(Byte type,String name){
        Map<String,Object> resultMap = new HashMap<>();
        try{

            if(StringUtils.isBlank(name)){
                name = null;
            }
            List<RadWordbook> list = wordbookSV.getWordbookByCond(type,name);

            resultMap.put("flag","Y");
            resultMap.put("data",toJsonString(list));

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/getWordbookById.do")
    public Map<String,Object> getWordbookById(String id){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(StringUtils.isBlank(id)){
                throw new ExceptionAPI("参数不能为空");
            }
            RadWordbook data = wordbookSV.getWordbookById(id);
            resultMap.put("flag","Y");
            resultMap.put("data",toJsonString(data));

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/doDeleteWordbook.do")
    public Map<String,Object> doDeleteWordbook(String id){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(StringUtils.isBlank(id)){
                throw new ExceptionAPI("参数不能为空");
            }
            wordbookSV.deleteWordbook(id);
            resultMap.put("flag","Y");
            resultMap.put("data","success");

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }


}
