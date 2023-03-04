package org.electric.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.electric.mapper.ExceptionRecordMapper;
import org.electric.mapper.ManagerMapper;
import org.electric.model.ExceptionRecord;
import org.electric.model.Manager;
import org.electric.service.ExceptionRecordService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
@Service
public class ExceptionRecordServiceImpl extends ServiceImpl<ExceptionRecordMapper, ExceptionRecord> implements ExceptionRecordService {
    @Resource
    ExceptionRecordMapper mapper;
    @Override
    public List<ExceptionRecord> getOneMonthData() {
        return mapper.getOneMonthData();
    }

    @Override
    public List<ExceptionRecord> getOneWeekData() {
        return mapper.getOneWeekData();
    }
}
