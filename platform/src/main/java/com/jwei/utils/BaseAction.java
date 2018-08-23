package com.jwei.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jwei.sys.entity.SessionUser;
import com.jwei.sys.shiro.ShiroUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wyb on 2018/5/16.
 */
public abstract class BaseAction {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected SessionUser getSessionUser() {
        return ShiroUtils.getSessionUser();
    }

    protected String toJsonString(Object obj){
        return JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue);
    }

}
