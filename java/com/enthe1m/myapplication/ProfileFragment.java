package com.enthe1m.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {

    private NavigationManager navigationManager;

    public ProfileFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView nameTextView = view.findViewById(R.id.profile_name);
        TextView statusTextView = view.findViewById(R.id.profile_status);
        TextView emailTextView = view.findViewById(R.id.profile_email);
        TextView booksCountTextView = view.findViewById(R.id.books_read_count);
        TextView reviewsCountTextView = view.findViewById(R.id.reviews_count);

        Button editButton = view.findViewById(R.id.edit_profile_button);
        Button logoutButton = view.findViewById(R.id.logout_button);

        String userName = "Дарья";
        String userStatus = "Люблю читать книги и писать отзывы";
        String userEmail = "darya@example.com";
        String booksReadCount = "15";
        String reviewsMadeCount = "8";

        nameTextView.setText(userName);
        statusTextView.setText(userStatus);
        emailTextView.setText(userEmail);
        booksCountTextView.setText(booksReadCount);
        reviewsCountTextView.setText(reviewsMadeCount);


        editButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Будет открыт экран редактирования профиля", Toast.LENGTH_SHORT).show();
        });

        logoutButton.setOnClickListener(v -> {

            Toast.makeText(getContext(), "Выход из аккаунта", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        return view;
    }
}