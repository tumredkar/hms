package com.bits.hms.entity;

/**
 * @author: Naichuan Zhang
 * @create: 24-Nov-2019
 **/
public class RoomDecorator extends Room {

    private Room room;

    public RoomDecorator(Room room) {
        this.room = room;
    }

    @Override
    public Double getPrice() {
        return room.getPrice();
    }

    @Override
    public String getDescription() {
        return room.getDescription();
    }
}

