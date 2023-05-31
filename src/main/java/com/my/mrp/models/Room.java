package com.my.mrp.models;

import jakarta.persistence.*;

import java.util.Random;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @Column(name = "name_room", nullable = false)
    private String name;
    @Column(name = "turn_light_room", nullable = false)
    private boolean turn;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "heating_id", referencedColumnName = "id_heating")
    private Heating heating;
    @Column(name = "light_room")
    double light;

    public Room(String name, boolean turn, double light, Heating heating) {
        this.name = name;
        this.turn = turn;
        this.light = light;
        this.heating = heating;
        setTemperature(22.9, 23.7);
    }

    public Room() {
    }

    public void setTemperature(double n1, double n2){
        heating.setTemperature();
        if(heating.getTemperature() <= n1){
            heating.setHeating(true);
        }
        else if(heating.getTemperature() >= n2){
            heating.setHeating(false);
        }
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

    public Heating getHeating() {
        return heating;
    }

    public void setHeating(Heating heating) {
        this.heating = heating;
    }
    public double getLight() {
        return light;
    }

    public void setLight(double light) {
        this.light = light;
    }
}
