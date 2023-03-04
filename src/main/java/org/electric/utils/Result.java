package org.electric.utils;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * @author Administrator
 * @version 1.0
 * @description //TODO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class Result<T> {

    private Integer code;
    private String message;
    private T data;

    public Result(Integer code,String message){
        this(code,message,null);
        log.info("Result:[code:"+code+"]  [message:"+message+"]");
    }

    public Result(Integer code,T data){
        this(code,null,data);
        log.info("Result:[code:"+code+"]  [data:"+ JSON.toJSONString(data)+"]");
    }


}
