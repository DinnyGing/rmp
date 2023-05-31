package com.my.mrp.repo;

import com.my.mrp.models.AverageTemperature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AverageTemperatureRepository extends JpaRepository<AverageTemperature, Long> {
}