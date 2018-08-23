package com.wyb.sys.service;

import com.wyb.sys.entity.SessionUser;
import com.wyb.sys.entity.SysMenu;

import java.util.List;

/**
 * Created by wyb on 2018/6/7.
 */
public interface MenuSV {

    public void saveMenu(List<SysMenu> list,SessionUser user)throws Exception;

    public SysMenu getMenuById(String id) throws Exception;

    public List<SysMenu> getMenuByPid(String pid)throws Exception;

    public List<SysMenu> getAllMenu()throws Exception;

    public void deleteMenu(String id) throws Exception;
}
