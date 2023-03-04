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
 * @description //TODO
 **/

@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Manager {
    private String id;
    private String realName;
    private String password;
    private String username;
    private String age;
    private String sex;
    private String tel;
    private String createTime;
    private String head;

    @TableField(exist = false)
    private MultipartFile file;

}
