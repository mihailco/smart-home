package com.example.repository;

import com.example.entity.HomeEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HomeRepository extends JpaRepository<HomeEntity, Integer> {

    @Override
    @EntityGraph(value = "home-entity-graph-with-rooms", type = EntityGraph.EntityGraphType.LOAD)
    Optional<HomeEntity> findById(@NonNull Integer aLong);

    @Override
    @EntityGraph(value = "home-entity-graph-with-rooms", type = EntityGraph.EntityGraphType.LOAD)
    <S extends HomeEntity> S save(@NonNull S entity);

    List<HomeEntity> findAllByOwnerId(int ownerId);
}
