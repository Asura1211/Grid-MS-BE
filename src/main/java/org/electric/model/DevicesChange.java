package org.electric.model;

import lombok.Data;

/**
 * @author 林来军
 * @version 1.0
 * @description //TODO
 **/
@Data
public class DevicesChange {

    private String id;
    private String changeDevice;
    private String oldManager;
    private String oldPrice;
    private String oldAddress;
    private String oldState;
    private String newManager;
    private String newPrice;
    private String newAddress;
    private String newState;
    private String changeTime;


}
