package com.my.mrp.models;

import java.util.Random;

public class Room {
    private long id;
    private String name;
    private boolean turn;
    private double temperature;
    private Random random;

    public Room(String name, boolean turn) {
        this.name = name;
        this.turn = turn;
        random = new Random();
        generate();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }


    public double getTemperature(){
        return temperature;
    }
    public void generate(){
        temperature = Math.round((random.nextDouble(6) + 20)*10)/10;
    }
}
