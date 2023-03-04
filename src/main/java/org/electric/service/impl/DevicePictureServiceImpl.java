package org.electric.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.electric.mapper.DevicePictureMapper;
import org.electric.model.DevicePicture;
import org.electric.service.DevicePictureService;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class DevicePictureServiceImpl extends ServiceImpl<DevicePictureMapper, DevicePicture> implements
        DevicePictureService {
    @Resource
    private DevicePictureMapper devicePictureMapper;

    @Override
    public DevicePicture getByName(String name) {
        return devicePictureMapper.getByName(name);
    }

    @Override
    public Object personImgs() {
        File file = new File("D:\\code\\img\\1.jpg");
//        String base = getBaseImg(file);
//        return base;
        return file;
    }

    @Override
    public List<Map<String, String>> listAll() {
        return devicePictureMapper.listAll();
    }

    /**
     * 将图片base64转码
     *
     * @param imgPath 图片路径
     * @return base64编码
     */
    public String getBaseImg(String imgPath) {
        InputStream in;
        byte[] data = null;
        try {
            in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //进行Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
}
