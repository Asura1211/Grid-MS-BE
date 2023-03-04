package org.electric.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Picture {
    private String id;
    private String name;
    private String binData;
    @TableField(exist = false)
    private MultipartFile file;
}
