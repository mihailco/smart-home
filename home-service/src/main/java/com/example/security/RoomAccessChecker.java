package com.example.security;

import com.example.security.filter.UserContextHolder;
import com.example.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomAccessChecker implements OwnershipChecker {
    private final RoomService roomService;

    @Override
    public boolean check(int id) {
        var home = roomService.getHomeById(id);
        if (home == null)
            return false;
        var ownerId = UserContextHolder.getId();
        return home.getOwnerId() == ownerId;
    }
}
