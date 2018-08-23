package com.jwei.rad.dao;

import com.jwei.rad.entity.RadWordbook;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RadWordbookMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RadWordbook record);

    int insertSelective(RadWordbook record);

    RadWordbook selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RadWordbook record);

    int updateByPrimaryKey(RadWordbook record);


    RadWordbook selectByTypeAndName(@Param("type")Byte type,@Param("name")String name);

    List selectByPage(@Param("type")Byte type,@Param("name")String name,
                                   @Param("start")int start,@Param("end")int end);
    int countByPage(@Param("type")Byte type,@Param("name")String name);

    List<RadWordbook> selectByCond(@Param("type")Byte type, @Param("name")String name);

    int batchUpdateById(List<RadWordbook> list);


}