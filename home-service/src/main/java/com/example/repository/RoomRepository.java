package com.example.repository;

import com.example.entity.HomeEntity;
import com.example.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomRepository extends JpaRepository<RoomEntity, Integer> {

    @Query("select r.home from RoomEntity r where r.id = ?1")
    HomeEntity getHomeById(int id);

}
