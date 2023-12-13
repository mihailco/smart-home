package com.example.controller;

import com.example.dto.request.HomeRequest;
import com.example.dto.response.HomeListResponse;
import com.example.dto.response.HomeResponse;
import com.example.kafka.Topics;
import com.example.kafka.events.HomeDeleteEvent;
import com.example.mapper.HomeMapper;
import com.example.security.HomeAccessChecker;
import com.example.security.filter.UserContextHolder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/homes")
@RequiredArgsConstructor
public class HomeController {
    private final HomeAccessChecker homeAccessChecker;
    private final HomeMapper homeMapper;
    private final KafkaTemplate<Long, HomeDeleteEvent> homeDeleteKafkaTemplate;



    @GetMapping
    public List<HomeListResponse> getHomes() {
        return homeMapper.getAll(UserContextHolder.getId());
    }

    @PostMapping
    public HomeResponse createHome(@Valid @RequestBody HomeRequest homeRequest) {
        return homeMapper.create(homeRequest, UserContextHolder.getId());
    }

    @GetMapping("/{id}")
    @PreAuthorize("@homeAccessChecker.check(#id)")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        var home = homeMapper.getHomeById(id);
        if (home == null) {
            return new ResponseEntity<>("home not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(home);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@homeAccessChecker.check(#id)")
    public HomeResponse updateHome(@NotBlank @PathVariable("id") int id, @Valid @RequestBody HomeRequest homeRequest) {
        return homeMapper.updateHome(id, homeRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@homeAccessChecker.check(#id)")
    public void deleteHome(@PathVariable("id") Integer id) {
        homeMapper.deleteHome(id);

        var event = new HomeDeleteEvent(id);
        homeDeleteKafkaTemplate.send(Topics.HOME_DELETE,
                id.longValue(), event);
        log.info("Deleted home {}", id);
    }
}
