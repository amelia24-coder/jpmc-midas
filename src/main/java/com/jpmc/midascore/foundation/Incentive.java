package com.jpmc.midascore.foundation;

public class Incentive {

    private long amount;

    public Incentive() {
        
    }

    public Incentive(long amount) {
        this.amount = amount;
    }

    public long getIncentiveAmount() {
        return this.amount;
    }
}
