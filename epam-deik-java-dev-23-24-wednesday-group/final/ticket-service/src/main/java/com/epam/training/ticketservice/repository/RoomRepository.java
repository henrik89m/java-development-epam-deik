package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByName(String roomName);

    boolean existsByName(String roomName);

    @Transactional
    void deleteByName(String name);

}
