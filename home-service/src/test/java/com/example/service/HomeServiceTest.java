package com.example.service;

import com.example.entity.HomeEntity;
import com.example.repository.HomeRepository;
import com.example.service.impl.HomeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HomeServiceTest {

    @Mock
    private HomeRepository homeRepository;

    @InjectMocks
    private HomeServiceImpl homeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {
        HomeEntity homeEntity = new HomeEntity();
        when(homeRepository.save(any(HomeEntity.class))).thenReturn(homeEntity);

        HomeEntity result = homeService.create(homeEntity);

        assertNotNull(result);
        verify(homeRepository, times(1)).save(homeEntity);
    }

    @Test
    void getAll() {
        List<HomeEntity> entities = Arrays.asList(new HomeEntity(), new HomeEntity());
        when(homeRepository.findAll()).thenReturn(entities);

        List<HomeEntity> result = homeService.getAll();

        assertEquals(entities.size(), result.size());
        verify(homeRepository, times(1)).findAll();
    }

    @Test
    void getAllByOwnerId() {
        int ownerId = 1;
        List<HomeEntity> entities = Arrays.asList(new HomeEntity(), new HomeEntity());
        when(homeRepository.findAllByOwnerId(ownerId)).thenReturn(entities);

        List<HomeEntity> result = homeService.getAllByOwnerId(ownerId);

        assertEquals(entities.size(), result.size());
        verify(homeRepository, times(1)).findAllByOwnerId(ownerId);
    }

    @Test
    void getHomeById() {
        int id = 1;
        HomeEntity entity = new HomeEntity();
        entity.setId(id);
        when(homeRepository.findById(id)).thenReturn(Optional.of(entity));

        HomeEntity result = homeService.getHomeById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(homeRepository, times(1)).findById(id);
    }

    @Test
    void updateHome() {
        int id = 1;
        HomeEntity updatedHomeEntity = new HomeEntity();
        updatedHomeEntity.setId(id);
        when(homeRepository.existsById(id)).thenReturn(true);
        when(homeRepository.save(any(HomeEntity.class))).thenReturn(updatedHomeEntity);

        HomeEntity result = homeService.updateHome(id, updatedHomeEntity);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(homeRepository, times(1)).existsById(id);
        verify(homeRepository, times(1)).save(updatedHomeEntity);
    }

    @Test
    void updateHome_NotFound() {
        int id = 1;
        HomeEntity updatedHomeEntity = new HomeEntity();
        when(homeRepository.existsById(id)).thenReturn(false);

        NoSuchElementException exception = org.junit.jupiter.api.Assertions.assertThrows(
                NoSuchElementException.class,
                () -> homeService.updateHome(id, updatedHomeEntity)
        );

        assertNull(exception.getMessage());
        verify(homeRepository, times(1)).existsById(id);
    }

    @Test
    void deleteHome() {
        int id = 1;
        homeService.deleteHome(id);
        verify(homeRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteByOwnerId() {
        int ownerId = 1;
        homeService.deleteByOwnerId(ownerId);
        verify(homeRepository, times(1)).deleteAllByOwnerId(ownerId);
    }
}
