package com.wecode.bookit.model;


import java.util.Objects;

public class Rooms {
    private int id;
    private String name;
    private int seatingCapacity;

    public Rooms(int id,String name, int seatingCapacity) {
        this.id = id;
        this.name = name;
        this.seatingCapacity = seatingCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rooms rooms)) return false;
        return id == rooms.id && seatingCapacity == rooms.seatingCapacity && Objects.equals(name, rooms.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, seatingCapacity);
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

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    @Override
    public String toString() {
        return "Room{id=" + id + ", name='" + name + '\'' + ", seatingCapacity=" + seatingCapacity + '}';
    }
}

