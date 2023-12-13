package com.example.controller;

import com.example.dto.request.RoomRequest;
import com.example.dto.response.RoomResponse;
import com.example.kafka.Topics;
import com.example.kafka.events.RoomDeleteEvent;
import com.example.mapper.RoomMapper;
import com.example.security.HomeAccessChecker;
import com.example.security.RoomAccessChecker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {
    final RoomMapper roomMapper;
    private final RoomAccessChecker roomAccessChecker;
    private final HomeAccessChecker homeAccessChecker;
    private final KafkaTemplate<Long, RoomDeleteEvent> homeDeleteKafkaTemplate;



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
    public void deleteRoom(@NotNull @PathVariable Integer roomId) {
        roomMapper.deleteRoom(roomId);

        var event = new RoomDeleteEvent(roomId);
        homeDeleteKafkaTemplate.send(Topics.HOME_DELETE,
                roomId.longValue(), event);
        log.info("Deleted room {}", roomId);
    }
}
