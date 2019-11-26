package com.example.aigentestapp.activity;

import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aigentestapp.R;
import com.example.aigentestapp.adapter.CarListAdapter;
import com.example.aigentestapp.database.DatabaseSingleton;
import com.example.aigentestapp.database.entity.Car;
import com.example.aigentestapp.utils.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAdvertisementActivity extends BaseActivity  {
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

    private int ImageIndex = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_advertisement);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Advertise");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @OnClick({R.id.image,R.id.add_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.image: {
                ImageIndex = 1;
                ImagePickerActivity(AddAdvertisementActivity.this);
            }

            case R.id.add_btn: {
                    checkValidation();
            }
            break;

        }
    }

    public void SetImage(Uri uri, String path) {
        if (ImageIndex == 1) {
            imageView.setImageURI(uri);
            imageUri = uri;
            imageView.setTag(path);
        }
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
    private boolean checkValidation() {
        boolean isValid = true;
        String name = nameTxt.getText().toString();
        String city = cityTxt.getText().toString();
        String phoneNo = phoneTxt.getText().toString();
        String prize = prizeTxt.getText().toString();

        String year = yearTxt.getText().toString();
        String km = kmTxt.getText().toString();
        String brand = brandTxt.getText().toString();
        String description = descTxt.getText().toString();
        String fuel = fuelTxt.getText().toString();

        if (TextUtils.isEmpty(name)) {
            nameTxt.setError("Enter Name!");
            isValid = false;
        }
        if (TextUtils.isEmpty(city)) {
            cityTxt.setError("Enter City!");
            isValid = false;
        }
        if (TextUtils.isEmpty(brand)) {
            brandTxt.setError("Enter brand with model!");
            isValid = false;
        }
        if (TextUtils.isEmpty(prize)) {
            prizeTxt.setError("Enter Prize!");
            isValid = false;
        }
        if (TextUtils.isEmpty(year)) {
            yearTxt.setError("Enter Year!");
            isValid = false;
        }
        if (TextUtils.isEmpty(km)) {
            kmTxt.setError("Enter Km Driven!");
            isValid = false;
        }
        if (TextUtils.isEmpty(description)) {
            descTxt.setError("Enter Description!");
            isValid = false;
        }
        if (TextUtils.isEmpty(fuel)) {
            fuelTxt.setError("Enter fuel type!");
            isValid = false;
        }
        if (!isValidPhoneNumber(phoneNo)) {
            phoneTxt.setError("Please enter valid mobile number");
            isValid = false;
        }
        if(imageView.getTag()!=null){
            isValid = true;
        }else{
            isValid = false;
            Toast.makeText(this, "Please select photo", Toast.LENGTH_SHORT).show();
        }
        if (isValid) {
            SaveInLocalDB();
            return true;
        }
        return false;
    }

    private void SaveInLocalDB() {
        Toast.makeText(this, "Your Advertise is save in local memory.", Toast.LENGTH_SHORT).show();
        Car car = new Car();
        car.inMemory = "1";
        car.sellerName = nameTxt.getText().toString();
        car.sellerCity = cityTxt.getText().toString();
        car.sellerPhone = phoneTxt.getText().toString();
        car.prize = prizeTxt.getText().toString();
        car.year = Integer.parseInt(yearTxt.getText().toString());
        car.kmDriven = kmTxt.getText().toString();
        car.description = descTxt.getText().toString();
        car.fuelType = fuelTxt.getText().toString();
        car.carModel = brandTxt.getText().toString();
        car.image = imageUri.toString();
        DatabaseSingleton.GetDatabase(this).carDAO().Add(car);

        finish();
    }


}
