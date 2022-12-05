package com.bits.hms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author: Naichuan Zhang
 * @create: 24-Nov-2019
 **/

@Entity
@Table(name = "executiveRoom")
public class ExecutiveRoom extends Room {

    @Column(name = "price", columnDefinition = "Decimal(10,2) default '500.0'")
    private Double price;

    @Column(name = "description", columnDefinition = "varchar(100) default 'This is an Executive Room'")
    private String description;

    public ExecutiveRoom() {
        price = 500.0;
        description = "This is an Executive Room";
        this.setRoomType("EXECUTIVE");
    }

    @Override
    public Double getPrice() {
        return 500.0;
    }

    @Override
    public String getDescription() {
        return description;
    }

}
