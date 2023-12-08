package com.example.service;

import com.example.entity.HomeEntity;
import com.example.entity.RoomEntity;

public interface RoomService {
    RoomEntity create(RoomEntity home);
    RoomEntity updateRoom(int roomId, RoomEntity updatedRoom);
    void deleteRoom(int id);

    HomeEntity getHomeById(int id);
}
