package com.my.mrp.models;

import jakarta.persistence.*;

import java.util.Random;

@Entity
@Table(name = "heatings")
public class Heating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_heating", nullable = false)
    long id;
    @Column(name = "temperature_heating", nullable = false)
    private double temperature;
    @Column(name = "is_heating", nullable = false)
    private boolean isHeating;

    @OneToOne(mappedBy = "heating")
    private Room room;

    public Heating() {
    }

    public Heating(long id, boolean isHeating) {
        this.id = id;
        this.isHeating = isHeating;
        setTemperature();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getTemperature() {
        return temperature;
    }

    public boolean isHeating() {
        return isHeating;
    }

    public void setHeating(boolean heating) {
        isHeating = heating;
    }
    public void setTemperature(){
        temperature = Math.round((new Random().nextDouble(20) + 12)*10)/10;
    }
}
