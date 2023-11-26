package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.dto.RoomDto;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Optional<RoomDto> createRoom(String roomName, int rows, int cols) {
        if (roomRepository.existsByName(roomName)){
           throw new IllegalArgumentException("Room name already exists");
        }

        Room makeRoom = new Room();
        makeRoom.setName(roomName);
        makeRoom.setRows(rows);
        makeRoom.setCols(cols);

       Room savedRoom = roomRepository.save(makeRoom);
       RoomDto roomDto = new RoomDto(savedRoom.getName(), savedRoom.getRows(), savedRoom.getCols());

       return Optional.of(roomDto);
    }

    @Override
    public Optional<RoomDto> updateRoom(String roomName, int rows, int cols) {
        Room room = roomRepository.findByName(roomName)
                .orElseThrow(() -> new IllegalArgumentException("Room not found to be updated"));

        room.setRows(rows);
        room.setCols(cols);

        Room updatedRoom = roomRepository.save(room);
        RoomDto roomDto = new RoomDto(room.getName(), room.getRows(), room.getCols());

        return Optional.of(roomDto);
    }

    @Override
    public Optional<RoomDto> deleteRoom(String roomName) {
        Optional<Room> optionalRoom = roomRepository.findByName(roomName);

        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            roomRepository.delete(room);

            RoomDto deletedRoomDto = new RoomDto(room.getName(),room.getRows(), room.getCols());
            return Optional.of(deletedRoomDto);
        } else {
            throw new IllegalArgumentException("Room does not exist");
        }
    }


    @Override
    public List<RoomDto> listRooms() {
        return roomRepository.findAll().stream()
                .map(room -> new RoomDto(room.getName(), room.getRows(), room.getCols()))
                .collect(Collectors.toList());
    }


}
