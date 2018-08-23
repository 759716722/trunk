package com.wyb.sys.service;

import com.wyb.sys.entity.SysRoleMenu;

import java.util.List;

/**
 * Created by wyb on 2018/6/11.
 */
public interface RoleMenuSV {

    public void saveRoleMenuByRoleId(List<SysRoleMenu> list,String roleId)throws Exception;

    public List getMenuByRoleId(String roleId)throws Exception;

    public void deleteRoleMenu(String id)throws Exception;

}
