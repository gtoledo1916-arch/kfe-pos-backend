package org.kfe.api.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    //GETTERS
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    //SETTERS
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
