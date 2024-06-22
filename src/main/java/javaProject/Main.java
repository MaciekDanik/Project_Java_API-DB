package javaProject;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        DrinkAPI api = new DrinkAPI();
        CompletableFuture<Void> getDataFuture = api.getData();

        // Wait for getData operation to complete
        getDataFuture.get();

        for (Drink drink : api.getDrinks()) {
             System.out.println(drink.toString());
        }


//        Drink d = api.getDrinks().get(48);

//        CompletableFuture<Void> getDetailsFuture = api.getDetails(d);
//        getDetailsFuture.get(); // Wait for getDetails operation for each drink to complete

        System.out.println("end");

//        for (Drink drink : api.drinks) {
//            CompletableFuture<Void> getDetailsFuture = api.getDetails(drink);
//            getDetailsFuture.get(); // Wait for getDetails operation for each drink to complete
//        }
    }
}