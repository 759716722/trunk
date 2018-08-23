package com.jwei.hr.dao;

import com.jwei.hr.entity.HrEmployeeContract;

import java.util.List;

public interface HrEmployeeContractMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HrEmployeeContract record);

    int insertSelective(HrEmployeeContract record);

    HrEmployeeContract selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HrEmployeeContract record);

    int updateByPrimaryKey(HrEmployeeContract record);

    List<HrEmployeeContract> selectByEmployeeId(Integer employeeId);


}