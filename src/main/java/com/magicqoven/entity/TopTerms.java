package com.magicqoven.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "top_terms")
//@EqualsAndHashCode(callSuper = true)
public class TopTerms extends BaseData {


    @Column(name = "dma_id", nullable = true)
    private Long dmaId;

    private String term;
    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate week;
    private Integer score;
    @Column(name = "term_rank")
    private Integer termRank;
    @Column(name = "refresh_date")
    @Temporal(TemporalType.DATE)
    private LocalDate refreshDate;
    @Column(name = "dma_name")
    private String dmaName;

    public TopTerms() {
        this.setTableName("TopTerms");
        this.setSchemaName("your_schema"); // Establece el esquema específico
    }
}
