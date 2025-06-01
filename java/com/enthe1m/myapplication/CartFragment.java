package com.enthe1m.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartFragment extends Fragment {

    private NavigationManager navigationManager;
    private CartAdapter adapter;
    private TextView totalTextView;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof NavigationManager) {
            navigationManager = (NavigationManager) getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.cart_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        totalTextView = view.findViewById(R.id.total_price);
        Button checkoutButton = view.findViewById(R.id.checkout_button);

        setupAdapter();

        checkoutButton.setOnClickListener(v -> {
            if (CartManager.getInstance().isEmpty()) {
                Toast.makeText(getContext(), "Корзина пуста", Toast.LENGTH_SHORT).show();
                return;
            }
            CheckoutFragment fragment = new CheckoutFragment();
            navigationManager.navigateToFragment(fragment, true);
        });

        return view;
    }

    private void setupAdapter() {
        List<CartItem> cartItems = CartManager.getInstance().getCartItems();
        adapter = new CartAdapter(cartItems, new CartAdapter.CartInteractionListener() {
            @Override
            public void onItemRemoved(String bookId) {
                CartManager.getInstance().removeFromCart(bookId);
                refreshCartData();
            }

            @Override
            public void onQuantityChanged(String bookId, int newQuantity) {
                CartManager.getInstance().updateQuantity(bookId, newQuantity);
                updateTotalPrice();
                refreshCartData();
            }
        });
        recyclerView.setAdapter(adapter);
        updateTotalPrice();
    }

    private void refreshCartData() {
        adapter.updateItems(CartManager.getInstance().getCartItems());
        updateTotalPrice();

        if (CartManager.getInstance().isEmpty()) {
            Toast.makeText(getContext(), "Корзина пуста", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTotalPrice() {
        totalTextView.setText(String.format("Итого: $%.2f",
                CartManager.getInstance().getTotalPrice()));
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshCartData();
    }
}