package com.jwei.sys.action;

import com.jwei.sys.shiro.ReloadPermissionService;
import com.jwei.sys.shiro.RetryLimitHashedCredentialsMatcher;
import com.jwei.utils.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by wyb on 2018/6/15.
 */
@RestController
@RequestMapping("/sys/admin")
public class AdminAction extends BaseAction{

    @Resource
    private ReloadPermissionService reloadPermissionService;
    @Resource
    private SessionDAO sessionDAO;
    @Resource
    private RetryLimitHashedCredentialsMatcher credentialsMatcher;

    /*
    * 查看在线的用户
    * */
    @RequestMapping("/getOnlineUser.do")
    public Map<String,Object> getOnlineUser(){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            List userList = new ArrayList<>();
            Collection<Session> sessionList = sessionDAO.getActiveSessions();
            for(Session session:sessionList){
                SimplePrincipalCollection one = (SimplePrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                if(one!=null){
                    Map userMap = new HashMap();
                    userMap.put("sessionId",session.getId());
                    userMap.put("startTime",session.getStartTimestamp());
                    userMap.put("lastTime",session.getLastAccessTime());
                    Object user = one.getPrimaryPrincipal();
                    userMap.put("user",user);
                    userList.add(userMap);
                }
            }
            resultMap.put("flag","Y");
            resultMap.put("data",toJsonString(userList));

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    /*
    * 剔除某个登陆的用户
    * */
    @RequestMapping("/logoutUser.do")
    public Map<String,Object> logoutUser(String sessionId){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            Session session = sessionDAO.readSession(sessionId);
            if(session != null) {
                session.setTimeout(0);
            }
            resultMap.put("flag","Y");
            resultMap.put("data","success");

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    /*
    * 重新加载权限过滤链
    * */
    @RequestMapping("/reloadPermission.do")
    public Map<String,Object> reloadPermission(){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            reloadPermissionService.reloadPermission();
            resultMap.put("flag","Y");
            resultMap.put("data","success");

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    /*
    * 清除某个用户的尝试次数，解除锁定
    * */
    @RequestMapping("/clearPasswordCache.do")
    public Map<String,Object> clearPasswordCache(String userName){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(StringUtils.isNotBlank(userName)){
                credentialsMatcher.clearPasswordCache(userName);
            }
            resultMap.put("flag","Y");
            resultMap.put("data","success");

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

}
