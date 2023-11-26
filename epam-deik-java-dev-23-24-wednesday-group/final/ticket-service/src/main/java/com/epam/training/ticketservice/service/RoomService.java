package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dto.MovieDto;
import com.epam.training.ticketservice.dto.RoomDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface RoomService {

    Optional<RoomDto> createRoom(String roomName, int rows, int cols);

    Optional<RoomDto> updateRoom(String roomName, int rows, int cols);

    Optional<RoomDto> deleteRoom(String roomName);

    List<RoomDto> listRooms();
}
