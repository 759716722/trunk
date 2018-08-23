package com.jwei.rad.dao;

import com.jwei.rad.entity.RadTypeConfig;

import java.util.List;

public interface RadTypeConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RadTypeConfig record);

    int insertSelective(RadTypeConfig record);

    RadTypeConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RadTypeConfig record);

    int updateByPrimaryKey(RadTypeConfig record);

    List<RadTypeConfig> selectAll();

    int deleteByConfigId(Integer configId);

    int deleteByTypeId(Integer typeId);

}