package com.jwei.sys.dao;

import com.jwei.sys.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser selectByUserName(String name);

    List<SysUser> selectByOrCond(@Param("searchParam")String searchParam);

    List selectByPage(@Param("name")String name,@Param("loginName")String loginName,
                               @Param("state")String state,@Param("start")int start, @Param("end")int end);
    int countByPage(@Param("name")String name,@Param("loginName")String loginName,@Param("state")String state);


}