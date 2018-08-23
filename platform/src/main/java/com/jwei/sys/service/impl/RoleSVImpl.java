package com.jwei.sys.service.impl;

import com.jwei.sys.dao.SysRoleMapper;
import com.jwei.sys.dao.SysRoleMenuMapper;
import com.jwei.sys.dao.SysUserRoleMapper;
import com.jwei.sys.entity.SessionUser;
import com.jwei.sys.entity.SysRole;
import com.jwei.sys.service.RoleSV;
import com.jwei.utils.ExceptionAPI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wyb on 2018/6/4.
 */
@Service
public class RoleSVImpl implements RoleSV {
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private SysRoleMenuMapper roleMenuMapper;
    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Override
    public void saveRole(SysRole info, SessionUser user) throws Exception {

        SysRole role = roleMapper.selectByName(info.getName());

        if(info.getId()==null || info.getId()<1){
            if(role!=null){
                throw new ExceptionAPI("该角色已存在，请核查。");
            }
            info.setCreateId(user.getId());
            info.setCreateName(user.getLoginName());
            info.setCreateDate(new Date());
            roleMapper.insert(info);
        }else{
            if(role!=null && !info.getId().equals(role.getId())){
                throw new ExceptionAPI("该角色已存在，请核查。");
            }
            info.setModifyId(user.getId());
            info.setModifyName(user.getLoginName());
            info.setModifyDate(new Date());
            roleMapper.updateByPrimaryKeySelective(info);
        }
    }

    @Override
    public SysRole getRoleById(String id) throws Exception {
        return roleMapper.selectByPrimaryKey(Long.parseLong(id));
    }

    @Override
    public Map getRoleByPage(String name,int start,int end) throws Exception {
        List list = roleMapper.selectByPage(name,start,end);
        int count = roleMapper.countByPage(name);
        Map resultMap = new HashMap();
        resultMap.put("dataList",list);
        resultMap.put("dataCount",count);
        return resultMap;
    }

    @Override
    public List<SysRole> getRoleByCond(String name) throws Exception {
        return roleMapper.selectByCond(name);
    }

    @Override
    public void deleteRole(String id) throws Exception {
        SysRole role = getRoleById(id);
        if(role == null){
            throw new ExceptionAPI("角色不存在");
        }
        if("admin".equals(role.getName())){
            throw new ExceptionAPI("管理员角色不能删除");
        }
        roleMapper.deleteByPrimaryKey(Long.parseLong(id));
        roleMenuMapper.deleteByRoleId(Long.parseLong(id));
        userRoleMapper.deleteByRoleId(Long.parseLong(id));
    }
}
