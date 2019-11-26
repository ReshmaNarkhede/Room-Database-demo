package com.example.aigentestapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.aigentestapp.R;
import com.example.aigentestapp.database.entity.Car;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.ItemViewHolder> {
    private Context context;
    private LayoutInflater mInflater;
    List<Car> carList;
    private boolean isOnline;
    public interface OnItemClickListener {
        void onItemClick(Car item);
    }
    private OnItemClickListener listener;

    public CarListAdapter(Context context, List<Car> carList, OnItemClickListener listener, boolean isOnline) {
        this.context = context;
        this.carList = carList;
        this.listener = listener;
        this.isOnline = isOnline;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new CarListAdapter.ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Car data = carList.get(position);
        holder.bind(data, listener);
    }


    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.prize_txt)
        TextView prizeTxt;
        @BindView(R.id.year_txt)
        TextView yearTxt;
        @BindView(R.id.description_txt)
        TextView descTxt;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.setIsRecyclable(false);
            ButterKnife.bind(this, itemView);

        }
        public void bind(final Car data, final CarListAdapter.OnItemClickListener listener) {
            prizeTxt.setText(String.valueOf(data.prize));
            descTxt.setText(data.carModel);
            yearTxt.setText(String.format("%d- %s", data.year, data.kmDriven));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(data);
                }
            });
            if (isOnline) {
                Glide.with(context)
                        .asBitmap()
                        .load(data.image)
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                image.setImageBitmap(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }
                        });
            }else{
                Glide.with(context)
                        .asBitmap()
                        .load(data.image)
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                image.setImageURI(Uri.parse(data.image));
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }
                        });

            }
        }
    }
}