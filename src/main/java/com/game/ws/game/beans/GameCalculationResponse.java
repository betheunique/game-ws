package com.game.ws.game.beans;

/**
 * Created by abhishekrai on 20/10/2016.
 */
public class GameCalculationResponse extends DefaultResponseBean {
    private int totalValue;

    public int getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }

    @Override
    public String toString() {
        return "GameCalculationResponse [totValue=" + totalValue + "]";
    }
}
