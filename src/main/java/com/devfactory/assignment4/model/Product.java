package com.devfactory.assignment4.model;

import javax.persistence.*;

/**
 * Created by vaibhavtulsyan on 07/07/16.
 */


@Entity
@Table(name="product")
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private String name;
    private String code;
    private Integer remaining;
    private String description;
    private Integer deleted;

    public Product() {}

    public Product(int id, String name, String code, Integer remaining, String description, Integer deleted) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.remaining = remaining;
        this.description = description;
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
