package com.sms.accounting.controller.dto.transactiondefinition;

import java.util.Set;

public class SaveDTO {
    public class Particular {
        private String name;

        private Double amount;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }
    }

    private Long costHeaderId;

    private String transactionType;

    private String applicableTo;

    private String interval;

    private String from;

    private Boolean hasParticulars;

    private Set<Particular> particulars;

    private Double amount;

    public Long getCostHeaderId() {
        return costHeaderId;
    }

    public void setCostHeaderId(Long costHeaderId) {
        this.costHeaderId = costHeaderId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getApplicableTo() {
        return applicableTo;
    }

    public void setApplicableTo(String applicableTo) {
        this.applicableTo = applicableTo;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Boolean getHasParticulars() {
        return hasParticulars;
    }

    public void setHasParticulars(Boolean hasParticulars) {
        this.hasParticulars = hasParticulars;
    }

    public Set<Particular> getParticulars() {
        return particulars;
    }

    public void setParticulars(Set<Particular> particulars) {
        this.particulars = particulars;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
