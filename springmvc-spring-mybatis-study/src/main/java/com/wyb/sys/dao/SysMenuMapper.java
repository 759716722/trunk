package com.wyb.sys.dao;

import com.wyb.sys.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    List<String> selectAllUrl();

    List<SysMenu> selectAllMenu();

    List<SysMenu> selectByPid(Long pid);

    SysMenu selectByNameOrUrl(@Param("name")String name,@Param("url")String url);

    int deleteByRecursionId(Long id);

}