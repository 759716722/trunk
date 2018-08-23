package com.jwei.sys.action;

import com.jwei.sys.entity.SysMenu;
import com.jwei.sys.entity.SysRoleMenu;
import com.jwei.sys.service.RoleMenuSV;
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
@RequestMapping("/sys/roleMenu")
public class RoleMenuAction extends BaseAction {

    @Resource
    private RoleMenuSV roleMenuSV;

    @RequestMapping("/createRoleMenu.do")
    public Map<String,Object> createRoleMenu(@RequestBody List<SysRoleMenu> list,String roleId){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            if(StringUtils.isBlank(roleId)){
                throw new ExceptionAPI("角色参数不能为空");
            }
            for(SysRoleMenu one : list){
                if(one.getRoleId()==null || one.getMenuId()==null){
                    throw new ExceptionAPI("关键参数不能为空");
                }
            }
            roleMenuSV.saveRoleMenuByRoleId(list,roleId);
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

    @RequestMapping("/getMenuByRoleId.do")
    public Map<String,Object> getMenuByRoleId(String roleId){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(StringUtils.isBlank(roleId)){
                throw new ExceptionAPI("参数不能为空");
            }
            List<SysMenu> list = roleMenuSV.getMenuByRoleId(roleId);
            resultMap.put("flag","Y");
            resultMap.put("data",toJsonString(list));

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }


}
