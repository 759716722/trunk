package com.wyb.sys.service.impl;

import com.wyb.sys.dao.SysMenuMapper;
import com.wyb.sys.dao.SysRoleMenuMapper;
import com.wyb.sys.entity.SessionUser;
import com.wyb.sys.entity.SysMenu;
import com.wyb.sys.service.MenuSV;
import com.wyb.utils.ExceptionAPI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by wyb on 2018/6/7.
 */
@Service
public class MenuSVImpl implements MenuSV {

    @Resource
    private SysMenuMapper menuMapper;
    @Resource
    private SysRoleMenuMapper roleMenuMapper;

    @Override
    public void saveMenu(List<SysMenu> list, SessionUser user) throws Exception {
        if(list == null){
            throw new ExceptionAPI("保存信息不能为空。");
        }
        for(SysMenu one : list){
            SysMenu oldMenu = menuMapper.selectByNameOrUrl(one.getName(),one.getUrl());
            if(one.getId()==null || one.getId()<1){
                if(oldMenu!=null){
                    if(oldMenu.getName().equals(one.getName())){
                        throw new ExceptionAPI("菜单["+one.getName()+"]"+"已存在，请核查。");
                    }else if(oldMenu.getUrl().equals(one.getUrl())){
                        throw new ExceptionAPI("URL["+one.getUrl()+"]"+"已存在，请核查。");
                    }
                }
                one.setCreateId(user.getId());
                one.setCreateName(user.getLoginName());
                one.setCreateDate(new Date());
                menuMapper.insert(one);
            }
        }

    }

    @Override
    public SysMenu getMenuById(String id) throws Exception {
        return menuMapper.selectByPrimaryKey(Long.parseLong(id));
    }

    @Override
    public List<SysMenu> getMenuByPid(String pid) throws Exception {
        return menuMapper.selectByPid(Long.parseLong(pid));
    }

    @Override
    public List<SysMenu> getAllMenu() throws Exception {
        return menuMapper.selectAllMenu();
    }

    @Override
    public void deleteMenu(String id) throws Exception {

        roleMenuMapper.deleteByRecursionMenuId(Long.parseLong(id));

        menuMapper.deleteByRecursionId(Long.parseLong(id));
    }

}
