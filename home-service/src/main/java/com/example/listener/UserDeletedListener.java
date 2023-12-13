package com.example.listener;

import com.example.kafka.KafkaGroupIds;
import com.example.kafka.Topics;
import com.example.kafka.events.UserDeleteEvent;
import com.example.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDeletedListener {
    private final HomeService homeService;

    @KafkaListener(topics = Topics.USER_DELETE, groupId = KafkaGroupIds.HOMES)
    public void onDeleteUser(UserDeleteEvent event){
        log.info("Received: {}" , event);
        homeService.deleteByOwnerId(event.getUserId());
    }

}
