package com.my.mrp.models;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Entity
@Table(name = "averageTemperatures")
public class AverageTemperature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_temperature", nullable = false)
    long id;
    @Column(name = "temperature_temperature", nullable = false)
    private double temperature;
    @Column(name = "date_temperature", nullable = false)
    private LocalDateTime date;

    public AverageTemperature(double temperature) {
        this.temperature = temperature;
        this.date = LocalDateTime.now();
    }

    public AverageTemperature() {

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

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}