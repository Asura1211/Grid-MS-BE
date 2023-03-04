package org.electric.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.electric.mapper.DevicesChangeMapper;
import org.electric.model.DevicesChange;
import org.electric.service.DevicesChangeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
public class DevicesChangeServiceImpl extends ServiceImpl<DevicesChangeMapper, DevicesChange> implements DevicesChangeService {

    @Resource
    private DevicesChangeMapper mapper;

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

}
