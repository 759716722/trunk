package com.jwei.rad.action;

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
@RequestMapping("/rad/product")
public class ProductAction extends BaseAction {
    @Resource
    private WordbookSV wordbookSV;
    @Resource
    private ProductSV productSV;

    @RequestMapping("/saveTypeConfig.do")
    public Map<String,Object> saveTypeConfig(@RequestBody List<RadTypeConfig> list){
        Map<String,Object> resultMap = new HashMap<>();
        SessionUser user = getSessionUser();
        try{
            if(list==null || list.isEmpty()){
                throw new ExceptionAPI("保存信息不能为空");
            }
            for(RadTypeConfig one : list){
                if(one.getTypeId()==null || one.getConfigId()==null){
                    throw new ExceptionAPI("关键参数不能为空");
                }
            }
            productSV.saveTypeConfig(list,user);
            resultMap.put("flag","Y");
            resultMap.put("data","success");

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/getTypeConfig.do")
    public Map<String,Object> getTypeConfig(){
        Map<String,Object> resultMap = new HashMap<>();
        try{

            List<RadWordbook> configList = wordbookSV.getWordbookByCond(Byte.valueOf("2"),null);
            List<RadWordbook> typeList = wordbookSV.getWordbookByCond(Byte.valueOf("3"),null);
            List<RadTypeConfig> typeConfigList = productSV.getTypeConfig();

            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("configList",configList);
            paramMap.put("typeList",typeList);
            paramMap.put("typeConfigList",typeConfigList);

            resultMap.put("flag","Y");
            resultMap.put("data",toJsonString(paramMap));

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }


    @RequestMapping("/saveProduct.do")
    public Map<String,Object> saveProduct(@RequestBody RadProduct info){
        Map<String,Object> resultMap = new HashMap<>();
        SessionUser user = getSessionUser();
        try{
            if(info==null){
                throw new ExceptionAPI("保存信息错误");
            }
            if(info.getTypeId()==null || info.getIndustry()==null || StringUtils.isBlank(info.getModel())){
                throw new ExceptionAPI("关键参数不能为空");
            }
            if(info.getDetailList().isEmpty()){
                throw new ExceptionAPI("配置参数列表不能为空");
            }
            productSV.saveProduct(info,user);

            resultMap.put("flag","Y");
            resultMap.put("data", "success");

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }
    
    @RequestMapping("/getProductConfig.do")
    public Map<String,Object> getProductConfig(Integer productId,Integer typeId){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(typeId==null){
                throw new ExceptionAPI("请选择类型");
            }
            List list = productSV.getProductConfig(productId,typeId);

            resultMap.put("flag","Y");
            resultMap.put("data",toJsonString(list));

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/getProductByPage.do")
    public Map<String,Object> getProductByPage(Integer typeId,Integer industry,String model,int onePageNum,int pageNo){
        Map<String,Object> resultMap = new HashMap<>();
        try{

            if(StringUtils.isBlank(model)){
                model = null;
            }
            int start = onePageNum*(pageNo-1)+1;
            int end = onePageNum*pageNo;
            Map dataMap = productSV.getProductByPage(typeId,industry,model,start,end);

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

    @RequestMapping("/getProductById.do")
    public Map<String,Object> getProductById(Integer id){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(id==null){
                throw new ExceptionAPI("参数不能为空");
            }
            Map dataMap = productSV.getProductById(id);
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

    @RequestMapping("/doDeleteProduct.do")
    public Map<String,Object> doDeleteProduct(Integer id){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            if(id==null){
                throw new ExceptionAPI("参数不能为空");
            }
            productSV.deleteProduct(id);
            resultMap.put("flag","Y");
            resultMap.put("data","success");

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put("flag","N");
            resultMap.put("data",e.getMessage());
        }
        return resultMap;
    }


}
