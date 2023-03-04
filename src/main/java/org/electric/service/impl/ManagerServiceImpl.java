package org.electric.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.electric.mapper.ManagerMapper;
import org.electric.model.Manager;
import org.electric.service.ManagerService;
import org.electric.utils.IPUtil;
import org.electric.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements ManagerService {

    @Resource
    private ManagerMapper mapper;

    @Override
    public IPage<Manager> listByPage(Integer currentPage, Integer pageSize, Map<String, String> params) {
        IPage<Manager> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Manager> wrapper = new QueryWrapper<>();
        for(Map.Entry<String,String> entry : params.entrySet()){
            wrapper.like(entry.getKey(),null==entry.getValue()?"":entry.getValue());
        }
        return mapper.listByPage(page, wrapper);
    }

    @Override
    public List<Map<String,String>> listAll() {
        return mapper.listAll();
    }

    @Override
    public Result<Manager> login(Manager manager, HttpServletRequest request) {
        Manager u = mapper.login(manager);
        if(null!=u) {
            if(manager.getPassword().equals(u.getPassword())){
                HttpSession session = request.getSession();
                String ip = IPUtil.getIp(request);
                ArrayList<String> ip2LocRes = IPUtil.ip2Loc(ip);
                session.setAttribute("backUsername",u.getUsername());
                session.setAttribute("backHead",u.getHead());
                session.setAttribute("backUserid",u.getId());
                Map<String,String> params=new HashMap<>();

                params.put("province",ip2LocRes.get(0));
                params.put("city",ip2LocRes.get(1));
                params.put("date",DateTime.now().toDateStr());
                return new Result<>(200,"登录成功！",u);
            }
        }
        return new Result<>(400,"登录失败！");
    }

    @Override
    public List<Map<String, String>> statistics() {
        return mapper.statistics();
    }

    @Override
    public List<Manager> getNormalManager() {
        return mapper.getNormalManager();
    }

    @Override
    public String getIdByName(String realName) {
        return mapper.getIdByName(realName);
    }

    @Override
    public List<Manager> getAllManagers() {
        return mapper.getAllManagers();
    }
}
