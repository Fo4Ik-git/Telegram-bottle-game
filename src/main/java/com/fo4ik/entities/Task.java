package com.fo4ik.entities;

import jakarta.persistence.*;
import org.apache.commons.codec.language.bm.Lang;
import org.apache.commons.codec.language.bm.Languages;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    private String task;
    private boolean forAdult;


    public Task(String task, boolean forAdult) {
        this.task = task;
        this.forAdult = forAdult;
    }

    public Task() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isForAdult() {
        return forAdult;
    }

    public void setForAdult(boolean forAdult) {
        this.forAdult = forAdult;
    }
}
