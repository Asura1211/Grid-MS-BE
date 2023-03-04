package org.electric.utils;

import cn.hutool.extra.servlet.ServletUtil;
import org.electric.consts.Const;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IPUtil {
    public static ArrayList<String> ip2LocRes;

    public static String getIp(HttpServletRequest httpServletRequest){
        String clientIP = ServletUtil.getClientIP(httpServletRequest,null);
        return clientIP;
    }

    /**
     * ip地址转换为位置
     * @return
     */

    public static ArrayList<String> ip2Loc(String ip){
        RestTemplate restTemplate=new RestTemplate();
        Map<String,String> params=new HashMap<>();
        params.put("ip",ip);  //
        ResponseEntity<String> responseEntity=restTemplate.getForEntity(Const.IP_TO_LOCATION_API+"ip={ip}",String.class,params);

        // 字符串解析
        String resultStr = responseEntity.getBody().trim();
        int provinceNum = resultStr.indexOf("lo") +4 ;
        int cityNum = resultStr.indexOf("lc")  +4;
        String province = resultStr.trim().substring(provinceNum,provinceNum+3);
        String city = resultStr.trim().substring(cityNum,cityNum+3);
        ip2LocRes = new ArrayList<String>();
        ip2LocRes.add(province);
        ip2LocRes.add(city);
        return ip2LocRes;
    }
}
