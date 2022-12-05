package com.bits.hms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: John Long
 * @create: 11-Nov-2019
 **/

@Entity
@Table(name = "roomType")
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomTypeId")
    private Long roomTypeId;

//    @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL)
//    private Set<Room> rooms;

    @Column(name = "roomStyle")
    @Enumerated(EnumType.STRING)
    private RoomStyle roomStyle;

    @Column(name = "roomRate")
    private int roomRate;

    @Column(name = "numBeds")
    private int numBeds;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "description")
    private String description;

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }



    public int getRoomRate() {
        return roomRate;
    }

    public RoomStyle getRoomStyle() {
        return roomStyle;
    }

    public void setRoomStyle(RoomStyle roomStyle) {
        this.roomStyle = roomStyle;
    }

    public void setRoomRate(int roomRate) {
        this.roomRate = roomRate;
    }

    public int getNumBeds() {
        return numBeds;
    }

    public void setNumBeds(int numBeds) {
        this.numBeds = numBeds;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}