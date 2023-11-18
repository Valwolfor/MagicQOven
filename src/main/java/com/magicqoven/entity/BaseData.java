package com.magicqoven.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "base_data")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "table_name", nullable = false)
    private String tableName;
    @Column(name = "schema_name", nullable = false)
    private String schemaName;
    @ManyToOne
    @JoinColumn(name = "saved_query_id", nullable = false)
    private SavedQuery savedQuery;

}