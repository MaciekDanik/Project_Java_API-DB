package javaProject;

import java.util.ArrayList;
import java.util.List;

public class Drink {
    private int id;
    private String searchID;
    private String name;
    private String alternateDrink;
    private String tags;
    private String category;
    private String glass;
    private String instructions;
    private String drinkPIC;
    private boolean isAlcoholic;
    private List<String> ingredients;
    private List<String> measures;
    private boolean detailed;
    private boolean favourite;

    public Drink() {
        this.ingredients = new ArrayList<>();
        this.measures = new ArrayList<>();
        this.detailed = false;
        this.favourite = false;
        this.tags = "";
        this.category = "";
        this.glass = "";
        this.instructions = "";
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSearchID() {
        return searchID;
    }

    public void setSearchID(String searchID) {
        this.searchID = searchID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlternateDrink() {
        return alternateDrink;
    }

    public void setAlternateDrink(String alternateDrink) {
        this.alternateDrink = alternateDrink;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getDrinkPIC() {
        return drinkPIC;
    }

    public void setDrinkPIC(String drinkPIC) {
        this.drinkPIC = drinkPIC;
    }

    public boolean isAlcoholic() {
        return isAlcoholic;
    }

    public void setAlcoholic(boolean isAlcoholic) {
        this.isAlcoholic = isAlcoholic;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getMeasures() {
        return measures;
    }

    public void setMeasures(List<String> measures) {
        this.measures = measures;
    }

    public boolean isDetailed() {
        return detailed;
    }

    public void setDetailed(boolean detailed) {
        this.detailed = detailed;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    @Override
    public String toString() {
        if (!favourite && !isAlcoholic) {
            return "ID: " + id + ",    Name: " + name + ",        Not Alcoholic" + System.lineSeparator();
        } else if (!favourite && isAlcoholic) {
            return "ID: " + id + ",    Name: " + name + ",        Alcoholic" + System.lineSeparator();
        } else if (favourite && isAlcoholic) {
            return "ID: " + id + ",    Name: " + name + ",        Alcoholic,        Fav" + System.lineSeparator();
        } else {
            return "ID: " + id + ",    Name: " + name + ",        Not Alcoholic,        Fav" + System.lineSeparator();
        }
    }

    public String ingrToString() {
        StringBuilder ingr = new StringBuilder();
        for (int i = 0; i < ingredients.size(); i++) {
            ingr.append(ingredients.get(i)).append(measures.get(i)).append(System.lineSeparator());
        }
        return ingr.toString();
    }
}

