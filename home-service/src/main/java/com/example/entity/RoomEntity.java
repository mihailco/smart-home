package com.example.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "rooms")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    String name;

    @Column(name = "home_id", insertable = false, updatable = false)
    long homeId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_id", referencedColumnName = "id")
    private HomeEntity home;
}
