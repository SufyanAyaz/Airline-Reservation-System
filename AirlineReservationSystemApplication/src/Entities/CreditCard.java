package Entities;

public class CreditCard {
    private long cardNumber;
    private int expiryDate;
    private int CVV;
    private float moneyOnCard;

    public CreditCard(long uCardNumber, int uExpiryDate, int uCVV, float uMoneyOnCard) {
        this.cardNumber = uCardNumber;
        this.expiryDate = uExpiryDate;
        this.CVV = uCVV;
        this.moneyOnCard = uMoneyOnCard;
    }

    // Getters
    public long getCardNumber() {
        return this.cardNumber;
    }

    public int getExpiryDate() {
        return this.expiryDate;
    }

    public int getCVV() {
        return this.CVV;
    }

    public float getMoneyOnCard() {
        return this.moneyOnCard;
    }
}
