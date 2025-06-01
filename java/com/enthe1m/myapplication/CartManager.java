package com.enthe1m.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<CartItem> cartItems = new ArrayList<>();
    private SharedPreferences prefs;
    private static final String CART_PREFS = "cart_prefs";
    private static final String CART_ITEMS_KEY = "cart_items";

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void initialize(Context context) {
        prefs = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE);
        loadCart();
    }

    public void addToCart(BookShop book, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getBook().getId().equals(book.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                saveCart();
                return;
            }
        }
        cartItems.add(new CartItem(book, quantity));
        saveCart();
    }

    public void removeFromCart(String bookId) {
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getBook().getId().equals(bookId)) {
                cartItems.remove(i);
                saveCart();
                break;
            }
        }
    }

    public void updateQuantity(String bookId, int newQuantity) {
        for (CartItem item : cartItems) {
            if (item.getBook().getId().equals(bookId)) {
                if (newQuantity <= 0) {
                    removeFromCart(bookId);
                } else {
                    item.setQuantity(newQuantity);
                    saveCart();
                }
                return;
            }
        }
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems);
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void clearCart() {
        cartItems.clear();
        saveCart();
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    private void saveCart() {
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartItems);
        editor.putString(CART_ITEMS_KEY, json);
        editor.apply();
    }

    private void loadCart() {
        Gson gson = new Gson();
        String json = prefs.getString(CART_ITEMS_KEY, null);
        Type type = new TypeToken<ArrayList<CartItem>>() {}.getType();
        List<CartItem> items = gson.fromJson(json, type);
        if (items != null) {
            cartItems = items;
        }
    }
}