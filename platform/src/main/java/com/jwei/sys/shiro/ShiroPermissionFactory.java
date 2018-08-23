package com.jwei.sys.shiro;

import com.alibaba.fastjson.JSON;
import com.jwei.sys.dao.SysMenuMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wyb on 2018/1/25.
 */
public class ShiroPermissionFactory extends ShiroFilterFactoryBean {
    private final static Logger log = LoggerFactory.getLogger(ShiroPermissionFactory.class);

    @Resource
    private SysMenuMapper menuMapper;

    public static String definition="";

    @Override
    public void setFilterChainDefinitions(String definitions) {
        // 记录配置文件中的过滤链
        this.definition = definitions;
        log.info("加载配置文件中的过滤链");
        //加载配置默认的过滤链和数据库取出的过滤连
        Ini ini = new Ini();
        ini.load(definitions);
        Ini.Section section = ini.getSection(IniFilterChainResolverFactory.URLS);
        if (CollectionUtils.isEmpty(section)) {
            section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        }
        log.info("加载数据库权限过滤链");
        Map<String,String> otherChainMap = new LinkedHashMap();
        List<String> menuList = menuMapper.selectAllUrl();
        for(String one : menuList){
            if(StringUtils.isNotBlank(one)){
                otherChainMap.put(one,MessageFormat.format("perms[{0}]",one));
            }
        }
        otherChainMap.put("/**", "authc");
        section.putAll(otherChainMap);
        setFilterChainDefinitionMap(section);
        log.info("所有过滤链:" + JSON.toJSONString(section));

    }
}
