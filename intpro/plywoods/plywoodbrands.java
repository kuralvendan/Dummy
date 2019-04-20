package com.binary2quantum.android.intpro.plywoods;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.binary2quantum.android.intpro.About_us;
import com.binary2quantum.android.intpro.CartDetails;
import com.binary2quantum.android.intpro.Contact;
import com.binary2quantum.android.intpro.First_activity;
import com.binary2quantum.android.intpro.Login;
import com.binary2quantum.android.intpro.R;

public class plywoodbrands extends AppCompatActivity {

    ListView ply_list;
    String username, userid, brand;
    String [] ply_type = {"Green Gold","Green Ply","Green Gold Platinum","ECOTEC 710","ECOTEC Platinum","ECOTEC MR","Optima Red","Kitply Gold",
    "Kitply Marine","Kitply Vista","Century Sainik 710","Century Club Prime","Century sainik MR","Century Bond","Sharon Gold","Sharon Prima",
    "Fineply 303","Fineply 710"};


    ArrayAdapter adapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plywoodtypes);

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
                        Intent in = new Intent(plywoodbrands.this, First_activity.class);
                        startActivity(in);
                        break;
                    case R.id.action_cart:
                        Intent in1 = new Intent(plywoodbrands.this, CartDetails.class);
                        startActivity(in1);
                        break;
                    case R.id.action_logout:
                        Intent in2 = new Intent(plywoodbrands.this, Login.class);
                        startActivity(in2);
                        break;
                }
                return false;
            }
        });

        ply_list = findViewById(R.id.expandable);
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,ply_type);
        ply_list.setAdapter(adapter);
        ply_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             brand = ply_type[position];

                Intent intent = new Intent(plywoodbrands.this,plywooddetails.class);
                intent.putExtra("user_name",username);
                Log.e("plywoodbrands","username:"+username);

                intent.putExtra("user_id",userid);
                Log.e("plywoodbrands","userid:"+userid);

                intent.putExtra("plywood","Plywoods");
                Log.e("plywoodbrands","plywoods:"+"Plywoods");

                intent.putExtra("brand", brand);
                Log.e("plywoodbrands","brand:"+brand);
                startActivity(intent);

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(plywoodbrands.this, First_activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about_us) {
            Intent in2 = new Intent(plywoodbrands.this, About_us.class);
            startActivity(in2);
            return true;
        }  else if(id == R.id.contact){
            Intent in3 = new Intent(plywoodbrands.this, Contact.class);
            startActivity(in3);
            return true;
        }  else if (id == R.id.rate){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            // https://play.google.com/store/apps/details?id=com.binary2quantum.android.intpro
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.binary2quantum.android.intpro"));
            startActivity(intent);
            return true;
        } else if (id == R.id.share){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.binary2quantum.android.intpro");
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this site!");
            startActivity(Intent.createChooser(intent, "Share"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}