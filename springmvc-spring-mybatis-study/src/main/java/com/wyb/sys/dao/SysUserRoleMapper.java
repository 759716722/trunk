package com.wyb.sys.dao;

import com.wyb.sys.entity.SysRole;
import com.wyb.sys.entity.SysUser;
import com.wyb.sys.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

    List selectByUserId(Long userId);

    SysUserRole selectByUserIdAndRoleId(@Param("userId")Long userId,@Param("roleId")Long roleId);

    List<SysUser> selectUserByRoleId(Long roleId);

    List<SysRole> selectRoleByUserId(Long userId);

    int deleteByUserId(Long userId);

    int deleteByRoleId(Long roleId);

}