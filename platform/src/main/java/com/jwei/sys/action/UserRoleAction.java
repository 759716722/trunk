package com.jwei.sys.action;

import com.jwei.sys.entity.SysUserRole;
import com.jwei.sys.service.UserRoleSV;
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
 * Created by wyb on 2018/6/12.
 */
@RestController
@RequestMapping("/sys/userRole")
public class UserRoleAction extends BaseAction {
    @Resource
    private UserRoleSV userRoleSV;

    @RequestMapping("/createUserRole.do")
    public Map<String,Object> createUserRole(@RequestBody SysUserRole info){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            if(info == null || info.getUserId()==null || info.getRoleId()==null){
                throw new ExceptionAPI("参数不能为空");
            }
            userRoleSV.saveUserRole(info);
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

    @RequestMapping("/getUserByRoleId.do")
    public Map<String,Object> getUserByRoleId(String roleId){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(StringUtils.isBlank(roleId)){
                throw new ExceptionAPI("参数不能为空");
            }
            List list = userRoleSV.getUserByRoleId(roleId);
            resultMap.put("flag","Y");
            resultMap.put("data",toJsonString(list));

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/doDeleteUserRole.do")
    public Map<String,Object> doDeleteUserRole(String id){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(StringUtils.isBlank(id)){
                throw new ExceptionAPI("参数不能为空");
            }
            userRoleSV.deleteUserRole(id);
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
