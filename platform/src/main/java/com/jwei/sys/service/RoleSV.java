package com.jwei.sys.service;

import com.jwei.sys.entity.SessionUser;
import com.jwei.sys.entity.SysRole;

import java.util.List;
import java.util.Map;

/**
 * Created by wyb on 2018/6/4.
 */
public interface RoleSV {

    public void saveRole(SysRole info,SessionUser user)throws Exception;

    public SysRole getRoleById(String id) throws Exception;

    public Map getRoleByPage(String name,int start,int end)throws Exception;

    public List<SysRole> getRoleByCond(String name)throws Exception;

    public void deleteRole(String id) throws Exception;
}
