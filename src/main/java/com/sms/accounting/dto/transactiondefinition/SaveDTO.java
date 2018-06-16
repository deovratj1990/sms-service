package com.sms.accounting.dto.transactiondefinition;

import java.util.Set;

public class SaveDTO {
    public class Particular {
        public class CostHeader {
            private Long id;

            private String name;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        private Long id;

        private CostHeader costHeader;

        private Double amount;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public CostHeader getCostHeader() {
            return costHeader;
        }

        public void setCostHeader(CostHeader costHeader) {
            this.costHeader = costHeader;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }
    }

    private Long costHeaderId;

    private String transactionFrom;

    private String transactionTo;

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

    public String getTransactionFrom() {
        return transactionFrom;
    }

    public void setTransactionFrom(String transactionFrom) {
        this.transactionFrom = transactionFrom;
    }

    public String getTransactionTo() {
        return transactionTo;
    }

    public void setTransactionTo(String transactionTo) {
        this.transactionTo = transactionTo;
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
