package com.jwei.hr.action;

import com.jwei.hr.entity.HrEmployee;
import com.jwei.hr.entity.HrEmployeeContract;
import com.jwei.hr.service.EmployeeSV;
import com.jwei.sys.entity.SessionUser;
import com.jwei.utils.BaseAction;
import com.jwei.utils.Constant;
import com.jwei.utils.ExceptionAPI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wyb on 2018/6/26.
 */
@RestController
@RequestMapping("/hr/employee")
public class EmployeeAction extends BaseAction{
    @Resource
    private EmployeeSV employeeSV;

    @RequestMapping("/createEmployee.do")
    public Map<String,Object> createEmployee(@RequestBody HrEmployee info){
        Map<String,Object> resultMap = new HashMap<>();
        SessionUser user = getSessionUser();
        try{
            if(info.getId()!=null){
                throw new ExceptionAPI("系统异常[逻辑错误]");
            }
            if(StringUtils.isAnyBlank(info.getName(),info.getIdCard()) || info.getState()==null){
                throw new ExceptionAPI("关键参数不能为空[名称/身份证号码/状态]");
            }
            employeeSV.saveEmployee(info,user);
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

    @RequestMapping("/doModifyEmployee.do")
    public Map<String,Object> doModifyEmployee(@RequestBody HrEmployee info){
        Map<String,Object> resultMap = new HashMap<>();
        SessionUser user = getSessionUser();
        try{
            if(info.getId()==null){
                throw new ExceptionAPI("系统异常[逻辑错误]");
            }
            if(StringUtils.isAnyBlank(info.getName(),info.getIdCard()) || info.getState()==null){
                throw new ExceptionAPI("关键参数不能为空[名称/身份证号码/状态]");
            }
            employeeSV.saveEmployee(info,user);

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

    @RequestMapping("/getEmployeeByPage.do")
    public Map<String,Object> getEmployeeByPage(String employeeNo, String name, String idCard,
                                                Integer birthdayMonth, @DateTimeFormat(pattern="yyyy-MM-dd")Date contractMonth,
                                                Byte state, int onePageNum, int pageNo){
        Map<String,Object> resultMap = new HashMap<>();
        try{

            if(StringUtils.isBlank(name)){
                name = null;
            }
            int start = onePageNum*(pageNo-1)+1;
            int end = onePageNum*pageNo;

            Map dataMap = employeeSV.getEmployeeByPage(employeeNo, name, idCard, birthdayMonth,
                    contractMonth, state, start, end);

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

    @RequestMapping("/getEmployeeById.do")
    public Map<String,Object> getEmployeeById(String id){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(StringUtils.isBlank(id)){
                throw new ExceptionAPI("参数不能为空");
            }
            HrEmployee data = employeeSV.getEmployeeById(id);
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

    @RequestMapping("/doDeleteEmployee.do")
    public Map<String,Object> doDeleteEmployee(String id){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(StringUtils.isBlank(id)){
                throw new ExceptionAPI("参数不能为空");
            }
            employeeSV.deleteEmployee(id);
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

    @RequestMapping("/saveContract.do")
    public Map<String,Object> saveContract(@RequestBody HrEmployeeContract info){
        Map<String,Object> resultMap = new HashMap<>();
        SessionUser user = getSessionUser();
        try{
            if(info.getEmployeeId()==null || info.getStartDate()==null || info.getEndDate()==null){
                throw new ExceptionAPI("关键参数不能为空[员工Id/开始日期/结束日期]");
            }
            employeeSV.saveContract(info,user);
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

    @RequestMapping("/getContract.do")
    public Map<String,Object> getContract(Integer employeeId){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(employeeId==null ){
                return null;
            }
            List<HrEmployeeContract> list = employeeSV.getContractByEmployeeId(employeeId);
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


}
