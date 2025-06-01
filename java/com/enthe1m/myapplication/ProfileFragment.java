package com.enthe1m.myapplication;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    public ProfileFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView nameTextView = view.findViewById(R.id.profile_name);
        TextView statusTextView = view.findViewById(R.id.profile_status);
        TextView emailTextView = view.findViewById(R.id.profile_email);
        TextView booksCountTextView = view.findViewById(R.id.books_read_count);
        TextView reviewsCountTextView = view.findViewById(R.id.reviews_count);

        Button editButton = view.findViewById(R.id.edit_profile_button);
        Button logoutButton = view.findViewById(R.id.logout_button);

        // Пример данных
        nameTextView.setText("Дарья");
        statusTextView.setText("Люблю читать книги и писать отзывы");
        emailTextView.setText("darya@example.com");
        booksCountTextView.setText("15");
        reviewsCountTextView.setText("8");

        editButton.setOnClickListener(v -> {
            // TODO: Открыть экран редактирования профиля
        });

        logoutButton.setOnClickListener(v -> {
            // TODO: Реализовать выход
        });


        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}
