package com.sms.accounting.entity;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.sms.common.converter.ZonedDateTimeConverter;
import com.sms.user.entity.User;

@Entity
public class TransactionDefinition {
    public enum Interval {
        ONE_TIME,
        MONTHLY,
        QUARTERLY,
        HALF_YEARLY,
        YEARLY
    }

    private enum Status {
        ACTIVE,
        INACTIVE,
        DELETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private CostHeader costHeader;

    @Enumerated(EnumType.STRING)
    private Account.Type transactionFrom;

    @Enumerated(EnumType.STRING)
    private Account.Type transactionTo;

    @Column(name = "intervalType")
    @Enumerated(EnumType.STRING)
    private Interval interval;
    
    @Column(name = "fromDate")
    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime from;

    @OneToMany(mappedBy = "transactionDefinition")
    private Set<Particular> particulars;
    
    @Column
    private Double amount;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime createdOn;

    @OneToOne
    private User createdBy;

    public TransactionDefinition() {
        particulars = new HashSet<Particular>();
    }

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

    public Account.Type getTransactionFrom() {
        return transactionFrom;
    }

    public void setTransactionFrom(Account.Type transactionFrom) {
        this.transactionFrom = transactionFrom;
    }

    public Account.Type getTransactionTo() {
        return transactionTo;
    }

    public void setTransactionTo(Account.Type transactionTo) {
        this.transactionTo = transactionTo;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }
    
    public ZonedDateTime getFrom() {
        return from;
    }

    public void setFrom(ZonedDateTime from) {
        this.from = from;
    }

    public Set<Particular> getParticulars() {
        return particulars;
    }

    public void addParticular(Particular particular) {
        particulars.add(particular);
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
