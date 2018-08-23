package com.jwei.rad.dao;

import com.jwei.rad.entity.RadProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RadProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RadProduct record);

    int insertSelective(RadProduct record);

    RadProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RadProduct record);

    int updateByPrimaryKey(RadProduct record);

    RadProduct selectByIndustryAndModel(@Param("industry")Integer industry,@Param("model")String model);

    List<String> selectAllModel();

    List<RadProduct> selectByPage(@Param("typeId")Integer typeId,@Param("industry")Integer industry,
                                  @Param("model")String model,@Param("start")int start,@Param("end")int end);
    int countByPage(@Param("typeId")Integer typeId,@Param("industry")Integer industry,@Param("model")String model);

    List selectByAPI(@Param("industryId")Integer industryId,@Param("configIds")Integer[] configIds,@Param("model")String model);




}