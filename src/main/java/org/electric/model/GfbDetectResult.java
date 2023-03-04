package org.electric.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GfbDetectResult {
    //private byte[] url;
    private String url;
    private String name;
    private Integer num;

}
