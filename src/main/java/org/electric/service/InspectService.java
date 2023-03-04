package org.electric.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.electric.model.Inspect;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 **/
public interface InspectService extends IService<Inspect> {

    IPage<Map<String, String>> listByPage(Integer currentPage, Integer pageSize, Map<String, String> params);

    List<Map<String, String>> listAll();

}
