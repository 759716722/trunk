package com.jwei.sys.service;

import com.jwei.sys.entity.SessionUser;
import com.jwei.sys.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * Created by wyb on 2017/12/25.
 */
public interface UserSV {
    public void saveUser(SysUser info,SessionUser user) throws Exception;

    public Map getUserByPage(String name,String loginName,String state,
                             int start,int end)throws Exception;

    public List<SysUser> getUserByOrCond(String searchParam) throws Exception;

    public SysUser getUserById(String id) throws Exception;

    public SysUser getUserByAccount(String userName) throws Exception;

    public void deleteUser(String id) throws Exception;

}
