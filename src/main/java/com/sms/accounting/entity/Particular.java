package com.sms.accounting.entity;

import javax.persistence.*;

@Entity
public class Particular {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private CostHeader costHeader;

    @Column
    private Double amount;

    @ManyToOne
    private TransactionDefinition transactionDefinition;

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

    public TransactionDefinition getTransactionDefinition() {
        return transactionDefinition;
    }

    public void setTransactionDefinition(TransactionDefinition transactionDefinition) {
        this.transactionDefinition = transactionDefinition;
    }
}
