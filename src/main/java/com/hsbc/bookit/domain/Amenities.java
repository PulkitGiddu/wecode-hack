package com.hsbc.bookit.domain;

import java.util.Objects;

public class Amenities {

    private int id;
    private String name;
    private int cost;

    public Amenities(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Amenities amenities)) return false;
        return id == amenities.id && cost == amenities.cost && Objects.equals(name, amenities.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cost);
    }

    @Override
    public String toString() {
        return "Amenity{id=" + id + ", name='" + name + '\'' + ", cost=" + cost + '}';
    }
}

