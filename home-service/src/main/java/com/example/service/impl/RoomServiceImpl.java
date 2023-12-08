package com.example.service.impl;

import com.example.entity.HomeEntity;
import com.example.entity.RoomEntity;
import com.example.repository.RoomRepository;
import com.example.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public RoomEntity create(RoomEntity room) {
        return roomRepository.save(room);
    }

    @Override
    public RoomEntity updateRoom(int roomId, RoomEntity updatedRoom) {
        if (!roomRepository.existsById(roomId)) {
            throw new NoSuchElementException();
        }

        var room = roomRepository.findById(roomId).get();
        room.setName(updatedRoom.getName());

        return roomRepository.save(room);
    }

    @Override
    public void deleteRoom(int id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
        }
    }

    @Override
    public HomeEntity getHomeById(int id) {
        return roomRepository.getHomeById(id);
    }
}
