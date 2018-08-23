package com.jwei.hr.service;

import com.jwei.hr.entity.HrEmployee;
import com.jwei.hr.entity.HrEmployeeContract;
import com.jwei.sys.entity.SessionUser;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wyb on 2018/6/26.
 */
public interface EmployeeSV {

    public void saveEmployee(HrEmployee info, SessionUser user)throws Exception;

    public Map getEmployeeByPage(String employeeNo, String name, String idCard,
                                 Integer birthdayMonth, Date contractDate, Byte state,
                                 int start, int end)throws Exception;

    public List<HrEmployee> getEmployeeByCond(Integer birthdayMonth, Integer noContractDay,Date contractEndDate, Byte state)throws Exception;

    public HrEmployee getEmployeeById(String id)throws Exception;

    public void deleteEmployee(String id)throws Exception;

    public void saveContract(HrEmployeeContract info, SessionUser user)throws Exception;

    public List<HrEmployeeContract> getContractByEmployeeId(Integer employeeId)throws Exception;


}
