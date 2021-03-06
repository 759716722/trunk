package com.wyb.sys.service.impl;

import com.wyb.sys.dao.SysDeptMapper;
import com.wyb.sys.entity.SessionUser;
import com.wyb.sys.entity.SysDept;
import com.wyb.sys.service.DeptSV;
import com.wyb.utils.ExceptionAPI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by wyb on 2018/6/15.
 */
@Service
public class DeptSVImpl implements DeptSV {
    @Resource
    private SysDeptMapper deptMapper;

    @Override
    public void saveDept(List<SysDept> list,SessionUser user) throws Exception {
        if(list==null){
            throw new ExceptionAPI("保存信息不能为空");
        }
        for(SysDept one : list){
            SysDept oldDept = deptMapper.selectByName(one.getName());
            if(one.getId()==null || one.getId()<1){
                if(oldDept!=null && oldDept.getName().equals(one.getName())){
                    throw new ExceptionAPI("部门["+one.getName()+"]"+"已存在，请核查。");
                }
                one.setCreateId(user.getId());
                one.setCreateName(user.getLoginName());
                one.setCreateDate(new Date());
                deptMapper.insert(one);
            }
        }

    }

    @Override
    public SysDept getDeptById(String id) throws Exception {
        return deptMapper.selectByPrimaryKey(Long.parseLong(id));
    }

    @Override
    public List<SysDept> getDeptByPid(String pid) throws Exception {
        return deptMapper.selectByPid(Long.parseLong(pid));
    }

    @Override
    public List<SysDept> getDeptByCond(String name) throws Exception {
        return null;
    }

    @Override
    public List<SysDept> getAllDept() throws Exception {
        return deptMapper.selectAllDept();
    }

    @Override
    public void deleteDept(String id) throws Exception {
        deptMapper.deleteByIdOrPid(Long.parseLong(id));
    }
}
