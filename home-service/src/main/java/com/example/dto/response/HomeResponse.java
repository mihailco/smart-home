package com.example.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HomeResponse {
   private int id;
   private String name;
   private String address;

   private List<RoomResponse> rooms = new ArrayList<>();
}
