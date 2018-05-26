package com.sms.accounting.entity;

import com.sms.common.converter.ZonedDateTimeConverter;
import com.sms.user.entity.User;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class TransactionDefinition {
    public enum AccountType {
        SOCIETY,
        MEMBER,
        VENDOR,
        CUSTOMER
    }

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
    private AccountType transactionFrom;

    @Enumerated(EnumType.STRING)
    private AccountType transactionTo;

    @Column(name = "intervalType")
    private Interval interval;

    @Column(name = "fromDate")
    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime from;

    @Column
    private Double amount;

    @OneToMany(mappedBy = "transactionDefinition")
    private Set<Particular> particulars;

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

    public AccountType getTransactionFrom() {
        return transactionFrom;
    }

    public void setTransactionFrom(AccountType transactionFrom) {
        this.transactionFrom = transactionFrom;
    }

    public AccountType getTransactionTo() {
        return transactionTo;
    }

    public void setTransactionTo(AccountType transactionTo) {
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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
