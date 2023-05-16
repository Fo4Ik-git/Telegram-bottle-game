package com.fo4ik.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/*@Entity
@Table(name = "groups")*/
public class Group {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  // ManyToOne Users in Group

    @ManyToMany
    @JoinTable(
            name = "group_user",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> userList;

    public Group(Long id, List<User> userList) {
        this.id = id;
        this.userList = userList;
    }

    public Group() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
