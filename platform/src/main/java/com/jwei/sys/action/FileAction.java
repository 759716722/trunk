package com.jwei.sys.action;

import com.jwei.utils.BaseAction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wyb on 2018/8/23.
 */
@RestController
@RequestMapping("/file")
public class FileAction extends BaseAction{

    private final String saveBasePath = "F:/PlatformFile/";
    private final String getBasePath = "F:/PlatformFile/";

    @RequestMapping(value="/uploadFile.do",method=RequestMethod.POST)
    public Map<String,Object> uploadFile(String url,String fileName,@RequestParam("files")MultipartFile[] files)throws Exception{
        Map<String,Object> resultMap = new HashMap<>();
        try{
            long startTime=System.currentTimeMillis();   //获取开始时间
            if(files==null){
                return null;
            }
            for(MultipartFile file : files){
                if(!file.isEmpty()){
                    String path = saveBasePath+url;
                    File dir = new File(path);
                    System.out.println(dir.isDirectory());
                    if(!dir.isDirectory()){
                        dir.mkdirs();
                    }
                    //保存文件
                    file.transferTo(new File(path+file.getOriginalFilename()));
                }
            }
            long endTime=System.currentTimeMillis(); //获取结束时间
            System.out.println("上传文件共使用时间："+(endTime-startTime));

            resultMap.put("name", fileName);
            resultMap.put("path", saveBasePath);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/downloadFile.do")
    public void downloadFile(String url,String isOnLine,HttpServletResponse response) {

        try {
            String realUrl = getBasePath+url;

            File f = new File(realUrl);
            if (!f.exists()) {
                response.sendError(404, "File not found!");
                return;
            }
            // 处理中文文件名乱码问题
            String fileName = f.getName();
            fileName = URLEncoder.encode(fileName, "UTF-8");
            /*fileName = StringUtils.replace(fileName, "+", "%20");
            if (fileName.length() > 150) {
                fileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
                fileName = StringUtils.replace(fileName, " ", "%20");
            }*/

            response.reset(); // 清空response
            //设置输出不缓存
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            if ("1".equals(isOnLine)) {  // 在线打开方式
//                response.setContentType("application/x-msdownload");
                // 设置response的Header
                response.setHeader("Content-Disposition", "inline; filename=" + fileName);
                response.setCharacterEncoding("UTF-8");
            } else {  // 下载方式
                response.setContentType("application/x-msdownload");
                // 设置response的Header
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                response.setCharacterEncoding("UTF-8");
            }
            /*attachment是以附件下载的形式，inline是以线上浏览的形式。
            attachment是在本地机里打开，inline是在浏览器里打开。*/

            // 读取内容并输出
            BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
            OutputStream out = response.getOutputStream();
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = br.read(buf)) > 0){
                out.write(buf, 0, len);
            }
            out.flush();
            br.close();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
