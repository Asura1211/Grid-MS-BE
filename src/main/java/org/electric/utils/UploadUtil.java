package org.electric.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @version 1.0
 * @description //TODO 文件上传
 **/
@Log4j2
public class UploadUtil {

    @Value("${spring.servlet.upload.drive.letter}")
    private String driveLetter;
    @Value("${spring.servlet.upload.base-path}")
    private String uploadPath;

    /**
     * @description  图片上传
     * @param file 文件
     * @param path 上传路径
     * @param oldName 旧文件名
     * @param newName 新文件名
     * @return java.lang.String 图片名
     */
    protected String fileUpload(MultipartFile file, String path, String oldName, String newName){
        String fileName = "";
        if (null!=file && (!file.isEmpty())) {
            String originalName = file.getOriginalFilename();
            fileName = newName+originalName.substring(originalName.lastIndexOf("."));
            File dest = new File(driveLetter+":\\" + uploadPath + "\\" + path + "\\" + fileName);
            File old = new File(driveLetter+":\\" + uploadPath + "\\" + path + "\\" + oldName);
            if(old.isFile()&&old.exists()){
                old.delete();
            }
            if(!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
                log.info("上传成功");
            } catch (IOException e) {
                log.error(e.toString(), e);
            }
        }
        return fileName;
    }

    /**
     * @description  删除文件
     * @param path 上传文件路径
     * @param filename 文件名
     */
    protected static void deleteFile(String path,String filename) {
        File file = new File(path);
        File temp = null;
        File[] filelist = file.listFiles();
        for (int i = 0; i < filelist.length; i++) {
            temp = filelist[i];
            if (temp.getName().substring(0,temp.getName().indexOf(".")).equals(filename)){
                temp.delete();
                log.info("删除成功！");
            }
        }
    }

    public static String upload(MultipartFile multipartFile) {
        // 文件存储位置，文件的目录要存在才行，可以先创建文件目录，然后进行存储
        String filePath = "/Users/muzhikid/IdeaProjects/design_electric/img/" + multipartFile.getOriginalFilename();
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 文件存储
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
}
