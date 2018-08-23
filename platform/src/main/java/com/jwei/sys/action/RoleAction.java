package com.jwei.sys.action;

import com.jwei.sys.entity.SessionUser;
import com.jwei.sys.entity.SysRole;
import com.jwei.sys.service.RoleSV;
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
 * Created by wyb on 2018/6/4.
 */
@RestController
@RequestMapping("/sys/role")
public class RoleAction extends BaseAction {
    @Resource
    private RoleSV roleSV;

    @RequestMapping("/saveRole.do")
    public Map<String,Object> saveRole(@RequestBody SysRole info){
        Map<String,Object> resultMap = new HashMap<>();
        SessionUser user = getSessionUser();
        try {
            if(StringUtils.isBlank(info.getName())){
                throw new ExceptionAPI("参数不能为空");
            }
            roleSV.saveRole(info,user);
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

    @RequestMapping("/getRoleByPage.do")
    public Map<String,Object> getRoleByPage(String name,int onePageNum,int pageNo){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            int start = onePageNum*(pageNo-1)+1;
            int end = onePageNum*pageNo;
            Map dataMap = roleSV.getRoleByPage(name,start,end);
            resultMap.put("flag","Y");
            resultMap.put("data",toJsonString(dataMap));

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/getRoleByCond.do")
    public Map<String,Object> getRoleByCond(String name){
        Map<String,Object> resultMap = new HashMap<>();
        try{

            List<SysRole> list = roleSV.getRoleByCond(name);
            resultMap.put("flag","Y");
            resultMap.put("data",toJsonString(list));

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/getRoleById.do")
    public Map<String,Object> getRoleById(String id){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(StringUtils.isBlank(id)){
                throw new ExceptionAPI("参数不能为空");
            }
            SysRole role = roleSV.getRoleById(id);
            resultMap.put("flag","Y");
            resultMap.put("data",toJsonString(role));

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/doDeleteRole.do")
    public Map<String,Object> doDeleteRole(String id){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(StringUtils.isBlank(id)){
                throw new ExceptionAPI("参数不能为空");
            }
            roleSV.deleteRole(id);
            resultMap.put("flag","Y");
            resultMap.put("data","success");

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

}
