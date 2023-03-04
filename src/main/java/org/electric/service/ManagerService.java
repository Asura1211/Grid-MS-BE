package org.electric.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.electric.model.Manager;
import org.electric.utils.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 * @description //TODO
 **/

public interface ManagerService extends IService<Manager> {

    IPage<Manager> listByPage(Integer currentPage, Integer pageSize, Map<String, String> params);

    List<Map<String,String>> listAll();

    Result<Manager> login(Manager managers, HttpServletRequest request);

    List<Map<String, String>> statistics();

    List<Manager> getNormalManager();

    String getIdByName(String realName);

    List<Manager> getAllManagers();
}
