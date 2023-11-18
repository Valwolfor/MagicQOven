package com.magicqoven.controller;

import java.time.LocalDate;

public class TopRisingTermsDTO {

    private LocalDate refreshDate;
    private String term;
    private Integer rank;
    private Integer score;
    private Long dmaId;
    private String dmaName;
    private Double percentGain;

    public TopRisingTermsDTO(LocalDate refreshDate, String term, Integer rank, Integer score, Long dmaId, String dmaName, Double percentGain) {
        this.refreshDate = refreshDate;
        this.term = term;
        this.rank = rank;
        this.score = score;
        this.dmaId = dmaId;
        this.dmaName = dmaName;
        this.percentGain = percentGain;
    }

    public LocalDate getRefreshDate() {
        return refreshDate;
    }

    public void setRefreshDate(LocalDate refreshDate) {
        this.refreshDate = refreshDate;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getDmaId() {
        return dmaId;
    }

    public void setDmaId(Long dmaId) {
        this.dmaId = dmaId;
    }

    public String getDmaName() {
        return dmaName;
    }

    public void setDmaName(String dmaName) {
        this.dmaName = dmaName;
    }

    public Double getPercentGain() {
        return percentGain;
    }

    public void setPercentGain(Double percentGain) {
        this.percentGain = percentGain;
    }
}