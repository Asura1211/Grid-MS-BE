package org.electric.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.electric.model.ProblemToSolve;

import java.util.List;
import java.util.Map;
@Mapper
public interface ProblemToSolveMapper extends BaseMapper<ProblemToSolve> {
    Page<Map<String, String>> listByPage(IPage<Map<String, String>> page, @Param("ew") Wrapper<Map<String, String>> queryWrapper);

    List<Map<String, String>> listAll();

    List<Map<String,String>> export();

    Integer getCount(@Param("ew")Wrapper<Map<String, String>> queryWrapper);

    List<ProblemToSolve> getByUserId(String userId);

}
