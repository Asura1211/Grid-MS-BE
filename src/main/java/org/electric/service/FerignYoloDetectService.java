package org.electric.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Repository
@FeignClient(name = "yolodetect",url = "localhost:8000/app/")
public interface FerignYoloDetectService {
    @RequestMapping(value = "/detect", method = RequestMethod.GET)
    public String detectImage();

    @RequestMapping(value = "/find_exception",method = RequestMethod.GET)
    public String findException();

    @RequestMapping(value = "/wire_detect", method = RequestMethod.GET)
    public String wireDetect();
}
