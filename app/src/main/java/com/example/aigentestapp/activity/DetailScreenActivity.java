package com.example.aigentestapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.aigentestapp.R;
import com.example.aigentestapp.database.entity.Car;
import com.example.aigentestapp.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailScreenActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_layout)
    Toolbar toolbar;
    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.name_edit_text)
    TextView nameTxt;
    @BindView(R.id.city_edt_txt)
    TextView cityTxt;
    @BindView(R.id.phone_edt_txt)
    TextView phoneTxt;
    @BindView(R.id.prize_edt_txt)
    TextView prizeTxt;
    @BindView(R.id.year_edt_txt)
    TextView yearTxt;
    @BindView(R.id.km_txt)
    TextView kmTxt;
    @BindView(R.id.brand_txt)
    TextView brandTxt;
    @BindView(R.id.desc_txt)
    TextView descTxt;
    @BindView(R.id.fuel_txt)
    TextView fuelTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_screen);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail Screen");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Car obj = (Car) getIntent().getSerializableExtra(Constants.CAR_MODEL);

        Toast.makeText(this, "car:  " + obj.carModel, Toast.LENGTH_SHORT).show();

        nameTxt.setText(obj.sellerName);
        cityTxt.setText(obj.sellerCity);
        phoneTxt.setText(obj.sellerPhone);
        prizeTxt.setText(obj.prize);
        yearTxt.setText(String.valueOf(obj.year));
        kmTxt.setText(obj.kmDriven);
        brandTxt.setText(obj.carModel);
        descTxt.setText(obj.description);
        fuelTxt.setText(obj.fuelType);
        Glide.with(DetailScreenActivity.this)
                .asBitmap()
                .load(obj.image)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }
    @OnClick({R.id.send_mail_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.send_mail_btn: {
                sendMail();
            }
            break;

        }
    }

    private void sendMail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","developer@aigen.tech", null));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Requesting for car details");
        intent.putExtra(Intent.EXTRA_TEXT, "Hi");
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
