package org.electric.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Administrator
 * @version 1.0
 **/
@Data @AllArgsConstructor
@NoArgsConstructor
@ToString
public class Devices {

    private String id;
    private String identifier;
    private String picture;
    private String name;
    private String brand;
    private String makeDate;
    private String useDate;
    private String hisLocation;
    private String personInCharge;
    private String price;
    private String qrcode;
    private String state;
    private String types;

    @TableField(exist = false)
    private MultipartFile file;

}
