package org.electric.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 林来军
 * @version 1.0
 * @description //TODO
 **/
@Data @AllArgsConstructor
@NoArgsConstructor
@ToString
public class Inspect {

    private String id;
    private String deviceid;
    private String managerid;
    private String inspectTime;
    private String description;
    private String inspectAddress;
    private String state;
    private String picture;
    private String x;
    private String y;
}
