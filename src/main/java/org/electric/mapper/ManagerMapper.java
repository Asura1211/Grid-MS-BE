package org.electric.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.electric.model.Manager;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 * @description //TODO
 **/
@Mapper
public interface ManagerMapper extends BaseMapper<Manager> {

    Page<Manager> listByPage(IPage<Manager> page, @Param("ew") Wrapper<Manager> queryWrapper);

    List<Map<String,String>> listAll();

    Manager login(Manager manager);

    List<Map<String, String>> statistics();

    List<Manager> getNormalManager();

    String getIdByName(String realName);

    List<Manager> getAllManagers();
}
