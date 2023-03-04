package org.electric.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.electric.model.DevicePicture;

import java.util.List;
import java.util.Map;

@Mapper
public interface DevicePictureMapper extends BaseMapper<DevicePicture> {
    DevicePicture getByName(String name);
    List<Map<String, String>> listAll();
}
