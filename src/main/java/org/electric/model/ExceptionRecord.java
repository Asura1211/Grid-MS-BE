package org.electric.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExceptionRecord {
    String id;
    String errorCnt;
    String noReadings;  //无示数
    String wireWinding; //电线缠绕
    String abnormalPosture; //异常姿态
    String timeRecord;      //记录时间
}
