package javaProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.sql.*;

public class GUI {
    Vector<Drink> drinks = new Vector<Drink>();
    Vector<Drink> drinksFromDB = new Vector<Drink>();
    Vector<Drink> favDrinks = new Vector<Drink>();
    String DrinkDB_url = "jdbc:sqlite:Drinks.db";
    String FavDrinkDB_url = "jdbc:sqlite:Fav_Drinks.db";


    private JLabel lbl_Title;
    private JList lst_drinks;
    private JButton btn_addToFavDB;
    private JButton btn_Show_Fav;
    private JPanel panel;
    private JButton btn_getData;
    private JButton btn_saveToDB;
    private JButton btn_LoadFromDb;
    private JLabel lbl_ListTitle;
    private JTextArea txt_Area;


    public GUI() {
        btn_Show_Fav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lst_drinks.removeAll();

                getFavFromDB();
                lbl_ListTitle.setText("Favourites");
                lst_drinks.setListData(favDrinks);
            }
        });

        btn_addToFavDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = lst_drinks.getSelectedIndex();
                Drink tmp = drinks.get(index);
                String selectedDrink_searchID = tmp.getSearchID();
                String selectedDrink_name = tmp.getName();
                int selectedDrink_isAlcoholic;
                int selectedDrink_isFavourite = 1;
                if (tmp.isAlcoholic()) { selectedDrink_isAlcoholic = 1; } else { selectedDrink_isAlcoholic = 0; }

                //if (tmp.isFavourite()) { selectedDrink_isFavourite = 1; } else { selectedDrink_isFavourite = 0; }


//                String sql = "INSERT INTO hits (id, searchID, name, isAlcoholic, isFavourite) VALUES (?, ?, ?, ?, ?)";
                String sql = "INSERT INTO fav_drinks (searchID, name, isAlcoholic, isFavourite) VALUES (?, ?, ?, ?)";

                try (var conn = DriverManager.getConnection(FavDrinkDB_url);
                    var pstmt = conn.prepareStatement(sql)){
                    pstmt.setString(1, selectedDrink_searchID);
                    pstmt.setString(2, selectedDrink_name);
                    pstmt.setInt(3, selectedDrink_isAlcoholic);
                    pstmt.setInt(4, selectedDrink_isFavourite);

                    pstmt.executeUpdate();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        });

        btn_getData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lbl_ListTitle.setText("Drinks from API");
                try {
                    DrinkAPI api = new DrinkAPI();
                    CompletableFuture<Void> getDataFuture = api.getData();
                    // Wait for getData operation to complete
                    getDataFuture.get();


//                    txt_Area.setText("OK");
                    for (Drink drink : api.getDrinks()) {
    //                    System.out.println(drink.toString());
                        drinks.add(drink);
//                        txt_Area.setText(drink.toString());
                    }

                    lst_drinks.setListData(drinks);


                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btn_saveToDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = "INSERT INTO drinks (searchID, name, isAlcoholic, isFavourite) VALUES (?, ?, ?, ?)";
                for (var drink: drinks) {
                    int selectedDrink_isAlcoholic;
                    int selectedDrink_isFavourite;

                    if (drink.isAlcoholic()) { selectedDrink_isAlcoholic = 1; } else { selectedDrink_isAlcoholic = 0; }

                    if (drink.isFavourite()) { selectedDrink_isFavourite = 1; } else { selectedDrink_isFavourite = 0; }

                    try (var conn = DriverManager.getConnection(DrinkDB_url);
                         var pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, drink.getSearchID());
                        pstmt.setString(2, drink.getName());
                        pstmt.setInt(3, selectedDrink_isAlcoholic);
                        pstmt.setInt(4, selectedDrink_isFavourite);

                        pstmt.executeUpdate();
                    } catch (SQLException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            }
        });
        btn_LoadFromDb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lst_drinks.removeAll();

                getFromDB();

                lst_drinks.setListData(drinksFromDB);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DrinkGUI");
        frame.setContentPane(new GUI().panel);
        frame.setPreferredSize(new Dimension(1200, 800));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void getFromDB(){
        var sql = "SELECT * FROM drinks";
        drinksFromDB.clear();
        lbl_ListTitle.setText("Drinks from DB");

        try (var conn = DriverManager.getConnection(DrinkDB_url);
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Drink drink = new Drink();
                drink.setId(rs.getInt("id"));
                drink.setSearchID(rs.getString("searchID"));
                drink.setName(rs.getString("name"));

                Boolean Alc;
                if (rs.getInt("isAlcoholic")==1) {Alc = true;} else {Alc=false;}
                drink.setAlcoholic(Alc);

                Boolean Fav;
                if (rs.getInt("isFavourite")==1) {Fav = true;} else {Fav=false;}
                drink.setAlcoholic(Alc);

                drinksFromDB.add(drink);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


    }

    public void getFavFromDB(){
        var sql = "SELECT * FROM fav_drinks";
        favDrinks.clear();

        try (var conn = DriverManager.getConnection(FavDrinkDB_url);
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {


            while (rs.next()) {
                Drink drink = new Drink();
                drink.setId(rs.getInt("id"));
                drink.setSearchID(rs.getString("searchID"));
                drink.setName(rs.getString("name"));

                Boolean Alc;
                if (rs.getInt("isAlcoholic")==1) {Alc = true;} else {Alc=false;}
                drink.setAlcoholic(Alc);

                Boolean Fav;
                if (rs.getInt("isFavourite")==1) {Fav = true;} else {Fav=false;}
                drink.setAlcoholic(Alc);

                favDrinks.add(drink);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


    }
}
