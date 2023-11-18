package com.magicqoven.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "comment")
@EqualsAndHashCode(callSuper = true)
public class Comment extends QueryEntity {

    @Column(nullable = false)
    private String text;
    @Column(name = "created_date", nullable = false)
    private LocalDateTime creationDateTime;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Comment() {
        super();
    }

}