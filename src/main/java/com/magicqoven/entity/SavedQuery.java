package com.magicqoven.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@Table(name = "saved_query")
@EqualsAndHashCode(callSuper = true)
public  class SavedQuery extends QueryEntity {

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "savedQuery")
    private List<BaseData> baseDataList;
}