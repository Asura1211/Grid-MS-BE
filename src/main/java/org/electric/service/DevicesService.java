package org.electric.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.electric.model.Devices;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 **/
public interface DevicesService extends IService<Devices> {

    IPage<Map<String, String>> listByPage(Integer currentPage, Integer pageSize, Map<String, String> params);

    List<Map<String, String>> listAll();

    List<Map<String,String>> export();

    List<Map<String, String>> listByParam(Map<String, String> params);

    List<Map<String, String>> qualifiedExport();

    Integer getCount(Map<String, String> params);


    String getPicNameById(String id);
}
