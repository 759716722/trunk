package com.jwei.api;

import com.jwei.rad.entity.RadProduct;
import com.jwei.rad.entity.RadProductDetail;
import com.jwei.rad.entity.RadTypeConfig;
import com.jwei.rad.entity.RadWordbook;
import com.jwei.rad.service.ProductSV;
import com.jwei.rad.service.WordbookSV;
import com.jwei.sys.entity.SessionUser;
import com.jwei.utils.BaseAction;
import com.jwei.utils.ExceptionAPI;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wyb on 2018/6/27.
 */
@RestController
@RequestMapping("/api/product")
public class ProductAPIAction extends BaseAction {
    @Resource
    private WordbookSV wordbookSV;
    @Resource
    private ProductSV productSV;


    @RequestMapping("/getParam.do")
    public Map<String,Object> getParam(){
        Map<String,Object> resultMap = new HashMap<>();
        try{

            List<RadWordbook> industryList = wordbookSV.getWordbookByCond(Byte.parseByte("1"),null);
            List<RadWordbook> configList = wordbookSV.getWordbookByCond(Byte.parseByte("2"),null);
            List<RadWordbook> typeList = wordbookSV.getWordbookByCond(Byte.parseByte("3"),null);
            List<String> modelList = productSV.getAllModel();

            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("industryList",industryList);
            dataMap.put("configList",configList);
            dataMap.put("typeList",typeList);
            dataMap.put("modelList",modelList);
            resultMap.put("flag","Y");
            resultMap.put("data",toJsonString(dataMap));

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/getProduct.do")
    public Map<String,Object> getProduct(Integer industryId,Integer[] configIds,String model){
        Map<String,Object> resultMap = new HashMap<>();
        try{

            boolean flag = true;
            for(Integer one : configIds){
                if(one!=null){
                    flag = false;
                    break;
                }
            }
            if(flag){
                configIds = null;
            }
            if(StringUtils.isBlank(model)){
                model=null;
            }
            List list = productSV.getProductByAPI(industryId,configIds,model);
            resultMap.put("flag","Y");
            resultMap.put("data",toJsonString(list));

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/getProductById.do")
    public Map<String,Object> getProductById(Integer id){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(id==null){
                throw new ExceptionAPI("参数不能为空");
            }
            List<RadProductDetail> list = productSV.getProductDetailById(id);
            resultMap.put("flag","Y");
            resultMap.put("data",toJsonString(list));

        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }



}
