package com.jwei.sys.service;

import com.jwei.sys.entity.SessionUser;
import com.jwei.sys.entity.SysDept;

import java.util.List;

/**
 * Created by wyb on 2018/6/15.
 */
public interface DeptSV {

    public void saveDept(List<SysDept> list,SessionUser user)throws Exception;

    public SysDept getDeptById(String id) throws Exception;

    public List<SysDept> getDeptByPid(String pid)throws Exception;

    public List<SysDept> getDeptByCond(String name)throws Exception;

    public List<SysDept> getAllDept()throws Exception;

    public void deleteDept(String id) throws Exception;
}
