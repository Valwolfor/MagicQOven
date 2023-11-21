package com.magicqoven.entity.DTO;

public class FilterUnit {

    private String field;
    private String operator;
    private String isInt;
    private String operant;

    public String getIsInt() {
        return isInt;
    }

    public void setIsInt(String isInt) {
        this.isInt = isInt;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperant() {
        return operant;
    }

    public void setOperant(String operant) {
        this.operant = operant;
    }

    @Override
    public String toString() {
        return "FilterUnit{" +
                "field='" + field + '\'' +
                ", operator='" + operator + '\'' +
                ", isInt='" + isInt + '\'' +
                ", operant='" + operant + '\'' +
                '}';
    }
}
