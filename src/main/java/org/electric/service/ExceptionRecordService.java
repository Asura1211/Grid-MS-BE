package org.electric.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.electric.model.ExceptionRecord;

import java.util.List;

public interface ExceptionRecordService extends IService<ExceptionRecord> {
    List<ExceptionRecord> getOneMonthData();
    List<ExceptionRecord> getOneWeekData();
}
