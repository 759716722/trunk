package com.jwei.sys.service.impl;

import com.jwei.sys.dao.SysUserRoleMapper;
import com.jwei.sys.entity.SessionUser;
import com.jwei.sys.entity.SysUserRole;
import com.jwei.sys.service.UserRoleSV;
import com.jwei.utils.ExceptionAPI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wyb on 2018/6/11.
 */
@Service
public class UserRoleSVImpl implements UserRoleSV {

    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Override
    public void saveUserRole(SysUserRole info) throws Exception {

        if(info.getId()==null || info.getId()<1){
            SysUserRole oldInfo = userRoleMapper.selectByUserIdAndRoleId(info.getUserId(),info.getRoleId());
            if(oldInfo!=null){
                throw new ExceptionAPI("该用户已经拥有该角色");
            }
            userRoleMapper.insert(info);
        }

    }

    @Override
    public List getUserByRoleId(String roleId) throws Exception {
        return userRoleMapper.selectUserByRoleId(Long.parseLong(roleId));
    }

    @Override
    public List getRoleByUserId(String userId) throws Exception {
        return userRoleMapper.selectRoleByUserId(Long.parseLong(userId));
    }

    @Override
    public void deleteUserRole(String id) throws Exception {
        userRoleMapper.deleteByPrimaryKey(Long.parseLong(id));
    }

}
