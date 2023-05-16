package com.fo4ik.entities;


import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    private String name;
    private boolean isAdult;

    public User(long id, String name, boolean isAdult) {
        this.id = id;
        this.name = name;
        this.isAdult = isAdult;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<User> getAllUsers() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();


        List<User> users = session.createQuery("from User", User.class).getResultList();
        return users;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isAdult=" + isAdult +
                '}';
    }
}
