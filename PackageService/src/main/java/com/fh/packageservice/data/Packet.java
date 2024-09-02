package com.fh.packageservice.data;

// Use packet instead of package to avoid reserved keyword of Java
public class Packet {
    private Long id;
    private String name;
    private double weight;
    private String status;

    public Packet(String name, int weight) {
        this.name = name;
        this.weight = weight;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }
}
