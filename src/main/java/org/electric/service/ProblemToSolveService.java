package org.electric.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.electric.model.ProblemToSolve;

import java.util.List;
import java.util.Map;

public interface ProblemToSolveService extends IService<ProblemToSolve> {
    IPage<Map<String, String>> listByPage(Integer currentPage, Integer pageSize, Map<String, String> params);

    List<Map<String, String>> listAll();

    List<Map<String,String>> export();

    Integer getCount(Map<String, String> params);

    List<ProblemToSolve> getByUserId(String id);

}
