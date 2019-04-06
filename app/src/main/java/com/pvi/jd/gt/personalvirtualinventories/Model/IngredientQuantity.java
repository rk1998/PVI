package com.pvi.jd.gt.personalvirtualinventories.Model;

public class IngredientQuantity {

    private String ingredient;
    private String amount;

    public IngredientQuantity(String ingredient, String amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
