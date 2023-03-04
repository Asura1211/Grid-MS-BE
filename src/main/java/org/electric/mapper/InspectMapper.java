package org.electric.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.electric.model.Inspect;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 * @description //TODO
 **/
@Mapper
public interface InspectMapper extends BaseMapper<Inspect> {

    Page<Map<String, String>> listByPage(IPage<Map<String, String>> page, @Param("ew") Wrapper<Map<String, String>> queryWrapper);

    List<Map<String, String>> listAll();

}
