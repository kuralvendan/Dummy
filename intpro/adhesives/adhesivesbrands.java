package com.binary2quantumtechbase.andapp.intpro.adhesives;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.binary2quantumtechbase.andapp.intpro.CartDetails;
import com.binary2quantumtechbase.andapp.intpro.First_activity;
import com.binary2quantumtechbase.andapp.intpro.Login;
import com.binary2quantumtechbase.andapp.intpro.R;

public class adhesivesbrands extends AppCompatActivity {
    ListView adhe_list;
    String username, userid, brand;
    String [] adhe_type = {"Fevicol(G)","Fevicol(L)", "Araldite(G)","Araldite(L)", "3M"};


    ArrayAdapter adapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adhesives_brand);

        Intent ply_wood_intent = getIntent();
        // ply_wood_intent.getStringExtra("name");
        username = ply_wood_intent.getStringExtra("user_name");
        userid = ply_wood_intent.getStringExtra("user_id");


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent in = new Intent(adhesivesbrands.this, First_activity.class);
                        startActivity(in);
                        break;
                    case R.id.action_cart:
                        Intent in1 = new Intent(adhesivesbrands.this, CartDetails.class);
                        startActivity(in1);
                        break;
                    case R.id.action_logout:
                        Intent in2 = new Intent(adhesivesbrands.this, Login.class);
                        startActivity(in2);
                        break;
                }
                return false;
            }
        });

        adhe_list = findViewById(R.id.expandable_adh);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, adhe_type);
        adhe_list.setAdapter(adapter);
        adhe_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                brand = adhe_type[position];

                Intent intent = new Intent(adhesivesbrands.this, adhesivesdetails.class);
                intent.putExtra("user_name",username);
                Log.e("adhesivesbrands","username:"+username);

                intent.putExtra("user_id",userid);
                Log.e("adhesivesbrands","userid:"+userid);

                intent.putExtra("adhesives","Adhesives");
                Log.e("adhesivesbrands","adhesives:"+"Adhesives");

                intent.putExtra("brand", brand);
                Log.e("adhesivesbrands","brand:"+brand);
                startActivity(intent);

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(adhesivesbrands.this, First_activity.class);
        startActivity(intent);
        finish();
    }

}
