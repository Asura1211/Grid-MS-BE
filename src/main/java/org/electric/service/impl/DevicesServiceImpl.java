package org.electric.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.electric.mapper.DevicesMapper;
import org.electric.model.Devices;
import org.electric.service.DevicesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
public class DevicesServiceImpl extends ServiceImpl<DevicesMapper, Devices> implements DevicesService {

    @Resource
    private DevicesMapper mapper;

    @Override
    public IPage<Map<String, String>> listByPage(Integer currentPage, Integer pageSize, Map<String, String> params) {
        IPage<Map<String, String>> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Map<String, String>> wrapper = new QueryWrapper<>();
        for(Map.Entry<String,String> entry : params.entrySet()){
            wrapper.eq(entry.getKey(),null==entry.getValue()?"":entry.getValue());
        }
        return mapper.listByPage(page, wrapper);
    }

    @Override
    public List<Map<String, String>> listAll() {
        return mapper.listAll();
    }

    @Override
    public List<Map<String, String>> export() {
        return mapper.export();
    }

    @Override
    public List<Map<String, String>> qualifiedExport() {
        return mapper.qualifiedExport();
    }

    @Override
    public Integer getCount(Map<String, String> params) {
        QueryWrapper<Map<String, String>> wrapper = new QueryWrapper<>();
        for(Map.Entry<String,String> entry : params.entrySet()){
            wrapper.eq(entry.getKey(),null==entry.getValue()?"":entry.getValue());
        }
        return mapper.getCount(wrapper);
    }

    @Override
    public List<Map<String, String>> listByParam(Map<String, String> params) {
        QueryWrapper<Map<String, String>> wrapper = new QueryWrapper<>();
        for(Map.Entry<String,String> entry : params.entrySet()){
            wrapper.eq(entry.getKey(),null==entry.getValue()?"":entry.getValue());
        }
        return mapper.listByParam(wrapper);
    }

    @Override
    public String getPicNameById(String id) {
        return mapper.getPicNameById(id);
    }
}
