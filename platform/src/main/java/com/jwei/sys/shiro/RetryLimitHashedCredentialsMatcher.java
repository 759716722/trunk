package com.jwei.sys.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wyb on 2017/12/25.
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher{

    final static Logger log = LoggerFactory.getLogger(RetryLimitHashedCredentialsMatcher.class);
    //集群中可能会导致出现验证多过5次的现象，因为AtomicInteger只能保证单节点并发
    private Cache<String, AtomicInteger> passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
        log.info("缓存初始化成功");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token,
                                      AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        log.info("获取token中的用户名称[{}]",username);
        // retry count + 1
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        log.info("用户[{}]进行密码验证并放入密码验证缓存，已验证[{}]次",username,retryCount);
        if (retryCount.incrementAndGet() > 5) {
            log.info("用户[{}]密码验证超过五次，锁定10分钟。",username);
            // if retry count > 5 throw
            throw new ExcessiveAttemptsException();
        }

        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            // clear retry count
            passwordRetryCache.remove(username);
            log.info("用户[{}]登陆成功，清除该用户的尝试次数",username);
        }
        return matches;
    }

    /*
    * 主动清除某用户的尝试次数
    * */
    public void clearPasswordCache(String username){
        passwordRetryCache.remove(username);
        log.info("主动清除该用户"+username+"的尝试次数");
    }
}
