package com.example.recycleview.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleview.Model.UserModel;
import com.example.recycleview.R;
import com.example.recycleview.UserActivity;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> mObjects; // Danh sách dữ liệu
    private static final int TEXT = 0, IMAGE = 1, USER = 2;

    public CustomAdapter(UserActivity userActivity, List<Object> objects) {
        this.mObjects = objects;
    }

    @Override
    public int getItemViewType(int position) {
        if (mObjects.get(position) instanceof String)
            return TEXT;
        else if (mObjects.get(position) instanceof Integer)
            return IMAGE;
        else if (mObjects.get(position) instanceof UserModel)
            return USER;
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case TEXT:
                View textView = inflater.inflate(R.layout.row_text, parent, false);
                return new TextViewHolder(textView);

            case IMAGE:
                View imageView = inflater.inflate(R.layout.row_image, parent, false);
                return new ImageViewHolder(imageView);

            case USER:
                View userView = inflater.inflate(R.layout.row_user, parent, false);
                return new UserViewHolder(userView);

            default:
                throw new IllegalArgumentException("Invalid view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case TEXT:
                ((TextViewHolder) holder).bind((String) mObjects.get(position));
                break;

            case IMAGE:
                ((ImageViewHolder) holder).bind((Integer) mObjects.get(position));
                break;

            case USER:
                ((UserViewHolder) holder).bind((UserModel) mObjects.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    // ViewHolder cho TEXT
    static class TextViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        TextViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
        }

        void bind(String text) {
            textView.setText(text);
        }
    }

    // ViewHolder cho IMAGE
    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }

        void bind(Integer imageRes) {
            imageView.setImageResource(imageRes);
        }
    }

    // ViewHolder cho USER
    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userName;

        UserViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.tv_name);
        }

        void bind(UserModel user) {
            userName.setText(user.getName());
        }
    }
}
