package org.electric.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "detected_device_picture")

public class DetectedDevicePicture {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    private String name;
    private String devicePictureId;
    private String detectedPictureUrl;
    @TableField(exist = false)
    private String detectedBinData;
//    @TableField(exist = false)
//    private MultipartFile file;
}
