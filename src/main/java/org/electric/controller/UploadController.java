package org.electric.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.json.JSONConverter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.electric.consts.Const;
import org.electric.dao.DetectedDevicePictureDao;
import org.electric.model.*;
import org.electric.service.DevicePictureService;
import org.electric.service.ExceptionRecordService;
import org.electric.service.FerignYoloDetectService;
import org.electric.tool.DeleteTool;
import org.electric.tool.ReadFileTool;
import org.electric.utils.MyFileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.electric.consts.Const.OUT_PUT_DETECTED_DEVICE_AND_QUALITY_PICTURE_ROOT;
import static sun.misc.Version.print;
import static sun.misc.Version.println;

@CrossOrigin // 跨域
@Controller
@RequestMapping("admin")
public class UploadController {


    @Resource
    private DevicePictureService devicePictureService;
    //private DetectedDevicePictureService detectedDevicePictureService;
    @Resource
    private ExceptionRecordService exceptionRecordService;

    @Autowired
    private DetectedDevicePictureDao detectedDevicePictureDao;

    @Autowired
    private FerignYoloDetectService ferignYoloDetectService;

    @RequestMapping("upload_device_picture")
    @ResponseBody
    public Map<String, String> uploadDevicePicture(@RequestParam("file") MultipartFile[] files, @RequestParam("id") String tempId) {
        Map<String, String> map = new HashMap<>();
        for (MultipartFile file : files) {              //这里只有一个file
            FileOutputStream out = null;
            try {
                String name = file.getOriginalFilename();
//                name = tempId+name;                             //加上一个随机数 这个数是前端生成的
                if (name.indexOf("\\") != -1) {
                    name = name.substring(name.lastIndexOf("\\"));
                }
                // 获取文件存放地址
                String url = Const.DEVICE_AND_QUALITY_PICTURE_ROOT;
                File targetFile = new File(url, name);
                url = url + name;

                String id = IdUtil.simpleUUID();
                DevicePicture devicePicture = new DevicePicture();
                String encode = Base64.getEncoder().encodeToString(file.getBytes());
                String pre = "data:image/png;base64,";
                devicePicture.setId(id);
                devicePicture.setName(name);
//                devicePicture.setBinData(pre + encode);       //不在数据库直接存储图片
                devicePicture.setBinData("");
                devicePictureService.save(devicePicture);
                file.transferTo(targetFile); // 存入data路径
                File destFile = new File(Const.TEMP_DEVICE_BEFORE_PATH,name);
//                Files.copy(targetFile.toPath(),destFile.toPath());
                if(!destFile.exists()){
                    Files.copy(targetFile.toPath(),destFile.toPath());
                }
                /**
                 * -------分界线---------
                 */
            } catch (Exception e) {
                map.clear();
                map.put("url", "");
                return map;
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return map;
    }
//    public List<String> uploadDevicePicture(@RequestParam("file") MultipartFile[] files, @RequestParam("id") String tempId) {
//        //Map<String, String> map = new HashMap<>();
//        List<String> fileList = new ArrayList<>();
//        for (MultipartFile file : files) {              //这里只有一个file
//            FileOutputStream out = null;
//            try {
//                String name = file.getOriginalFilename();
////                name = tempId+name;                             //加上一个随机数 这个数是前端生成的
//                if (name.indexOf("\\") != -1) {
//                    name = name.substring(name.lastIndexOf("\\"));
//                }
//                // 获取文件存放地址
//                String url = Const.DEVICE_AND_QUALITY_PICTURE_ROOT;
//                File targetFile = new File(url, name);
//                url = url + name;
//                fileList.add(url);
//
//                String id = IdUtil.simpleUUID();
//                DevicePicture devicePicture = new DevicePicture();
//                String encode = Base64.getEncoder().encodeToString(file.getBytes());
//                String pre = "data:image/png;base64,";
//                devicePicture.setId(id);
//                devicePicture.setName(name);
////                devicePicture.setBinData(pre + encode);       //不在数据库直接存储图片
//                devicePicture.setBinData("");
//                //devicePictureService.save(devicePicture);
//                file.transferTo(targetFile); // 存入data路径
////                File destFile = new File(Const.TEMP_DEVICE_BEFORE_PATH,name);
//////                Files.copy(targetFile.toPath(),destFile.toPath());
////                if(!destFile.exists()){
////                    Files.copy(targetFile.toPath(),destFile.toPath());
////                }
//                /**
//                 * -------分界线---------
//                 */
//            } catch (Exception e) {
////                map.clear();
////                map.put("url", "");
////                return map;
//            } finally {
//                if (out != null) {
//                    try {
//                        out.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        return fileList;
//    }
    @RequestMapping("device_detect")
    @ResponseBody
    List<Map<String, Object>> deviceDetect(){
        ArrayList<Map<String,Object>> result = new ArrayList<>();
        String detectedResult = ferignYoloDetectService.detectImage();
        HashMap<String,Object> resultMap = JSON.parseObject(detectedResult,HashMap.class);
//        /**
//         * 根据识别结果添加新设备到数据库
//         */
//        for(String key:resultMap.keySet()){
//            HashMap<String,String> singleResult = (HashMap)resultMap.get(key);
//            for(String deviceName :singleResult.keySet()){
//                if(!deviceName.equals("二维码")){
//                    int num = Integer.parseInt(singleResult.get(deviceName));
//                    for(int j = 0;j<num;j++){
//                        Devices devices = new Devices();
//                        String id = IdUtil.simpleUUID();
//                        devices.setId(id);
//                        String saveName = MyFileUtil.genRandomString(id,Const.RANDOM_GEN_LENGTH);
//                        QrCodeUtil.generate(id, 300, 300, FileUtil.file(Const.QR_UPLOAD_PATH +"/"+ saveName));
//                    }
//
//                }
//            }
//        }

        ArrayList<HashMap<String,String>>picInfo = new ArrayList<>();
        ReadFileTool readFileTool = new ReadFileTool();
        readFileTool.readPic(new File(OUT_PUT_DETECTED_DEVICE_AND_QUALITY_PICTURE_ROOT));
        picInfo = readFileTool.getPicInfo();
        String pre = "data:image/png;base64,";
        for(HashMap<String,String> singlePicInfo:picInfo){
            byte[] data_ = null;
            try {
                InputStream in = new FileInputStream(singlePicInfo.get("url"));
                data_ = new byte[in.available()];
                in.read(data_);
                in.close();
                File source = new File(singlePicInfo.get("url"));
                File dest = new File(Const.TEMP_DEVICE_AFTER_PATH+singlePicInfo.get("name"));
                if(!dest.exists()){
                    Files.copy(source.toPath(),dest.toPath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            String d_enconde = Base64.getEncoder().encodeToString(data_);
            String d_id = IdUtil.simpleUUID();
            DetectedDevicePicture detectedDevicePicture = new DetectedDevicePicture();
            detectedDevicePicture.setId(d_id);
            detectedDevicePicture.setName(singlePicInfo.get("name"));
            //detectedDevicePicture.setDevicePictureId(id);
            detectedDevicePicture.setDetectedBinData(pre + d_enconde);
            Map<String,Object>singlePic = new HashMap<>();
            singlePic.put("binData",detectedDevicePicture.getDetectedBinData());
            singlePic.put("name",detectedDevicePicture.getName());
            singlePic.put("result",resultMap);
            result.add(singlePic);
        }

        //删除临时文件
        File file1 = new File(Const.DEVICE_AND_QUALITY_PICTURE_ROOT);
        File file2 = new File(OUT_PUT_DETECTED_DEVICE_AND_QUALITY_PICTURE_ROOT);
        DeleteTool deleteTool = new DeleteTool();
        deleteTool.DeleteTool(file2);
//        deleteTool.DeleteToolNotIncludingDirectory(file1);
        return result;
    }

    @RequestMapping("delete_temp_pic")
    @ResponseBody
    boolean deleteTempPic (){
        //删除临时文件
        File file1 = new File(Const.DEVICE_AND_QUALITY_PICTURE_ROOT);
        File file2 = new File(OUT_PUT_DETECTED_DEVICE_AND_QUALITY_PICTURE_ROOT);
        DeleteTool deleteTool = new DeleteTool();
        deleteTool.DeleteTool(file2);
        deleteTool.DeleteToolNotIncludingDirectory(file1);
        return true;
    }


    @RequestMapping("quality_detect")
    @ResponseBody
    List<Map<String, Object>> qualityDetect(){
        ArrayList<Map<String,Object>> result = new ArrayList<>();
        String detectedResult = ferignYoloDetectService.findException();
        HashMap<String,Object> resultMap = JSON.parseObject(detectedResult,HashMap.class);

        for(String key:resultMap.keySet()){
            JSONObject jsonObject = (JSONObject) resultMap.get(key);
            ExceptionRecord exceptionRecord = new ExceptionRecord() ;
            for(String exceptionName :jsonObject.keySet()){       //一张图片内出现的异常
                String num = (String )jsonObject.get(exceptionName);
                if(exceptionName.equals("Error")){
                    exceptionRecord.setErrorCnt(num);
                }else if(exceptionName.equals("电线缠绕")){
                    exceptionRecord.setWireWinding(num);
                }else if(exceptionName.equals("电表异常姿态")){
                    exceptionRecord.setAbnormalPosture(num);
                }else if(exceptionName.equals("无示数")){
                    exceptionRecord.setNoReadings(num);
                }
            }
//            HashMap<String,String> singleResult = new HashMap<>();
//            JSONObject jsonObject = (JSONObject) resultMap.get(key);
////            Iterator it = jsonObject.keys();
////            while (it.hasNext()){
////                String k = String.valueOf(it.next());
////                String v = (String)(jsonObject.get(k));
////                singleResult.put(k,v);
////            }
//            ExceptionRecord exceptionRecord = new ExceptionRecord() ;
//            for(String exceptionName :singleResult.keySet()){       //一张图片内出现的异常
//                String num = singleResult.get(exceptionName);
//                if(exceptionName.equals("Error")){
//                    exceptionRecord.setError(num);
//                }else if(exceptionName.equals("电线缠绕")){
//                    exceptionRecord.setWireWinding(num);
//                }else if(exceptionName.equals("电表异常姿态")){
//                    exceptionRecord.setAbnormalPosture(num);
//                }else if(exceptionName.equals("无示数")){
//                    exceptionRecord.setNoReadings(num);
//                }
//            }
            Date date = new Date();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            exceptionRecord.setTimeRecord(format.format(date));
            exceptionRecordService.save(exceptionRecord);

        }
        ArrayList<HashMap<String,String>>picInfo = new ArrayList<>();
        ReadFileTool readFileTool = new ReadFileTool();
        readFileTool.readPic(new File(OUT_PUT_DETECTED_DEVICE_AND_QUALITY_PICTURE_ROOT));
        picInfo = readFileTool.getPicInfo();
//        readFileTool.clear();
//        readFileTool.readFile(new File(Const.DEVICE_PICTURE_ROOT));

        String pre = "data:image/png;base64,";
        for(HashMap<String,String> singlePicInfo:picInfo){
            byte[] data_ = null;
            try {
                InputStream in = new FileInputStream(singlePicInfo.get("url"));
                data_ = new byte[in.available()];
                in.read(data_);
                in.close();
                File source = new File(singlePicInfo.get("url"));
                File dest = new File(Const.TEMP_QUALITY_AFTER_PATH+singlePicInfo.get("name"));
                if(!dest.exists()){
                    Files.copy(source.toPath(),dest.toPath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String d_enconde = Base64.getEncoder().encodeToString(data_);
            String d_id = IdUtil.simpleUUID();
            DetectedQualityPicture detectedQualityPicture = new DetectedQualityPicture();
            detectedQualityPicture.setId(d_id);
            detectedQualityPicture.setName(singlePicInfo.get("name"));
            //detectedDevicePicture.setDevicePictureId(id);
            detectedQualityPicture.setDetectedBinData(pre + d_enconde);
            Map<String,Object>singlePic = new HashMap<>();
            singlePic.put("binData",detectedQualityPicture.getDetectedBinData());
            singlePic.put("name",detectedQualityPicture.getName());
            singlePic.put("result",resultMap);
            result.add(singlePic);
        }
        //删除临时文件
        File file1 = new File(Const.DEVICE_AND_QUALITY_PICTURE_ROOT);
        File file2 = new File(OUT_PUT_DETECTED_DEVICE_AND_QUALITY_PICTURE_ROOT);

        DeleteTool deleteTool = new DeleteTool();
        deleteTool.DeleteTool(file2);
//        deleteTool.DeleteToolNotIncludingDirectory(file1);
        return result;
    }

    @RequestMapping("upload_quality_picture")
    @ResponseBody
    public Map<String, String> uploadQualityPicture(@RequestParam("file") MultipartFile[] files, @RequestParam("id") String tempId) {
        Map<String, String> map = new HashMap<>();
        for (MultipartFile file : files) {              //这里只有一个file
            FileOutputStream out = null;
            try {
                String name = file.getOriginalFilename();
//                name = tempId+name;                             //加上一个随机数 这个数是前端生成的
                if (name.indexOf("\\") != -1) {
                    name = name.substring(name.lastIndexOf("\\"));
                }
                // 获取文件存放地址
                String url = Const.DEVICE_AND_QUALITY_PICTURE_ROOT;
                File targetFile = new File(url, name);
                url = url + name;
                String id = IdUtil.simpleUUID();
                QualityPicture qualityPicture = new QualityPicture();
                String encode = Base64.getEncoder().encodeToString(file.getBytes());
                String pre = "data:image/png;base64,";
                qualityPicture.setId(id);
                qualityPicture.setName(name);
                qualityPicture.setBinData("");
//
                file.transferTo(targetFile); // 存入data路径
                File destFile = new File(Const.TEMP_QUALITY_BEFORE_PATH,name);
//                Files.copy(targetFile.toPath(),destFile.toPath());
                if(!destFile.exists()){
                    Files.copy(targetFile.toPath(),destFile.toPath());
                }
                /**
                 * -------分界线---------
                 */


            } catch (Exception e) {
                map.clear();
                map.put("url", "");
                return map;
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return map;
    }


    @RequestMapping("find_exception")
    @ResponseBody
    public Map<String,String> findException(){
        ferignYoloDetectService.findException();
        return new HashMap<>();
    }


    @RequestMapping("gfb_detect")
    @ResponseBody
    List<GfbDetectResult> gfbDetect() throws Exception{
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin","*");

        Random rd = new Random();
        List<GfbDetectResult> gfbDetectResultList = new ArrayList<>();
        String url = "F:\\study and research\\yolo\\update_yolo\\front\\glass_management\\src\\assets\\pic\\result_imgs\\";
        String newUrl = "../../src/assets/pic/result_imgs/";
        File f = new File(url);
        File fa[] = f.listFiles();//用数组接收  F:笔记总结C#, F:笔记总结if语句.txt
        //for(File fs : fa)
        for (int i = 0; i < fa.length; i++) {//循环遍历
            File fs = fa[i];//获取数组中的第i个
            GfbDetectResult gfbDetectResult = new GfbDetectResult();
            gfbDetectResult.setName(fs.getName());
            if(i ==0) {
                gfbDetectResult.setNum(95);
            }
            if(i ==1){
                gfbDetectResult.setNum(342);
            }
            if(i == 2 ){
                gfbDetectResult.setNum(246);
            }
//            FileInputStream fin = new FileInputStream(fs);
//            byte[] bytes = new byte[fin.available()];
//            fin.read(bytes);
            gfbDetectResult.setUrl(newUrl+fs.getName());
            gfbDetectResultList.add(gfbDetectResult);

        }
        return gfbDetectResultList;

    }

    @RequestMapping("upload_gfb_picture")
    @ResponseBody
    public List<GfbUploadResult> uploadGfbPicture(@RequestParam("file") MultipartFile[] files, @RequestParam("id") String tempId) {

        List<GfbUploadResult> fileList = new ArrayList<>();
        for (MultipartFile file : files) {              //这里只有一个file
            FileOutputStream out = null;
            try {
                String name = file.getOriginalFilename();
//                name = tempId+name;                             //加上一个随机数 这个数是前端生成的
                if (name.indexOf("\\") != -1) {
                    name = name.substring(name.lastIndexOf("\\"));
                }
                // 获取文件存放地址
                String url = Const.DEVICE_AND_QUALITY_PICTURE_ROOT;
                File targetFile = new File(url, name);
                url = url + name;
                GfbUploadResult gfbUploadResult = new GfbUploadResult();
                gfbUploadResult.setName(name);
                gfbUploadResult.setUrl(url);
                fileList.add(gfbUploadResult);

                String id = IdUtil.simpleUUID();
                DevicePicture devicePicture = new DevicePicture();
                String encode = Base64.getEncoder().encodeToString(file.getBytes());
                String pre = "data:image/png;base64,";
                devicePicture.setId(id);
                devicePicture.setName(name);
//                devicePicture.setBinData(pre + encode);       //不在数据库直接存储图片
                devicePicture.setBinData("");
                //devicePictureService.save(devicePicture);
                file.transferTo(targetFile); // 存入data路径
//                File destFile = new File(Const.TEMP_DEVICE_BEFORE_PATH,name);
////                Files.copy(targetFile.toPath(),destFile.toPath());
//                if(!destFile.exists()){
//                    Files.copy(targetFile.toPath(),destFile.toPath());
//                }
                /**
                 * -------分界线---------
                 */
            } catch (Exception e) {
//                map.clear();
//                map.put("url", "");
//                return map;
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return fileList;

    }


    @RequestMapping("upload_dl_picture")
    @ResponseBody
    public List<GfbUploadResult> uploadWirePicture(@RequestParam("file") MultipartFile[] files, @RequestParam("id") String tempId) {

        List<GfbUploadResult> fileList = new ArrayList<>();
        for (MultipartFile file : files) {              //这里只有一个file
            FileOutputStream out = null;
            try {
                String name = file.getOriginalFilename();
//                name = tempId+name;                             //加上一个随机数 这个数是前端生成的
                if (name.indexOf("\\") != -1) {
                    name = name.substring(name.lastIndexOf("\\"));
                }
                // 获取文件存放地址
                String url = Const.WIRE_UPLOAD_IMAGE_ROOT;
                File targetFile = new File(url, name);
                url = url + name;
                GfbUploadResult gfbUploadResult = new GfbUploadResult();
                gfbUploadResult.setName(name);
                gfbUploadResult.setUrl(url);
                fileList.add(gfbUploadResult);

                String id = IdUtil.simpleUUID();
                DevicePicture devicePicture = new DevicePicture();
                String encode = Base64.getEncoder().encodeToString(file.getBytes());
                String pre = "data:image/png;base64,";
                devicePicture.setId(id);
                devicePicture.setName(name);
//                devicePicture.setBinData(pre + encode);       //不在数据库直接存储图片
                devicePicture.setBinData("");
                //devicePictureService.save(devicePicture);
                file.transferTo(targetFile); // 存入data路径
//                File destFile = new File(Const.TEMP_DEVICE_BEFORE_PATH,name);
////                Files.copy(targetFile.toPath(),destFile.toPath());
//                if(!destFile.exists()){
//                    Files.copy(targetFile.toPath(),destFile.toPath());
//                }
                /**
                 * -------分界线---------
                 */
            } catch (Exception e) {
//                map.clear();
//                map.put("url", "");
//                return map;
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return fileList;

    }

    @RequestMapping("dl_detect")
    @ResponseBody
    List<GfbDetectResult> wireDetect() throws Exception{
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin","*");

        Random rd = new Random();
        String result = ferignYoloDetectService.wireDetect();
        List<GfbDetectResult> gfbDetectResultList = new ArrayList<>();
        String url = Const.WIRE_DETECTED_PICTURE_ROOT;
        String newUrl = "../../src/assets/pic/result_imgs/";
        File f = new File(url);
        File fa[] = f.listFiles();//用数组接收  F:笔记总结C#, F:笔记总结if语句.txt
        //for(File fs : fa)
        for (int i = 0; i < fa.length; i++) {//循环遍历
            File fs = fa[i];//获取数组中的第i个
            GfbDetectResult gfbDetectResult = new GfbDetectResult();
            gfbDetectResult.setName(fs.getName());
            gfbDetectResult.setNum(1);
//            FileInputStream fin = new FileInputStream(fs);
//            byte[] bytes = new byte[fin.available()];
//            fin.read(bytes);
            gfbDetectResult.setUrl(newUrl+fs.getName());
            gfbDetectResultList.add(gfbDetectResult);

        }
        return gfbDetectResultList;

    }






}
