package com.example.service.impl;

import com.example.entity.HomeEntity;
import com.example.repository.HomeRepository;
import com.example.service.HomeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class HomeServiceImpl implements HomeService {
    final HomeRepository homeRepository;

    public HomeServiceImpl(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    @Override
    public HomeEntity create(HomeEntity home) {
        return homeRepository.save(home);
    }

    @Override
    public List<HomeEntity> getAll() {
        return homeRepository.findAll();
    }

    @Override
    public List<HomeEntity> getAllByOwnerId(int ownerId) {
        return homeRepository.findAllByOwnerId(ownerId);
    }

    @Override
    public HomeEntity getHomeById(int id) {
        return homeRepository.findById(id).orElse(null);
    }

    @Override
    public HomeEntity updateHome(int id, HomeEntity updatedHome) {
        if (!homeRepository.existsById(id)) {
            throw new NoSuchElementException();
        }
        updatedHome.setId(id);
        return homeRepository.save(updatedHome);
    }

    @Override
    public void deleteHome(int id) {
        homeRepository.deleteById(id);
    }

    @Override
    public void deleteByOwnerId(int ownerId) {
        homeRepository.deleteAllByOwnerId(ownerId);
    }
}
