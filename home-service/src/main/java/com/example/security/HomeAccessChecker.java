package com.example.security;

import com.example.entity.HomeEntity;
import com.example.security.filter.UserContextHolder;
import com.example.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HomeAccessChecker implements OwnershipChecker{
    private final HomeService homeService;

    public boolean check(int homeId) {
        int ownerId = UserContextHolder.getId();
        HomeEntity home = homeService.getHomeById(homeId);
        if (home == null)
            return false;
        return home.getOwnerId() == ownerId;
    }
}
