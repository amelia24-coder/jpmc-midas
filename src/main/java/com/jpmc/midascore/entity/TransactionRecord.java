package com.jpmc.midascore.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name="Transaction")
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long transactionId;

    @Column
    private long senderId;

    @Column
    private long recipientId;

    @Column
    private float amount;

    @Column
    private long incentive;

    protected TransactionRecord() {

    }

    public TransactionRecord(long transactionId, long senderId, long recipientId, float amount, long incentive) {
        this.transactionId = transactionId;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.amount = amount;
        this.incentive = incentive;
    }

    public TransactionRecord(long senderId, long recipientId, float amount, long incentive) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.amount = amount;
        this.incentive = incentive;
    }

    public long getTransactionId() {
        return this.transactionId;
    }

    public long getSenderId() {
        return this.senderId;
    }

    public long getRecipientId() {
        return this.recipientId;
    }

    public float getAmount() {
        return this.amount;
    }
}
