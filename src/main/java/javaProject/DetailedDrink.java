package javaProject;

import java.util.List;

public class DetailedDrink {
    private List<DetailedDrinkTemplate> drinks;

    public List<DetailedDrinkTemplate> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<DetailedDrinkTemplate> drinks) {
        this.drinks = drinks;
    }

    @Override
    public String toString() {
        return drinks.toString();
    }
}
