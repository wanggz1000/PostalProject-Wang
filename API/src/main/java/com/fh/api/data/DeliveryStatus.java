package com.fh.api.data;

public class DeliveryStatus {
    private Long id;
    private String type;
    private String name;
    private String status;

    public DeliveryStatus(Long id, String type, String name, String status) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
}
