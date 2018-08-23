package com.jwei.sys.action;

import com.jwei.sys.entity.SessionUser;
import com.jwei.sys.entity.SysMenu;
import com.jwei.sys.entity.SysUser;
import com.jwei.sys.service.MenuSV;
import com.jwei.sys.service.UserSV;
import com.jwei.sys.shiro.ShiroUtils;
import com.jwei.utils.BaseAction;
import com.jwei.utils.ExceptionAPI;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by wyb on 2017/12/25.
 */

@RestController
@RequestMapping("/sys/user")
public class UserAction extends BaseAction {

    @Resource
    private UserSV userSV;
    @Resource
    private MenuSV menuSV;


    /*
    * 登陆
    * */
    @RequestMapping("/login.do")
    public Map<String,Object> login(String username,String password) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,
                password);
        String error = null;
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            error = "用户名/密码错误";
        } catch (IncorrectCredentialsException e) {
            error = "用户名/密码错误";
        } catch (ExcessiveAttemptsException e) {
            error = "登录失败多次，账户锁定10分钟";
        } catch (AuthenticationException e) {
            // 其他错误，比如锁定，如果想单独处理请单独catch处理
            error = "其他错误：" + e.getMessage();
        }

        Map<String,Object> resultMap = new HashMap<>();

        if (error != null) {// 出错了，返回登录页面
            resultMap.put("flag","N");
            resultMap.put("data",error);
        } else {// 登录成功
            resultMap.put("flag","Y");
            resultMap.put("data","success");
        }
        return resultMap;
    }

    /*
    * 创建用户
    * */
    @RequestMapping("/createUser.do")
    public Map<String,Object> createUser(@RequestBody SysUser info) {
        Map<String,Object> resultMap = new HashMap<>();
        SessionUser user = getSessionUser();
        try {
            if(info.getId()!=null||StringUtils.isBlank(info.getName())
                    ||StringUtils.isBlank(info.getPassword())){
                throw new ExceptionAPI("参数错误[关键参数为空]");
            }
            String password = ShiroUtils.getPassword(info.getPassword(), info.getSalt());
            info.setPassword(password);
            userSV.saveUser(info,user);
            resultMap.put("flag","Y");
            resultMap.put("data","success");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put("flag", "N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    /*
    * 获取系统用户,用于selete2组件使用
    * @Param searchParam
    * and (name like searchParam or loginName like searchParam or phone like searchParam)
    * and state = 1
    * */
    @RequestMapping("/getUserByOrCond.do")
    public Map<String,Object> getUserByOrCond(String searchParam){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(StringUtils.isBlank(searchParam)){
                searchParam = null;
            }
            List<SysUser> list = userSV.getUserByOrCond(searchParam);
            resultMap.put("flag", "Y");
            resultMap.put("data", toJsonString(list));

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    /*
    * 分页查看系统用户
    * */
    @RequestMapping("/getUserByPage.do")
    public Map<String,Object> getUserByPage(String name,String loginName,String state,int onePageNum,int pageNo){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(StringUtils.isBlank(name)){
                name = null;
            }
            if(StringUtils.isBlank(loginName)){
                loginName = null;
            }
            if(StringUtils.isBlank(state)){
                state = null;
            }
            int start = onePageNum*(pageNo-1)+1;
            int end = onePageNum*pageNo;
            Map dataMap = userSV.getUserByPage(name, loginName, state, start, end);
            resultMap.put("flag", "Y");
            resultMap.put("data", toJsonString(dataMap));

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }
    /*
    * 查看单个系统用户
    * */
    @RequestMapping("/getUserById.do")
    public Map<String,Object> getUserById(String id){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(StringUtils.isBlank(id)){
                throw new ExceptionAPI("参数不能为空");
            }
            SysUser user = userSV.getUserById(id);
            user.setPassword(null);
            resultMap.put("flag", "Y");
            resultMap.put("data", toJsonString(user));

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    /*
    * 修改单个系统用户
    * */
    @RequestMapping("/doModifyUser.do")
    public Map<String,Object> doModifyUser(@RequestBody SysUser info){
        Map<String,Object> resultMap = new HashMap<>();
        SessionUser user = getSessionUser();
        try{
            if(info.getId()==null){
                throw new ExceptionAPI("参数不能为空");
            }
            if(StringUtils.isNotBlank(info.getPassword())){
                String password = ShiroUtils.getPassword(info.getPassword(),info.getSalt());
                info.setPassword(password);
            }
            userSV.saveUser(info,user);
            resultMap.put("flag", "Y");
            resultMap.put("data", "success");

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    /*
    * 真是删除（慎用）
    * */
    @RequestMapping("/doDeleteUser.do")
    public Map<String,Object> doDeleteUser(String id){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(StringUtils.isBlank(id)){
                throw new ExceptionAPI("参数不能为空");
            }
            userSV.deleteUser(id);
            resultMap.put("flag", "Y");
            resultMap.put("data", "success");

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    /*
    * 查看当前登陆系统用户,及目录菜单
    * */
    @RequestMapping("/getCurrUserAndMenu.do")
    public Map<String,Object> getCurrUserAndMenu(){
        Map<String,Object> resultMap = new HashMap<>();
        SessionUser user = getSessionUser();
        try{
            List<SysMenu> menuList = menuSV.getMenuByUserId(user.getId());
            Map dataMap = new HashMap();
            dataMap.put("user",user);
            dataMap.put("menuList",menuList);
            resultMap.put("flag", "Y");
            resultMap.put("data", toJsonString(dataMap));

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    /*
    * 个人修改密码
    * */
    @RequestMapping("/doModifyPswBySelf.do")
    public Map<String,Object> doModifyPsw(String oldPassword,String password) {
        Map<String,Object> resultMap = new HashMap<>();
        SessionUser user = getSessionUser();
        try {
            if(StringUtils.isBlank(oldPassword)||StringUtils.isBlank(password)){
                throw new ExceptionAPI("参数错误");
            }
            SysUser userInfo = userSV.getUserById(user.getId().toString());
            String old = ShiroUtils.getPassword(oldPassword,userInfo.getSalt());
            if(!userInfo.getPassword().equals(old)){
                throw new ExceptionAPI("原始密码错误");
            }
            String newPassword = ShiroUtils.getPassword(password,userInfo.getSalt());
            userInfo.setPassword(newPassword);
            userSV.saveUser(userInfo,user);
            resultMap.put("flag","Y");
            resultMap.put("data","success");

        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    /*
    * 个人修改资料
    * */
    @RequestMapping("/doModifyUserBySelf.do")
    public Map<String,Object> doModifyUserBySelf(@RequestBody SysUser info) {
        Map<String,Object> resultMap = new HashMap<>();
        SessionUser user = getSessionUser();
        try {
            info.setId(user.getId());
            info.setName(null);
            info.setPassword(null);
            info.setState(null);
            userSV.saveUser(info,user);
            resultMap.put("flag","Y");
            resultMap.put("data","success");

        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }


}


