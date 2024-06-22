package javaProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DrinkAPI {
    private List<Drink> drinks = new ArrayList<>();

    public List<Drink> getDrinks(){
        return drinks;
    }

    public CompletableFuture<Void> getData() {
        HttpClient client = HttpClient.newHttpClient();
        CompletableFuture<Void> future = new CompletableFuture<>();

        // For alcoholic drinks
        HttpRequest requestAlc = HttpRequest.newBuilder()
                .uri(URI.create("https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Alcoholic"))
                .build();

        client.sendAsync(requestAlc, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(json -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        Drinks tmpDrinksAlc = mapper.readValue(json, Drinks.class);
                        return tmpDrinksAlc;
                    } catch (Exception e) {
                        future.completeExceptionally(e);
                        return null;
                    }
                })
                .thenAccept(tmpDrinksAlc -> {
                    if (tmpDrinksAlc != null) {
                        int id = 1;
                        for (var drink : tmpDrinksAlc.getDrinks()) {
                            Drink tmp = new Drink();
                            tmp.setSearchID(drink.getIdDrink());
                            tmp.setName(drink.getStrDrink());
                            tmp.setDrinkPIC(drink.getStrDrinkThumb());
                            tmp.setAlcoholic(true);
                            tmp.setId(id++);

                            drinks.add(tmp);
                        }
                    }
                })
                .thenCompose(v -> {
                    // For non-alcoholic drinks
                    HttpRequest requestNonAlc = HttpRequest.newBuilder()
                            .uri(URI.create("https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Non_Alcoholic"))
                            .build();

                    return client.sendAsync(requestNonAlc, HttpResponse.BodyHandlers.ofString())
                            .thenApply(HttpResponse::body)
                            .thenApply(json -> {
                                try {
                                    ObjectMapper mapper = new ObjectMapper();
                                    Drinks tmpDrinksNonAlc = mapper.readValue(json, Drinks.class);
                                    return tmpDrinksNonAlc;
                                } catch (Exception e) {
                                    future.completeExceptionally(e);
                                    return null;
                                }
                            });
                })
                .thenAccept(tmpDrinksNonAlc -> {
                    if (tmpDrinksNonAlc != null) {
                        int id = drinks.size() + 1;
                        for (var drink : tmpDrinksNonAlc.getDrinks()) {
                            Drink tmp = new Drink();
                            tmp.setSearchID(drink.getIdDrink());
                            tmp.setName(drink.getStrDrink());
                            tmp.setDrinkPIC(drink.getStrDrinkThumb());
                            tmp.setAlcoholic(false);
                            tmp.setId(id++);

                            drinks.add(tmp);
                        }
                    }
                    future.complete(null);
                })
                .exceptionally(e -> {
                    future.completeExceptionally(e);
                    return null;
                });

        return future;
    }


//    public CompletableFuture<Void> getDetails(Drink drink) {
//        HttpClient client = HttpClient.newHttpClient();
//        String urlAddress = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=" + drink.getSearchID();
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(urlAddress))
//                .build();
//
//        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
//                .thenApply(HttpResponse::body)
//                .thenApply(json -> {
//                    try {
//                        ObjectMapper mapper = new ObjectMapper();
//                        DetailedDrink detailedDrink = mapper.readValue(json, DetailedDrink.class);
//                        return detailedDrink;
//                    } catch (Exception e) {
//                        throw new RuntimeException("Failed to parse detailed drink data", e);
//                    }
//                })
//                .thenAccept(detailedDrink -> {
//                    String ingredient = detailedDrink.getDrinks().get(0).getStrIngredient1();
//                    if (ingredient != null) {
//                        System.out.println("ID: " + drink.getId() + "\t Name: " + ingredient);
//                    } else {
//                        System.out.println("NULL");
//                    }
//                })
//                .exceptionally(e -> {
//                    System.err.println("Error retrieving drink details: " + e.getMessage());
//                    return null;
//                });
//    }
}