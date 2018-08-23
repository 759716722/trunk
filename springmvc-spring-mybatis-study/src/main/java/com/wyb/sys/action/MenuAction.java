package com.wyb.sys.action;

import com.wyb.sys.entity.SessionUser;
import com.wyb.sys.entity.SysMenu;
import com.wyb.sys.service.MenuSV;
import com.wyb.utils.BaseAction;
import com.wyb.utils.ExceptionAPI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wyb on 2018/6/7.
 */
@RestController
@RequestMapping("/sys/menu")
public class MenuAction extends BaseAction {
    @Resource
    private MenuSV menuSV;

    @RequestMapping("/createMenu.do")
    public Map<String,Object> saveMenu(@RequestBody List<SysMenu> list){
        Map<String,Object> resultMap = new HashMap<>();
        SessionUser user = getSessionUser();
        try {
            if(list == null || list.size()<1){
                throw new ExceptionAPI("参数不能为空");
            }
            menuSV.saveMenu(list,user);
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

    @RequestMapping("/getMenuByPid.do")
    public Map<String,Object> getMenuByPid(String pid){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(StringUtils.isBlank(pid)){
                throw new ExceptionAPI("参数不能为空");
            }
            List<SysMenu> list = menuSV.getMenuByPid(pid);
            resultMap.put("flag","Y");
            resultMap.put("data",toJsonString(list));

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/getMenuById.do")
    public Map<String,Object> getMenuById(String id){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(StringUtils.isBlank(id)){
                throw new ExceptionAPI("参数不能为空");
            }
            SysMenu menu = menuSV.getMenuById(id);
            resultMap.put("flag","Y");
            resultMap.put("data",toJsonString(menu));

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }


    @RequestMapping("/getAllMenu.do")
    public Map<String,Object> getAllMenu(){
        Map<String,Object> resultMap = new HashMap<>();
        try{

            List<SysMenu> list = menuSV.getAllMenu();
            resultMap.put("flag","Y");
            resultMap.put("data",toJsonString(list));

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }



    @RequestMapping("/doDeleteMenu.do")
    public Map<String,Object> doDeleteMenu(String id){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(StringUtils.isBlank(id)){
                throw new ExceptionAPI("参数不能为空");
            }
            menuSV.deleteMenu(id);
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
