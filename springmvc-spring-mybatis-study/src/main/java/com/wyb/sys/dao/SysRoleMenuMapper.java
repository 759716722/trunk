package com.wyb.sys.dao;

import com.wyb.sys.entity.SysMenu;
import com.wyb.sys.entity.SysRoleMenu;

import java.util.List;

public interface SysRoleMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRoleMenu record);

    int insertSelective(SysRoleMenu record);

    SysRoleMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleMenu record);

    int updateByPrimaryKey(SysRoleMenu record);


    List selectByUserId(Long userId);

    List<SysRoleMenu> selectByRoleId(Long roleId);

    List<SysMenu> selectMenuByRoleId(Long roleId);

    int deleteByRoleId(Long roleId);


    /**
     * 描述: 递归所有菜单Id 并删除，必须再执行删除菜单之前调用。
     * @author: wyb
     * @date: 2018/6/20
     */
     int deleteByRecursionMenuId(Long menuId);


}