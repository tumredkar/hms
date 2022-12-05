package com.bits.hms.service;

import java.util.List;

import com.bits.hms.entity.Room;

public interface RoomService {

    Room findRoomByRoomId(Long roomId);
    List<Room> findAllRooms();
    List<Room> findAllAvailableRooms();
    List<Room> getRoomsByRoomType(String roomType);
    void updateRoomStatus(Long roomId, Integer roomStatus);
}
