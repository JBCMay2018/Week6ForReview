package com.nguyen.week6challenge.model;

import javax.persistence.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String type;

//    @OneToOne(mappedBy = "category")
//    private Car car;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public Car getCar() {
//        return car;
//    }
//
//    public void setCar(Car car) {
//        this.car = car;
//    }

//    @Override
//    public String toString() {
//        return "Category{" +
//                "id=" + id +
//                ", type='" + type + '\'' +
//                ", car=" + car +
//                '}';
//    }
}
