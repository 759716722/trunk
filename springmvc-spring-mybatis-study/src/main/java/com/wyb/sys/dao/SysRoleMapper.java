package com.wyb.sys.dao;

import com.wyb.sys.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    SysRole selectByName(String name);

    List<SysRole> selectByCond(@Param("name")String name);

    List selectByPage(@Param("name")String name,@Param("start")int start,@Param("end")int end);
    int countByPage(@Param("name")String name);


}