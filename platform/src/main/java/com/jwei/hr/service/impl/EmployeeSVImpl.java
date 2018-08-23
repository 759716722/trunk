package com.jwei.hr.service.impl;

import com.jwei.hr.dao.HrEmployeeContractMapper;
import com.jwei.hr.dao.HrEmployeeMapper;
import com.jwei.hr.entity.HrEmployee;
import com.jwei.hr.entity.HrEmployeeContract;
import com.jwei.hr.service.EmployeeSV;
import com.jwei.sys.entity.SessionUser;
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
public class EmployeeSVImpl implements EmployeeSV {
    @Resource
    private HrEmployeeMapper employeeMapper;
    @Resource
    private HrEmployeeContractMapper contractMapper;

    @Override
    public void saveEmployee(HrEmployee info, SessionUser user) throws Exception {

        HrEmployee old = employeeMapper.selectByIdCard(info.getIdCard());
        if(info.getId()==null || info.getId()<1){
            if(old!=null){
                throw new ExceptionAPI("该记录已存在，请核查。");
            }
            info.setCreateId(user.getId());
            info.setCreateName(user.getLoginName());
            info.setCreateDate(new Date());
            employeeMapper.insert(info);
        }else{
            if(old!=null && !info.getId().equals(old.getId())){
                throw new ExceptionAPI("该记录已存在，请核查。");
            }
            info.setModifyId(user.getId());
            info.setModifyName(user.getLoginName());
            info.setModifyDate(new Date());
            employeeMapper.updateByPrimaryKeySelective(info);
        }
    }


    @Override
    public Map getEmployeeByPage(String employeeNo,String name,String idCard,
                                 Integer birthdayMonth,Date contractDate,Byte state,
                                 int start,int end) throws Exception {
        List dataList = employeeMapper.selectByPage(employeeNo,name,idCard, birthdayMonth,
                contractDate, state,start,end);
        int dataCount = employeeMapper.countByPage(employeeNo,name,idCard,birthdayMonth,contractDate,state);
        Map dataMap = new HashMap();
        dataMap.put("dataList",dataList);
        dataMap.put("dataCount",dataCount);
        return dataMap;
    }

    @Override
    public List<HrEmployee> getEmployeeByCond(Integer birthdayMonth,Integer noContractDay,Date contractEndDate,Byte state) throws Exception {
        return employeeMapper.selectByCond(birthdayMonth,noContractDay,contractEndDate,state);
    }

    @Override
    public HrEmployee getEmployeeById(String id) throws Exception {
        return employeeMapper.selectByPrimaryKey(Integer.valueOf(id));
    }

    @Override
    public void deleteEmployee(String id) throws Exception {

        employeeMapper.deleteByPrimaryKey(Integer.valueOf(id));
    }

    @Override
    public void saveContract(HrEmployeeContract info, SessionUser user) throws Exception {
        if(info.getId()==null || info.getId()<1){
            info.setCreateId(user.getId());
            info.setCreateName(user.getLoginName());
            info.setCreateDate(new Date());
            contractMapper.insert(info);

            // 更改员工的劳动合同信息
            List<HrEmployeeContract> list = contractMapper.selectByEmployeeId(info.getEmployeeId());
            HrEmployee employee = new HrEmployee();
            employee.setId(info.getEmployeeId());
            employee.setContractNum((byte) list.size());
            employee.setContractStartDate(info.getStartDate());
            employee.setContractEndDate(info.getEndDate());
            employeeMapper.updateByPrimaryKeySelective(employee);

        }else{
            info.setModifyId(user.getId());
            info.setModifyName(user.getLoginName());
            info.setModifyDate(new Date());
            contractMapper.updateByPrimaryKeySelective(info);
        }
    }

    @Override
    public List<HrEmployeeContract> getContractByEmployeeId(Integer employeeId) throws Exception {
        return contractMapper.selectByEmployeeId(employeeId);
    }
}
