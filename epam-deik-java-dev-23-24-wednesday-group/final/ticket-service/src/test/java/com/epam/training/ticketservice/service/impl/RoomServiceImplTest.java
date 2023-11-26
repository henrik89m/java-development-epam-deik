package com.epam.training.ticketservice.service.impl;
import com.epam.training.ticketservice.dto.RoomDto;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {

    @InjectMocks
    private RoomServiceImpl roomService;

    @Mock
    private RoomRepository roomRepository;

    private final String ROOM_NAME = "terem";
    private final int ROOM_ROW = 12;
    private final int ROOM_COL = 24;

    private final RoomDto TEREM = new RoomDto(ROOM_NAME, ROOM_ROW, ROOM_COL);

    @Test
    void testCreateRoomShouldReturnRoomDtoWhenRoomDoesNotExist() {
        when(roomRepository.existsByName(ROOM_NAME)).thenReturn(false);

        Room savedRoom = new Room(ROOM_NAME, ROOM_ROW, ROOM_COL);

        when(roomRepository.save(any())).thenReturn(savedRoom);

        Optional<RoomDto> result = roomService.createRoom(ROOM_NAME, ROOM_ROW, ROOM_COL);

        assertTrue(result.isPresent());
        RoomDto createdRoomDto = result.get();
        assertEquals(ROOM_NAME, createdRoomDto.getName());
        assertEquals(ROOM_ROW, createdRoomDto.getRows());
        assertEquals(ROOM_COL, createdRoomDto.getCols());

        Mockito.verify(roomRepository, times(1)).save(any());
    }

    @Test
    void testCreateRoomShouldThrowExceptionWhenRoomExists() {
        when(roomRepository.existsByName(ROOM_NAME)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> roomService.createRoom(ROOM_NAME, ROOM_ROW, ROOM_COL));

        Mockito.verify(roomRepository, never()).save(any());
    }

    @Test
    void testUpdateRoom() {
        Room existingRoom = new Room(ROOM_NAME, ROOM_ROW, ROOM_COL);
        when(roomRepository.findByName(ROOM_NAME)).thenReturn(Optional.of(existingRoom));

        Room updatedRoom = new Room(ROOM_NAME, 10, 20);
        when(roomRepository.save(existingRoom)).thenReturn(updatedRoom);

        Optional<RoomDto> result = roomService.updateRoom(ROOM_NAME, 10, 20);

        assertTrue(result.isPresent());
        RoomDto updatedRoomDto = result.get();
        assertEquals(ROOM_NAME, updatedRoomDto.getName());
        assertEquals(10, updatedRoomDto.getRows());
        assertEquals(20, updatedRoomDto.getCols());

        Mockito.verify(roomRepository, times(1)).save(existingRoom);
    }

    @Test
    void testUpdateRoomShouldThrowExceptionWhenRoomNotFound() {
        when(roomRepository.findByName(ROOM_NAME)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> roomService.updateRoom(ROOM_NAME, 10, 20));

        Mockito.verify(roomRepository, never()).save(any());
    }

    @Test
    void testDeleteRoom() {
        Room existingRoom = new Room(ROOM_NAME, ROOM_ROW, ROOM_COL);
        when(roomRepository.findByName(ROOM_NAME)).thenReturn(Optional.of(existingRoom));

        Optional<RoomDto> result = roomService.deleteRoom(ROOM_NAME);

        assertTrue(result.isPresent());
        RoomDto deletedRoomDto = result.get();
        assertEquals(ROOM_NAME, deletedRoomDto.getName());
        assertEquals(ROOM_ROW, deletedRoomDto.getRows());
        assertEquals(ROOM_COL, deletedRoomDto.getCols());

        Mockito.verify(roomRepository, times(1)).delete(existingRoom);
    }

    @Test
    void testDeleteRoomShouldThrowExceptionWhenRoomNotFound() {
        when(roomRepository.findByName(ROOM_NAME)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> roomService.deleteRoom(ROOM_NAME));

        Mockito.verify(roomRepository, never()).delete(any());
    }

    @Test
    void testListRooms() {
        List<Room> rooms = List.of(new Room("Room1", 10, 15),
                new Room("Room2", 8, 12));
        when(roomRepository.findAll()).thenReturn(rooms);

        List<RoomDto> result = roomService.listRooms();

        assertEquals(rooms.size(), result.size());

        for (int i = 0; i < rooms.size(); i++) {
            RoomDto roomDto = result.get(i);
            Room room = rooms.get(i);
            assertEquals(room.getName(), roomDto.getName());
            assertEquals(room.getRows(), roomDto.getRows());
            assertEquals(room.getCols(), roomDto.getCols());
        }
    }
}
