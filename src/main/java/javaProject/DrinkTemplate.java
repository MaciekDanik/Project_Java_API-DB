package javaProject;

import java.util.List;

public class DrinkTemplate {
    private String strDrink;
    private String strDrinkThumb;
    private String idDrink;

    public String getStrDrink() {
        return strDrink;
    }

    public void setStrDrink(String strDrink) {
        this.strDrink = strDrink;
    }

    public String getStrDrinkThumb() {
        return strDrinkThumb;
    }

    public void setStrDrinkThumb(String strDrinkThumb) {
        this.strDrinkThumb = strDrinkThumb;
    }

    public String getIdDrink() {
        return idDrink;
    }

    public void setIdDrink(String idDrink) {
        this.idDrink = idDrink;
    }

    @Override
    public String toString() {
        return "ID: " + idDrink + ",\t Name: " + strDrink + ",\t Alcoholic: \n";
    }
}

