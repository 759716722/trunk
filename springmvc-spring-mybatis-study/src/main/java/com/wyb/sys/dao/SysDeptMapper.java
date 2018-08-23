package com.wyb.sys.dao;

import com.wyb.sys.entity.SysDept;

import java.util.List;

public interface SysDeptMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);

    SysDept selectByName(String name);

    List<SysDept> selectByPid(Long pid);

    List<SysDept> selectAllDept();

    int deleteByIdOrPid(Long id);

}