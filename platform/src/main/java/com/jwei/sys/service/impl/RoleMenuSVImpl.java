package com.jwei.sys.service.impl;

import com.jwei.sys.dao.SysRoleMapper;
import com.jwei.sys.dao.SysRoleMenuMapper;
import com.jwei.sys.entity.SysRole;
import com.jwei.sys.entity.SysRoleMenu;
import com.jwei.sys.service.RoleMenuSV;
import com.jwei.utils.Constant;
import com.jwei.utils.ExceptionAPI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by wyb on 2018/6/12.
 */
@Service
public class RoleMenuSVImpl implements RoleMenuSV {

    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private SysRoleMenuMapper roleMenuMapper;

    @Override
    public void saveRoleMenuByRoleId(List<SysRoleMenu> list,String roleId) throws Exception {

        SysRole role = roleMapper.selectByPrimaryKey(Long.parseLong(roleId));
        if(role==null){
            throw new ExceptionAPI("该角色不存在，请核查。");
        }else if("admin".equals(role.getName())){
            throw new ExceptionAPI("管理员角色不允许操作。");
        }
        if(list==null || list.isEmpty()){
            roleMenuMapper.deleteByRoleId(Long.parseLong(roleId));
        }else{
            List<SysRoleMenu> oldList = roleMenuMapper.selectByRoleId(Long.parseLong(roleId));
            List<SysRoleMenu> copyList = new  ArrayList(Arrays.asList(new SysRoleMenu[list.size()]));
            Collections.copy(copyList, list);
            // add
            list.removeAll(oldList);
            for(SysRoleMenu one : list){
                roleMenuMapper.insert(one);
            }
            // delete
            oldList.removeAll(copyList);
            for(SysRoleMenu one : oldList){
                roleMenuMapper.deleteByPrimaryKey(one.getId());
            }
        }

    }

    @Override
    public List getMenuByRoleId(String roleId) throws Exception {
        return roleMenuMapper.selectMenuByRoleId(Long.parseLong(roleId));
    }

    @Override
    public void deleteRoleMenu(String id) throws Exception {
        roleMenuMapper.deleteByPrimaryKey(Long.parseLong(id));
    }
}
