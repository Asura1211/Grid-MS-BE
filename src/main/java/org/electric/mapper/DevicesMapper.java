package org.electric.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.electric.model.Devices;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 * @description //TODO
 **/
@Mapper
public interface DevicesMapper extends BaseMapper<Devices> {

    Page<Map<String, String>> listByPage(IPage<Map<String, String>> page, @Param("ew") Wrapper<Map<String, String>> queryWrapper);

    List<Map<String, String>> listAll();

    List<Map<String,String>> export();

    List<Map<String, String>> listByParam(@Param("ew") Wrapper<Map<String, String>> queryWrapper);

    List<Map<String, String>> qualifiedExport();

    Integer getCount(@Param("ew") Wrapper<Map<String, String>> queryWrapper);


    String getPicNameById(String id);
}
