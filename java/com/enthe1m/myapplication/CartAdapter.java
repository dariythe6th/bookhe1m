package com.enthe1m.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide; // Добавьте этот импорт для Glide

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    public interface CartInteractionListener {
        void onItemRemoved(String bookId);
        void onQuantityChanged(String bookId, int newQuantity);
    }

    private List<CartItem> cartItems;
    private CartInteractionListener listener;

    public CartAdapter(List<CartItem> cartItems, CartInteractionListener listener) {
        this.cartItems = new ArrayList<>(cartItems);
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void updateItems(List<CartItem> newItems) {
        cartItems.clear();
        cartItems.addAll(newItems);
        notifyDataSetChanged();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView bookImage;
        private TextView bookTitle;
        private TextView bookAuthor;
        private TextView bookPrice;
        private TextView quantityText;
        private TextView itemTotal;
        private ImageButton increaseButton;
        private ImageButton decreaseButton;
        private ImageButton removeButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.cart_book_image);
            bookTitle = itemView.findViewById(R.id.cart_book_title);
            bookAuthor = itemView.findViewById(R.id.cart_book_author);
            bookPrice = itemView.findViewById(R.id.cart_book_price);
            quantityText = itemView.findViewById(R.id.cart_quantity); // Убедитесь, что ID совпадает с вашим XML
            itemTotal = itemView.findViewById(R.id.cart_item_total);
            increaseButton = itemView.findViewById(R.id.increase_button);
            decreaseButton = itemView.findViewById(R.id.decrease_button);
            removeButton = itemView.findViewById(R.id.remove_button);
        }

        public void bind(CartItem item) {
            String bookId = item.getBook().getId();

            bookTitle.setText(item.getBook().getTitle());
            bookAuthor.setText(item.getBook().getAuthor());
            bookPrice.setText(String.format("$%.2f", item.getBook().getPrice()));
            quantityText.setText(String.valueOf(item.getQuantity()));
            itemTotal.setText(String.format("Итого по позиции: $%.2f", item.getTotalPrice())); // Обновил текст для ясности

            // Загрузка изображения с помощью Glide
            Glide.with(itemView.getContext())
                    .load(item.getBook().getImageUrl()) // URL изображения из объекта BookShop
                    //.placeholder(R.drawable.placeholder_image) // Опционально: изображение-заполнитель
                    //.error(R.drawable.error_image) // Опционально: изображение при ошибке
                    .into(bookImage); // Куда загружать изображение

            increaseButton.setOnClickListener(v -> {
                int newQuantity = item.getQuantity() + 1;
                listener.onQuantityChanged(bookId, newQuantity);

            });

            decreaseButton.setOnClickListener(v -> {
                int newQuantity = item.getQuantity() - 1;
                // Не используйте getAdapterPosition() напрямую для удаления, так как это может привести к ошибкам
                // Адаптер обновится через refreshCartData() в CartFragment
                listener.onQuantityChanged(bookId, newQuantity); // Вызываем listener для обновления количества
                if (newQuantity < 1) { // Если количество становится 0, вызываем удаление
                    listener.onItemRemoved(bookId);
                }
            });

            removeButton.setOnClickListener(v -> {
                // Прямое удаление элемента из корзины
                listener.onItemRemoved(bookId);
            });
        }
    }
}