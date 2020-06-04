package oms.pc_protector.restApi.client.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientVO {

    private String userId;
    private String ipAddress;
    private String macAddress;
    private String os;
    private String pcProtectorVersion;
    private String vaccineVersion;
    private String pcName;
    private String checkTime;
    private String roles;
    private boolean active;

    public ClientVO(String id, String ipAddress) {
        this.userId = id;
        this.macAddress = ipAddress;
    }

    public ClientVO() {

    }
}
