package com.wyb.sys.service;

import com.wyb.sys.entity.SysUserRole;

import java.util.List;

/**
 * Created by wyb on 2018/6/11.
 */
public interface UserRoleSV {

    public void saveUserRole(SysUserRole info)throws Exception;

    public List getUserByRoleId(String roleId)throws Exception;

    public List getRoleByUserId(String userId)throws Exception;

    public void deleteUserRole(String id)throws Exception;

}
