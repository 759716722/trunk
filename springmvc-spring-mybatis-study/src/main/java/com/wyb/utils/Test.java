package com.wyb.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */
public class Test {
    public static void testStr(String str){
        str=str+"hello";//型参指向字符串 “hello”
        System.out.println("str="+str); //实参s引用没变，值也不变
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

    }

}
