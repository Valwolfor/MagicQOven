package com.magicqoven.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "saved_query")
public class SavedQueryBuilt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    private String name;

    private String description;

    @Column(name = "query_parameters")
    private String queryParameters;

    private String query;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "savedQuery", cascade = CascadeType.ALL)
    private Set<CommentQuery> comments = new HashSet<>();

}
