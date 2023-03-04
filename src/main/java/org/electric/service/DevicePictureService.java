package org.electric.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.electric.model.DevicePicture;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface DevicePictureService extends IService<DevicePicture> {
    DevicePicture getByName(String name);
    Object personImgs() ;
    List<Map<String, String>> listAll();
    ;
}
