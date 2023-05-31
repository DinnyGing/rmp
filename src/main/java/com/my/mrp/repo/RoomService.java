package com.my.mrp.repo;

import com.my.mrp.models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoomService {
    RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    @Transactional
    public Room save(Room room){
        return roomRepository.save(room);
    }
    public Room findById(String id){
        if(roomRepository.findById(id).isPresent())
            return roomRepository.findById(id).get();
        return null;
    }

}
