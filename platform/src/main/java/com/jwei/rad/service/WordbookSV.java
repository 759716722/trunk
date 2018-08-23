package com.jwei.rad.service;

import com.jwei.rad.entity.RadWordbook;
import com.jwei.sys.entity.SessionUser;

import java.util.List;
import java.util.Map;

/**
 * Created by wyb on 2018/6/26.
 */
public interface WordbookSV {

    public void saveWordbook(RadWordbook info,SessionUser user)throws Exception;

    public void updateWordbook(List<RadWordbook> list)throws Exception;

    public Map getWordbookByPage(Byte type,String name,int start,int end)throws Exception;

    public List<RadWordbook> getWordbookByCond(Byte type,String name)throws Exception;

    public RadWordbook getWordbookById(String id)throws Exception;

    public void deleteWordbook(String id)throws Exception;

}
