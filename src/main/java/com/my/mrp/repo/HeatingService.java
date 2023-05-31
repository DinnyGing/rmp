package com.my.mrp.repo;

import com.my.mrp.models.Heating;
import com.my.mrp.models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HeatingService {
    private HeatingRepository heatingRepository;

    @Autowired
    public HeatingService(HeatingRepository heatingRepository) {
        this.heatingRepository = heatingRepository;
    }
    @Transactional
    public Heating save(Heating heating){
        return heatingRepository.save(heating);
    }
    public Heating findById(long id){
        if(heatingRepository.findById(id).isPresent())
            return heatingRepository.findById(id).get();
        return null;
    }
}
