package com.jwei.utils;

import java.util.*;

/**
 * Created by wyb on 2018/6/28.
 */
public class ListTest {

    public static List removeAll1(List src,List oth){
        LinkedList result = new LinkedList(src);//大集合用linkedlist
        HashSet othHash = new HashSet(oth);//小集合用hashset
        Iterator iter = result.iterator();//采用Iterator迭代器进行数据的操作
        while(iter.hasNext()){
            if(othHash.contains(iter.next())){
                iter.remove();
            }
        }
        return result;
    }

    public static List removeAll2(List list1,List list2){

        HashSet set_all = new HashSet<>();
        for (int i=0; i<list1.size(); i++) {
            set_all.add(list1.get(i));
        }
        HashSet set_dup = new HashSet<>();
        ArrayList list2_clean = new ArrayList<>();
        for (int i=0; i<list2.size(); i++) {
            if (set_all.add(list2.get(i))) {  //in list2 but not in list1
                list2_clean.add(list2.get(i));
            } else {
                set_dup.add(list2.get(i));  //in list2 and also in list1
            }
        }
        ArrayList list1_clean = new ArrayList<>();
        for (int i=0; i<list1.size(); i++) {
            if (set_dup.add(list1.get(i))) {  //in list1 but not in the duplicated set
                list1_clean.add(list1.get(i));
            }
        }
        return null;
    }




    public static void main(String[] args) {

        int max_len = 50000;
        ArrayList<Integer> list1 = new ArrayList<Integer>();
        ArrayList<Integer> list2 = new ArrayList<Integer>();
        for (int i=0; i<max_len; i++) {
            list1.add((int)(Math.random()*max_len));
            list2.add((max_len/2)+(int)(Math.random()*max_len));
        }

        System.out.printf("list1:%d, list2:%d\n", list1.size(), list2.size());
        long start = System.currentTimeMillis();


        long end = System.currentTimeMillis();
        System.out.printf("time spent : %dms\n", end-start);

    }
}
