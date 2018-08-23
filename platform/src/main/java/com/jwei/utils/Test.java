package com.jwei.utils;

import org.apache.shiro.util.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/12/7.
 */
public class Test {
    public static void testStr(String str){
        str=str+"hello";//型参指向字符串 “hello”
        System.out.println("str="+str); //实参s引用没变，值也不变
    }

    public static String determineRootDir(String location) {
        int prefixEnd = location.indexOf(":") + 1;
        System.out.println(prefixEnd);

        int rootDirEnd;
        for(rootDirEnd = location.length(); rootDirEnd > prefixEnd&&new AntPathMatcher().isPattern(location.substring(prefixEnd, rootDirEnd)) ; rootDirEnd = location.lastIndexOf(47, rootDirEnd - 2) + 1) {
            ;
        }
        System.out.println(location.length());
        System.out.println(location.lastIndexOf(47, rootDirEnd - 2) + 1);

        if(rootDirEnd == 0) {
            rootDirEnd = prefixEnd;
        }

        return location.substring(0, rootDirEnd);
    }


    public static void main(String[] args) {
        String s="1" ;
        testStr(s);
        System.out.println("s="+s); //实参s引用没变，值也不变


        // create an array of string objs
        String init[] = { "One", "Two", "Three", "One", "Two", "Three",null,null};

        // create two lists
        List list1 = new ArrayList(Arrays.asList(init));
        List list2 = new ArrayList(Arrays.asList(init));

        // remove from list1
        list1.remove("One");
        System.out.println("List1 value: " + list1);
        list1.removeAll(Collections.singleton(null));
        System.out.println("List1 value: " + list1);

        // remove from list2 using singleton
        list2.removeAll(Collections.singleton("One"));
        System.out.println("The SingletonList is :"+list2);

        String a = "classpath*:com/jwei/**/dao/*";
        Boolean b = new AntPathMatcher().isPattern(a.substring("classpath*:".length()));
        System.out.println(b);

        String c = determineRootDir(a);
        System.out.println(c);

        Integer q = 11;
        testInt(q);
        testInteger(q);


        String subject = MessageFormat.format("超时[{1}]天及以上未关闭的采购订单","123");
        String subject2 = MessageFormat.format("超时[{0}]天及以上未关闭的采购订单","456");
        System.out.println(subject+subject2);

    }


    public static void testInt(int q){
        System.out.println(q);
    }
    public static void testInteger(Integer q){
        q++;
        System.out.println(q);
    }

}
