package ch.teko.currencytrader.models;

public class TradeBuilder {

    String customer = "Customer";
    double sourceAmount = 0;
    Currency sourceCur = Currency.CHF;
    double targetAmount = 0;
    Currency targetCur = Currency.CHF;

    public Trade build() {
        return new Trade(customer, new Money(sourceAmount, sourceCur), new Money(targetAmount, targetCur));
    }

    public TradeBuilder addCustomer(String customer) {
        if (customer.isEmpty()) {
            this.customer = "Milburn Moneybag";
        } else {
            this.customer = customer;
        }

        return this;
    }

    public TradeBuilder addSourceAmount(String sourceAmount) {
        try {
            double number = Double.parseDouble(sourceAmount);
            if (number > 0) {
                this.sourceAmount = number;
            }
        } catch (NumberFormatException ex) {
            // Only change on legal state.
        }

        return this;
    }

    public TradeBuilder addSourceCurrency(String currency) {
        try {
            sourceCur = Currency.valueOf(currency);
        } catch (IllegalArgumentException ex) {
            // Only change on legal state.
        }
        return this;
    }

    public TradeBuilder addTargetAmount(String targetAmount) {
        try {
            double number = Double.parseDouble(targetAmount);
            if (number > 0) {
                this.targetAmount = number;
            }
        } catch (NumberFormatException ex) {
            // Only change on legal state.
        }

        return this;
    }

    public TradeBuilder addTargetCurrency(String currency) {
        try {
            targetCur = Currency.valueOf(currency);
        } catch (IllegalArgumentException ex) {
            // Only change on legal state.
        }
        return this;
    }
}
