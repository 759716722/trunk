package com.jwei.rad.service.impl;

import com.jwei.rad.dao.RadProductDetailMapper;
import com.jwei.rad.dao.RadProductMapper;
import com.jwei.rad.dao.RadTypeConfigMapper;
import com.jwei.rad.entity.RadProduct;
import com.jwei.rad.entity.RadProductDetail;
import com.jwei.rad.entity.RadTypeConfig;
import com.jwei.rad.service.ProductSV;
import com.jwei.sys.entity.SessionUser;
import com.jwei.utils.ExceptionAPI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by wyb on 2018/6/27.
 */
@Service
public class ProductSVImpl implements ProductSV {
    @Resource
    private RadTypeConfigMapper typeConfigMapper;
    @Resource
    private RadProductMapper productMapper;
    @Resource
    private RadProductDetailMapper productDetailMapper;

    @Override
    public void saveTypeConfig(List<RadTypeConfig> list, SessionUser user) throws Exception {
        List<RadTypeConfig> oldList = typeConfigMapper.selectAll();
        List<RadTypeConfig> copyList = new ArrayList(Arrays.asList(new RadTypeConfig[list.size()]));
        Collections.copy(copyList, list);
        //add
        list.removeAll(oldList);
        for(RadTypeConfig one:list){
            one.setCreateId(user.getId());
            one.setCreateName(user.getLoginName());
            one.setCreateDate(new Date());
            typeConfigMapper.insert(one);
        }
        //delete
        oldList.removeAll(copyList);
        for(RadTypeConfig one:oldList){
            typeConfigMapper.deleteByPrimaryKey(one.getId());
            productDetailMapper.deleteByConfigId(one.getConfigId());
        }
    }

    @Override
    public List<RadTypeConfig> getTypeConfig() throws Exception {
        return typeConfigMapper.selectAll();
    }

    @Override
    public List getProductConfig(Integer productId,Integer typeId) throws Exception {
        return productDetailMapper.selectByProductIdAndTypeId(productId,typeId);
    }

    @Override
    public List<String> getAllModel() throws Exception {
        return productMapper.selectAllModel();
    }

    @Override
    public void saveProduct(RadProduct info, SessionUser user) throws Exception {
        List<RadProductDetail> detailList = info.getDetailList();
        RadProduct oldInfo = productMapper.selectByIndustryAndModel(info.getIndustry(), info.getModel());
        if(info.getId()==null || info.getId()<1){
            if(oldInfo!=null){
                throw new ExceptionAPI("该行业型号["+info.getModel()+"]已存在，请核查。");
            }
            info.setCreateId(user.getId());
            info.setCreateName(user.getLoginName());
            info.setCreateDate(new Date());
            productMapper.insert(info);
            info = productMapper.selectByIndustryAndModel(info.getIndustry(),info.getModel());

            // detail
            for(RadProductDetail one : detailList){
                one.setProductId(info.getId());
                productDetailMapper.insert(one);
            }

        }else{
            if(oldInfo!=null && !info.getId().equals(oldInfo.getId())){
                throw new ExceptionAPI("该行业型号["+info.getModel()+"]已存在，请核查。");
            }
            info.setModifyId(user.getId());
            info.setModifyName(user.getLoginName());
            info.setModifyDate(new Date());
            productMapper.updateByPrimaryKeySelective(info);

            // detail
            List<RadProductDetail> oldDetailList = productDetailMapper.selectByProductId(info.getId());
            List<RadProductDetail> copyList = new ArrayList<>(Arrays.asList(new RadProductDetail[detailList.size()]));
            Collections.copy(copyList,detailList);

            // add
            detailList.removeAll(oldDetailList);
            for(RadProductDetail one: detailList){
                one.setProductId(info.getId());
                productDetailMapper.insert(one);
            }

            // delete
            oldDetailList.removeAll(copyList);
            for(RadProductDetail one : oldDetailList){
                productDetailMapper.deleteByPrimaryKey(one.getId());
            }


        }

    }

    @Override
    public Map getProductByPage(Integer typeId,Integer industry,String model,int start,int end) throws Exception {

        List<RadProduct> list = productMapper.selectByPage(typeId,industry,model,start,end);
        int count = productMapper.countByPage(typeId,industry,model);
        Map dataMap = new HashMap<>();
        dataMap.put("dataList",list);
        dataMap.put("dataCount",count);
        return dataMap;
    }

    @Override
    public Map getProductById(Integer id) throws Exception {
        RadProduct product = productMapper.selectByPrimaryKey(id);
        if(product==null){
            throw new ExceptionAPI("该型号产品不存在，请核查。");
        }
        List detailList = productDetailMapper.selectByProductIdAndTypeId(id,product.getTypeId());
        Map dataMap = new HashMap<>();
        dataMap.put("product",product);
        dataMap.put("detailList",detailList);
        return dataMap;
    }

    @Override
    public void deleteProduct(Integer id) throws Exception {
        productMapper.deleteByPrimaryKey(id);
        productDetailMapper.deleteByProductId(id);
    }

    @Override
    public List getProductByAPI(Integer industryId, Integer[] configIds,String model) {
        return productMapper.selectByAPI(industryId,configIds,model);
    }

    @Override
    public List<RadProductDetail> getProductDetailById(Integer productId) {
        return productDetailMapper.selectByProductId(productId);
    }
}
