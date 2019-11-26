package com.example.aigentestapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.aigentestapp.R;
import com.example.aigentestapp.adapter.CarListAdapter;
import com.example.aigentestapp.database.DatabaseSingleton;
import com.example.aigentestapp.database.entity.Car;
import com.example.aigentestapp.utils.Constants;
import com.example.aigentestapp.utils.Session;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity implements CarListAdapter.OnItemClickListener{
    @BindView(R.id.toolbar_layout)
    Toolbar toolbar;
    @BindView(R.id.add_advertise_fab)
    FloatingActionButton addFab;
    @BindView(R.id.online_list2)
    RecyclerView list2;
    @BindView(R.id.offline_list)
    RecyclerView list1;
    @BindView(R.id.label)
    TextView labelTxt;
    private CarListAdapter adapter1,adapter2;
    ArrayList<Car> carList = new ArrayList<>();
    private List<Car> offlineList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Car car = new Car();
        car.carName = "Mahindra";
        car.carModel = "Bolero";
        car.prize = "₹ 4,15,000 ";
        car.sellerName = "Ansar Pallachi";
        car.sellerCity = "Palarivattom, Kochi, Kerala ";
        car.sellerPhone = "1234567891";
        car.kmDriven = "120,000 km";
        car.year = 2011;
        car.description = "Aux Compatibility: Yes\n" +
                "Type of Car: SUV\n" +
                "Registration Place: KL\n" +
                "Transmission: Manual\n" +
                "Make Month: June\n" +
                "Condition: Used\n" +
                "Power Windows: Front & rear\n" +
                "No. of Owners: First\n" +
                "Variant: Bolero SLX 2WD    ";
        car.fuelType = "Diesel";
        car.image = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQbDSHAVZz7fe5-5bJQyRvmm29Wo_kVTF6Bsr0GAXE-y8W4tsrP";
        carList.add(car);

        Car car1 = new Car();
        car1.carName = "Volkswagen";
        car1.carModel = "Polo";
        car1.prize = "₹ 5,50,000";
        car1.sellerName = "M. Patil ";
        car1.sellerCity = "Baner, Pune, Maharashtra ";
        car1.sellerPhone = "";
        car1.kmDriven = "39,990 km";
        car1.year = 2017;
        car1.description = "Type of Car: Hatchback\n" +
                "Registration Place: MH\n" +
                "Service History: Available\n" +
                "Make Month:May \n" +
                "Condition: Used\n" +
                "Battery Condition: New\n" +
                "No. of Owners: First\n" +
                "Variant: Polo Comfortline Petrol  ";
        car1.fuelType = "Petrol";
        car1.image = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRW6TY-W1Fv24EeuqrnbDQWwOnkRKamvvIZNzIN0j0vKg-hjfmd";
        carList.add(car1);

        Car car2 = new Car();
        car2.carName = "Hyundai";
        car2.carModel = "I20";
        car2.prize = "₹ 2,25,000 ";
        car2.sellerName = "S. Patel";
        car2.sellerCity = "Kalka Garhi, Ghaziabad, Uttar Pradesh";
        car2.sellerPhone = "2384983649";
        car2.kmDriven = "61,000 km";
        car2.year = 2010;
        car2.description = "Aux Compatibility: Yes\n" +
                "Type of Car: Hatchback\n" +
                "Registration Place: UP\n" +
                "Transmission: Manual\n" +
                "Make Month: August\n" +
                "Condition: Used\n" +
                "No. of Owners: First\n" +
                "Variant:  i20 Sportz 1.2   ";
        car2.fuelType = "Petrol";
        car2.image = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSvh7GYpR7Z4lxRcenhPrOg9QSOT8MTG5MUW-3rwNV6zbRMm0Nv";
        carList.add(car2);

        adapter1 = new CarListAdapter(HomeActivity.this, carList,this,true);
        list1.setLayoutManager(new GridLayoutManager(this, 2));
        list1.setAdapter(adapter1);

        offlineList = DatabaseSingleton.GetDatabase(this).carDAO().GetCarInMemory();
        if(offlineList.size()==0){
            labelTxt.setVisibility(View.GONE);
        }else {
            labelTxt.setVisibility(View.VISIBLE);
        }
        adapter2 = new CarListAdapter(HomeActivity.this, offlineList,this,false);
        list2.setLayoutManager(new GridLayoutManager(this, 2));
        list2.setAdapter(adapter2);

        list1.setNestedScrollingEnabled(false);
        list2.setNestedScrollingEnabled(false);
    }

    @OnClick(R.id.add_advertise_fab)
    public void submit() {
        startActivity(new Intent(HomeActivity.this, AddAdvertisementActivity.class));
    }
    @OnClick({R.id.logout_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.logout_btn: {
                DatabaseSingleton.GetDatabase(HomeActivity.this).clearAllTables();
                Session.GetInstance(HomeActivity.this).Logout();
            }
            break;

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

    @Override
    protected void onResume() {
        super.onResume();
        offlineList.clear();
        offlineList = DatabaseSingleton.GetDatabase(this).carDAO().GetCarInMemory();
        if(offlineList.size()==0){
            labelTxt.setVisibility(View.GONE);
        }else {
            labelTxt.setVisibility(View.VISIBLE);
        }
        adapter2 = new CarListAdapter(HomeActivity.this, offlineList,this, false);
        list2.setLayoutManager(new GridLayoutManager(this, 2));
        list2.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Car item) {
        Intent intent = new Intent(HomeActivity.this, DetailScreenActivity.class);
        intent.putExtra(Constants.CAR_MODEL, (Serializable) item);
        startActivity(intent);
    }
}
