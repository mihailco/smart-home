package com.example.service;

import com.example.entity.HomeEntity;

import java.util.List;

public interface HomeService {
   HomeEntity create(HomeEntity home);
   List<HomeEntity> getAll();
   List<HomeEntity> getAllByOwnerId(int ownerId);
   HomeEntity getHomeById(int id);
   HomeEntity updateHome(int id, HomeEntity updatedHome);
   void deleteHome(int id);
}
