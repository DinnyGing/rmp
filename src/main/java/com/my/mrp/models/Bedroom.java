package com.my.mrp.models;

public class Bedroom extends Room{
    double light;
    public Bedroom(String name, boolean turn, double light) {
        super(name, turn);
        this.light = light;
    }

    @Override
    public long getId() {
        return super.getId();
    }

    @Override
    public void setId(long id) {
        super.setId(id);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public boolean isTurn() {
        return super.isTurn();
    }

    @Override
    public void setTurn(boolean turn) {
        super.setTurn(turn);
    }

    @Override
    public double getTemperature() {
        return super.getTemperature();
    }

    public double getLight() {
        return light;
    }

    public void setLight(double light) {
        this.light = light;
    }
}
