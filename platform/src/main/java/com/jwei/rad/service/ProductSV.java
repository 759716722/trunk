package com.jwei.rad.service;

import com.jwei.rad.entity.RadProduct;
import com.jwei.rad.entity.RadProductDetail;
import com.jwei.rad.entity.RadTypeConfig;
import com.jwei.sys.entity.SessionUser;

import java.util.List;
import java.util.Map;

/**
 * Created by wyb on 2018/6/26.
 */
public interface ProductSV {

    public void saveTypeConfig(List<RadTypeConfig> list,SessionUser user)throws Exception;

    public List<RadTypeConfig> getTypeConfig()throws Exception;

    public List getProductConfig(Integer productId,Integer typeId)throws Exception;

    /**
     * 描述: 获取所有的产品信息，用户查询时选择，后期数据量大可改为select2方式
     * @author: wyb
     * @date: 2018/7/10
     */
    public List<String> getAllModel()throws Exception;

    public void saveProduct(RadProduct info,SessionUser user)throws Exception;

    public Map getProductByPage(Integer typeId,Integer industry,String model,int start,int end)throws Exception;

    public Map getProductById(Integer id)throws Exception;

    public void deleteProduct(Integer id)throws Exception;

    public List getProductByAPI(Integer industryId,Integer[] configIds,String model);

    public List<RadProductDetail> getProductDetailById(Integer productId);

}
