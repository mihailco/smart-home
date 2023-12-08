package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NamedEntityGraph(
        name = "home-entity-graph-with-rooms",
        attributeNodes = {@NamedAttributeNode("rooms")}
)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "homes")
public class HomeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    int ownerId;

    @Column(nullable = false)
    private String name;

    private String address;

    @OneToMany(mappedBy = "home", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<RoomEntity> rooms = new ArrayList<>();
}
