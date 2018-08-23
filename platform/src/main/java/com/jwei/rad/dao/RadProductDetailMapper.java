package com.jwei.rad.dao;

import com.jwei.rad.entity.RadProductDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RadProductDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RadProductDetail record);

    int insertSelective(RadProductDetail record);

    RadProductDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RadProductDetail record);

    int updateByPrimaryKey(RadProductDetail record);

    List<RadProductDetail> selectByProductId(Integer productId);

    /**
     * 描述: 根据产品ID获取当前产品配置时调用该方法，产品配置取决于机型配置，所以需要关联机型配置表。
     * @author: wyb
     * @date: 2018/6/28
     */
    List selectByProductIdAndTypeId(@Param("productId")Integer productId,@Param("typeId")Integer typeId);

    int deleteByProductId(Integer productId);

    int deleteByConfigId(Integer configId);

}