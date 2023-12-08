package com.example.controller;

import com.example.dto.request.RoomRequest;
import com.example.dto.response.RoomResponse;
import com.example.mapper.RoomMapper;
import com.example.security.HomeAccessChecker;
import com.example.security.RoomAccessChecker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {
    final RoomMapper roomMapper;
    private final RoomAccessChecker roomAccessChecker;
    private final HomeAccessChecker homeAccessChecker;


    @PostMapping
    @PreAuthorize("@homeAccessChecker.check(#homeId)")
    public RoomResponse createRoom(@RequestParam long homeId, @Valid @RequestBody RoomRequest roomRequest) {
        return roomMapper.create(roomRequest, homeId);
    }

    @PutMapping("/{roomId}")
    @PreAuthorize("@roomAccessChecker.check(#roomId)")
    public RoomResponse updateRoom(@PathVariable int roomId, @Valid @RequestBody RoomRequest roomRequest) {
        return roomMapper.updateRoom(roomId, roomRequest);
    }

    @DeleteMapping("/{roomId}")
    @PreAuthorize("@roomAccessChecker.check(#roomId)")
    public ResponseEntity<?> deleteRoom(@NotNull @PathVariable int roomId) {
        roomMapper.deleteRoom(roomId);
        return ResponseEntity.ok("");
    }
}
