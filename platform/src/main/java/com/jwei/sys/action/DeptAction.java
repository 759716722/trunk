package com.jwei.sys.action;

import com.jwei.sys.entity.SessionUser;
import com.jwei.sys.entity.SysDept;
import com.jwei.sys.service.DeptSV;
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
 * Created by wyb on 2018/6/15.
 */
@RestController
@RequestMapping("/sys/dept")
public class DeptAction extends BaseAction {
    @Resource
    private DeptSV deptSV;

    @RequestMapping("/createDept.do")
    public Map<String,Object> createDept(@RequestBody List<SysDept> list){
        Map<String,Object> resultMap = new HashMap<>();
        SessionUser user = getSessionUser();
        try {
            if(list==null){
                throw new ExceptionAPI("参数不能为空");
            }
            deptSV.saveDept(list,user);
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

    @RequestMapping("/getDeptByPid.do")
    public Map<String,Object> getDeptByPid(String pid){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(StringUtils.isBlank(pid)){
                throw new ExceptionAPI("参数不能为空");
            }
            List<SysDept> list = deptSV.getDeptByPid(pid);
            resultMap.put("flag","Y");
            resultMap.put("data",toJsonString(list));

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/getAllDept.do")
    public Map<String,Object> getAllDept(){
        Map<String,Object> resultMap = new HashMap<>();
        try{

            List<SysDept> list = deptSV.getAllDept();
            resultMap.put("flag","Y");
            resultMap.put("data",toJsonString(list));

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/getDeptById.do")
    public Map<String,Object> getDeptById(String id){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(StringUtils.isBlank(id)){
                throw new ExceptionAPI("参数不能为空");
            }
            SysDept role = deptSV.getDeptById(id);
            resultMap.put("flag","Y");
            resultMap.put("data",toJsonString(role));

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/doDeleteDept.do")
    public Map<String,Object> doDeleteDept(String id){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(StringUtils.isBlank(id)){
                throw new ExceptionAPI("参数不能为空");
            }
            deptSV.deleteDept(id);
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
