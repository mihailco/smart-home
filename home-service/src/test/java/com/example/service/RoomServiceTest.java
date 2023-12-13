package com.example.service;

import com.example.entity.HomeEntity;
import com.example.entity.RoomEntity;
import com.example.repository.RoomRepository;
import com.example.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {
        RoomEntity roomEntity = new RoomEntity();
        when(roomRepository.save(any(RoomEntity.class))).thenReturn(roomEntity);

        RoomEntity result = roomService.create(roomEntity);

        assertEquals(roomEntity, result);
        verify(roomRepository, times(1)).save(roomEntity);
    }

    @Test
    void updateRoom() {
        int roomId = 1;
        RoomEntity updatedRoomEntity = new RoomEntity();
        updatedRoomEntity.setId(roomId);
        RoomEntity existingRoomEntity = new RoomEntity();
        existingRoomEntity.setId(roomId);
        existingRoomEntity.setName("Old Room");

        when(roomRepository.existsById(roomId)).thenReturn(true);
        when(roomRepository.findById(roomId)).thenReturn(java.util.Optional.of(existingRoomEntity));
        when(roomRepository.save(any(RoomEntity.class))).thenReturn(updatedRoomEntity);

        RoomEntity result = roomService.updateRoom(roomId, updatedRoomEntity);

        assertEquals(updatedRoomEntity.getId(), result.getId());
        assertEquals(updatedRoomEntity.getName(), result.getName());
        verify(roomRepository, times(1)).existsById(roomId);
        verify(roomRepository, times(1)).findById(roomId);
        verify(roomRepository, times(1)).save(existingRoomEntity);
    }

    @Test
    void updateRoom_NotFound() {
        int roomId = 1;
        RoomEntity updatedRoomEntity = new RoomEntity();
        when(roomRepository.existsById(roomId)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> roomService.updateRoom(roomId, updatedRoomEntity));

        verify(roomRepository, times(1)).existsById(roomId);
        verify(roomRepository, never()).findById(roomId);
        verify(roomRepository, never()).save(any(RoomEntity.class));
    }

    @Test
    void deleteRoom() {
        int roomId = 1;

        roomService.deleteRoom(roomId);

        verify(roomRepository, times(1)).deleteById(roomId);
    }

    @Test
    void getHomeById() {
        int roomId = 1;
        HomeEntity homeEntity = new HomeEntity();

        when(roomRepository.getHomeById(roomId)).thenReturn(homeEntity);

        HomeEntity result = roomService.getHomeById(roomId);

        assertEquals(homeEntity, result);
        verify(roomRepository, times(1)).getHomeById(roomId);
    }
}
