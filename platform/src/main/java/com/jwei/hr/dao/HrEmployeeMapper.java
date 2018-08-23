package com.jwei.hr.dao;

import com.jwei.hr.entity.HrEmployee;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface HrEmployeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HrEmployee record);

    int insertSelective(HrEmployee record);

    HrEmployee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HrEmployee record);

    int updateByPrimaryKey(HrEmployee record);

    HrEmployee selectByIdCard(String idCard);

    List selectByPage(@Param("employNo")String employNo, @Param("name")String name,
                      @Param("idCard")String idCard, @Param("birthdayMonth")Integer birthdayMonth,
                      @Param("contractDate")Date contractDate, @Param("state")Byte state,
                      @Param("start")int start, @Param("end")int end);

    int countByPage(@Param("employNo")String employNo,@Param("name")String name,
                    @Param("idCard")String idCard,@Param("birthdayMonth")Integer birthdayMonth,
                    @Param("contractDate")Date contractDate,@Param("state")Byte state);

    List<HrEmployee> selectByCond(@Param("birthdayMonth")Integer birthdayMonth,@Param("noContractDay")Integer noContractDay,
                                  @Param("contractDate")Date contractDate, @Param("state")Byte state);





}