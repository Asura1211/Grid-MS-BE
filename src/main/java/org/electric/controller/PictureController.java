package org.electric.controller;

import cn.hutool.core.util.IdUtil;
import lombok.extern.log4j.Log4j2;
import org.electric.model.DevicePicture;
import org.electric.service.DevicePictureService;
import org.electric.utils.Result;
import org.electric.utils.UploadUtil;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,RequestMethod.POST}, origins="*")
@RestController
@Log4j2
public class PictureController extends UploadUtil {
    @Resource

    private DevicePictureService devicePictureService;
    @GetMapping("/admin/device_picture/get_by_name/{name}")
    public Result<DevicePicture> getByName(@PathVariable String name){
        DevicePicture devicePicture = devicePictureService.getByName(name);
        System.out.println(name);
        return new Result<>(200,devicePicture);
    }

    @PostMapping("/admin/device_picture/list_by_all")
    public Result<List<Map<String, String>>> listByAll(){
        List<Map<String, String>> result = devicePictureService.listAll();
        return new Result<>(result.size()>0?200:300,result);
    }

//    private Map<String, String> getParameters(HttpServletRequest request){
//        Map<String, String> params = new HashMap<>();
//        Enumeration<String> paramNames = request.getParameterNames();
//        while (paramNames.hasMoreElements()) {
//            String paramName = paramNames.nextElement();
//            String[] paramValues = request.getParameterValues(paramName);
//            if (paramValues.length == 1) {
//                String paramValue = paramValues[0];
//                if (paramValue.length() != 0) {
//                    params.put(paramName, paramValue);
//                }
//            }
//        }
//        return params;
//    }


//    @Deprecated
//    @PostMapping("/admin/upload_device_picture")
//    public Result<String> save(DevicePicture devicePicture){
//        System.out.println("执行了图片save");
//        String id = IdUtil.simpleUUID();
//        System.out.println(devicePicture.getFile());
//        devicePicture.setId(id);
//        devicePicture.setPicFile(fileUpload(devicePicture.getFile(),"picFile", devicePicture.getPicFile(),id));
////        String fileName = devicePicture.get.getOriginalFilename();
//        boolean result = devicePictureService.save(devicePicture);
//        return new Result<>(result?200:400,result?"添加成功！":"添加失败！");
//    }

}
