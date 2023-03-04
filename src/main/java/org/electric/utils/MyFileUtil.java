package org.electric.utils;

import cn.hutool.core.util.HashUtil;
import org.electric.consts.Const;
import org.electric.service.DevicesService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Base64;

public class MyFileUtil {
    public static String genPicData( MultipartFile file){
        try{
            String encode = Base64.getEncoder().encodeToString(file.getBytes());
            String pre = "data:image/png;base64,";
            return pre+encode;
        }catch (Exception e){

        }
        return "";

    }

    public static String getFileName(MultipartFile file){
        String name = file.getOriginalFilename();
        if (name.indexOf("\\") != -1) {
            name = name.substring(name.lastIndexOf("\\"));
        }
        return name;
    }

    public static String genRandomString(String baseStr,int length){
        return HashUtil.rsHash(baseStr)%((int)Math.pow(10,length))+"";
    }

    public static void uploadDevicePic(MultipartFile file, String id){
        try{
            String name = getFileName(file);
            String randomStr = genRandomString(id, Const.RANDOM_GEN_LENGTH);
            name = randomStr+"_"+name;
            String url = Const.DEVICE_PICTURE_UPLOAD_PATH;
            File targetFile = new File(url, name);
            file.transferTo(targetFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        return;
    }

    public static void uploadInspectPic(MultipartFile file, String id){
        try{
            String name = getFileName(file);
            String randomStr = genRandomString(id, Const.RANDOM_GEN_LENGTH);
            name = randomStr+"_"+name;
            String url = Const.INSPECT_UPLOAD_PATH;
            File targetFile = new File(url, name);
            file.transferTo(targetFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        return;
    }

    public static String getUploadDevicePicUrl(String id, DevicesService devicesService){
        String name = devicesService.getPicNameById(id);
        return Const.DEVICE_PICTURE_UPLOAD_PATH+name;
    }


}
