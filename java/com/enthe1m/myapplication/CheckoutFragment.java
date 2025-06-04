package com.enthe1m.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CheckoutFragment extends Fragment {

    private NavigationManager navigationManager;

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
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        EditText nameEditText = view.findViewById(R.id.name_edit_text);
        EditText emailEditText = view.findViewById(R.id.email_edit_text);
        EditText addressEditText = view.findViewById(R.id.address_edit_text);
        EditText cardEditText = view.findViewById(R.id.card_edit_text);
        Button submitButton = view.findViewById(R.id.submit_order_button);

        submitButton.setOnClickListener(v -> {
            if (nameEditText.getText().toString().isEmpty() ||
                    emailEditText.getText().toString().isEmpty() ||
                    addressEditText.getText().toString().isEmpty() ||
                    cardEditText.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            // Оформление заказа успешно, теперь очищаем корзину
            CartManager.getInstance().clearCart();

            Toast.makeText(getContext(), "Заказ оформлен!", Toast.LENGTH_LONG).show();


            if (navigationManager != null) {
                navigationManager.navigateToFragment(new CartFragment(), false);
            }
        });

        return view;
    }
}