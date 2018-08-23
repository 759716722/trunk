package com.jwei.sys.shiro;

import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by wyb on 2018/1/25.
 */
@Service
public class ReloadPermissionService {
    private static Logger log = LoggerFactory.getLogger(ReloadPermissionService.class);

    @Resource
    private ShiroPermissionFactory shiroPermissionFactory;

    public void reloadPermission(){
        log.info("开始重新加载权限过滤链");
        synchronized (shiroPermissionFactory){
            AbstractShiroFilter shiroFilter = null;
            try {

                shiroFilter = (AbstractShiroFilter) shiroPermissionFactory.getObject();

                /* 这里被搞死了，一直用的jetty容器，一直编译不通过，报错是 无法访问 filter,一直找不到原因
                *  终于在用tomcat容器的时候才完整的看到提示信息：
                *  Error:(39, 110) java: 无法访问javax.servlet.Filter找不到javax.servlet.Filter的类文件
                *  擦，原来是没有这jar包,引入 servlet-api jar包 通过
                *  原因，可能是由于缺少这个jar包所以导致 无法访问到 shiroFilter.getFilterChainResolver();
                * */

                // 获取过滤管理器
                PathMatchingFilterChainResolver chainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
                /*  DefaultFilterChainManager 是 FilterChainManager接口的实现类  */
                DefaultFilterChainManager manager = (DefaultFilterChainManager) chainResolver.getFilterChainManager();
                // 清空初始权限配置
                manager.getFilterChains().clear();
                shiroPermissionFactory.getFilterChainDefinitionMap().clear();
                //重新加载配置文件中和数据库中的权限过滤链
                shiroPermissionFactory.setFilterChainDefinitions(ShiroPermissionFactory.definition);
                Map<String,String> filterChainMap = shiroPermissionFactory.getFilterChainDefinitionMap();

                //重新生成过滤链
                if (!CollectionUtils.isEmpty(filterChainMap)) {
                    for (Map.Entry<String, String> chain : filterChainMap.entrySet()) {
                        manager.createChain(chain.getKey(), chain.getValue().replace(" ", ""));
                    }
                }
                log.info("权限过滤链加载完成");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
