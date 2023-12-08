package com.example.mapper;

import com.example.dto.request.HomeRequest;
import com.example.dto.response.HomeListResponse;
import com.example.dto.response.HomeResponse;
import com.example.entity.HomeEntity;
import com.example.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HomeMapper {
    private final ModelMapper basicMapper;
    final HomeService homeService;

    public HomeResponse create(HomeRequest home, Integer id) {
        HomeEntity homeEntity = basicMapper.map(home, HomeEntity.class);
        homeEntity.setOwnerId(id);
        return basicMapper.map(homeService.create(homeEntity), HomeResponse.class);
    }

    public List<HomeListResponse> getAll(int ownerId) {
        return homeService.getAllByOwnerId(ownerId).stream().map((e) -> basicMapper.map(e, HomeListResponse.class)).toList();
    }

    public HomeResponse getHomeById(int id) {
        return basicMapper.map(homeService.getHomeById(id), HomeResponse.class);
    }

    public HomeResponse updateHome(int id, HomeRequest updatedHome) {
        HomeEntity homeEntity = basicMapper.map(updatedHome, HomeEntity.class);
        return basicMapper.map(homeService.updateHome(id, homeEntity), HomeResponse.class);
    }

    public void deleteHome(int id) {
        homeService.deleteHome(id);
    }
}
