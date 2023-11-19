package com.magicqoven.entity.DTO;

import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public class QueryParameters {
    private Map<String, FilterUnit> filters;
    private List<String> selectedFields;
    private String sortField;
    private Sort.Direction sortDirection;
    private Queue<String> selectedOperators;
    private List<String> groupedFields;
    private Integer limit;

    public List<String> getGroupedFields() {
        return groupedFields;
    }

    public void setGroupedFields(List<String> groupedFields) {
        this.groupedFields = groupedFields;
    }

    public Map<String, FilterUnit>  getFilters() {
        return filters;
    }

    public void setFilters(Map<String, FilterUnit> filters) {
        this.filters = filters;
    }

    public List<String> getSelectedFields() {
        return selectedFields;
    }

    public void setSelectedFields(List<String> selectedFields) {
        this.selectedFields = selectedFields;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    public Queue<String> getSelectedOperators() {
        return selectedOperators;
    }

    public void setSelectedOperators(Queue<String> selectedOperators) {
        this.selectedOperators = selectedOperators;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
