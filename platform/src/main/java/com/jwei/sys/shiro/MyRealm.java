package com.jwei.sys.shiro;

import com.jwei.sys.dao.*;
import com.jwei.sys.entity.SessionUser;
import com.jwei.sys.entity.SysMenu;
import com.jwei.sys.entity.SysUser;
import com.jwei.utils.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by wyb on 2017/12/25.
 */
public class MyRealm extends AuthorizingRealm {

    @Resource
    private SysUserMapper userMapper;
    @Resource
    private SysUserRoleMapper userRoleMapper;
    @Resource
    private SysMenuMapper menuMapper;
    @Resource
    private SysRoleMenuMapper roleMenuMapper;
    /*
    *  认证
    * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername(); //得到用户名
        String password = token.getPassword()==null?null:String.valueOf(token.getPassword()); //得到密码

        SysUser user = userMapper.selectByUserName(username);
        if(user==null) {
            throw new UnknownAccountException(); //如果用户名错误
        }
        if(!(1==user.getState())) {
            throw new LockedAccountException("账户已被禁用"); //帐号锁定
        }
        SessionUser sessionUser = new SessionUser(user.getId(),user.getName(),user.getLoginName(),user.getId(),null);

        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        //如果身份认证验证成功，返回一个AuthenticationInfo实现；
        if(StringUtils.isBlank(user.getSalt())){
            return new SimpleAuthenticationInfo(sessionUser, user.getPassword(),getName());
        }else{
            return new SimpleAuthenticationInfo(sessionUser, user.getPassword(),ByteSource.Util.bytes(user.getSalt()),getName());
        }
    }

    /*
    *  授权
    * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SessionUser sessionUser = (SessionUser) principalCollection.getPrimaryPrincipal();
        List roleList = new ArrayList();
        List menuList = new ArrayList();

        if("admin".equals(sessionUser.getName())){
            roleList.add("admin");
        }else{
            roleList = userRoleMapper.selectByUserId(sessionUser.getId());
        }
        if(roleList.contains("admin")){
            menuList = menuMapper.selectAllUrl();
        }else{
            menuList = roleMenuMapper.selectByUserId(sessionUser.getId());
        }
        //这里其实用 remove(null) 就行了，别回头谁把我sql里的distinct去掉了，还是稳一点。
        roleList.removeAll(Collections.singleton(null));
        menuList.removeAll(Collections.singleton(null));
        //角色名的集合
        Set<String> roles = new HashSet<String>();
        roles.addAll(roleList);
        //菜单的集合
        Set<String> menus = new HashSet<String>();
        menus.addAll(menuList);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if(!roles.isEmpty()){
            authorizationInfo.addRoles(roles);
        }
        if(!menus.isEmpty()){
            authorizationInfo.addStringPermissions(menus);
        }
        return authorizationInfo;
    }

    /**
     * 清除用户认证缓存，用于修改密码的时候
     * @param loginName
     */
    public void removeUserCache(String loginName){
        SimplePrincipalCollection principals = new SimplePrincipalCollection();
        principals.add(loginName, super.getName());
        super.clearCachedAuthenticationInfo(principals);
    }

}
