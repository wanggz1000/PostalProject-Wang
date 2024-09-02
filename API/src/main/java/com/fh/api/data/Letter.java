package com.fh.api.data;

public class Letter {
    private Long id;
    private String name;
    private String country;
    private String status;

    public Letter(String name, String country) {
        this.name = name;
        this.country = country;
        this.status = "waiting";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStatus() {
        return this.status;
    }
}
