package com.gs.register;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ServiceMeta {

    private String serviceId;

    private String serviceName;

    private String host;

    private Integer port;


}
