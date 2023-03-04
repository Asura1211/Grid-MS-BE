package org.electric.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.electric.mapper.ProblemToSolveMapper;
import org.electric.model.ProblemToSolve;
import org.electric.service.ProblemToSolveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Service
public class ProblemToSolveServiceImpl extends ServiceImpl<ProblemToSolveMapper, ProblemToSolve>
        implements ProblemToSolveService {
    @Resource
    private ProblemToSolveMapper problemToSolveMapper;
    @Override
    public IPage<Map<String, String>> listByPage(Integer currentPage, Integer pageSize, Map<String, String> params) {
        IPage<Map<String, String>> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Map<String, String>> wrapper = new QueryWrapper<>();
        for(Map.Entry<String,String> entry : params.entrySet()){
            wrapper.like(entry.getKey(),null==entry.getValue()?"":entry.getValue());
        }
        return problemToSolveMapper.listByPage(page, wrapper);
    }

    @Override
    public List<Map<String, String>> listAll() {
        return problemToSolveMapper.listAll();
    }

    @Override
    public List<Map<String, String>> export() {
        return null;
    }

    @Override
    public Integer getCount(Map<String, String> params) {
        QueryWrapper<Map<String, String>> wrapper = new QueryWrapper<>();
        for(Map.Entry<String,String> entry : params.entrySet()){
            wrapper.eq(entry.getKey(),null==entry.getValue()?"":entry.getValue());
        }
        return problemToSolveMapper.getCount(wrapper);
    }

    @Override
    public List<ProblemToSolve> getByUserId(String id) {
        return problemToSolveMapper.getByUserId(id);
    }
}
