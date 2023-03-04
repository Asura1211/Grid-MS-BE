package org.electric.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.electric.model.DevicesChange;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 **/
public interface DevicesChangeService extends IService<DevicesChange> {

    IPage<Map<String, String>> listByPage(Integer currentPage, Integer pageSize, Map<String, String> params);

    List<Map<String, String>> listAll();

}
