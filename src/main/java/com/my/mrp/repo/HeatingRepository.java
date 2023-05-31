package com.my.mrp.repo;

import com.my.mrp.models.Heating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface HeatingRepository extends JpaRepository<Heating, Long> {
}