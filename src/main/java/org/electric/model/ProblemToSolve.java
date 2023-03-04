package org.electric.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

/**
 * 待解决的问题
 */
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class ProblemToSolve {
    private String id;
    private String problemOutline;
    private String problemDescription;
    private String otherInformation;
    private String problemClassification;
    private String problemState;
    private String deadline;
    private String presenter;
    private String personInCharge;
    private String presenterId;
    private String personInChargeId;
    @TableField(exist = false)
    private MultipartFile file;
}
