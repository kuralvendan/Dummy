package com.binary2quantumtechbase.andapp.intpro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.binary2quantumtechbase.andapp.intpro.adhesives.adhesivesbrands;
import com.binary2quantumtechbase.andapp.intpro.databases.SessionManager;
import com.binary2quantumtechbase.andapp.intpro.plywoods.plywoodbrands;

public class First_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    TextView textView;
    LinearLayout ply_wood,hardware,door,modular_kitchen,adhesive;
    String user_name, user_id;
    BottomNavigationView bottomNavigationView;
    SessionManager sessionManager;
    String single_price_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sessionManager = new SessionManager(this);


        Intent ply_wood_intent = getIntent();
        user_name = ply_wood_intent.getStringExtra("name");
        user_id = ply_wood_intent.getStringExtra("userid");
//        single_price_edit = ply_wood_intent.getStringExtra("Singleprice");
        Log.e("single","singleprice:"+single_price_edit);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        textView = (TextView) findViewById(R.id.titleproducts);
        textView.setSelected(true);
        textView.setSingleLine();

        ply_wood = findViewById(R.id.ply_wood);
        ply_wood.setOnClickListener(this);

        hardware = findViewById(R.id.hardware);
        hardware.setOnClickListener(this);

        door = findViewById(R.id.door);
        door.setOnClickListener(this);

        modular_kitchen = findViewById(R.id.modular_kitchen);
        modular_kitchen.setOnClickListener(this);


        adhesive = findViewById(R.id.adhesive);
        adhesive.setOnClickListener(this);



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
//                    case R.id.action_home:
//                        Intent in = new Intent(First_activity.this, First_activity.class);
//                        startActivity(in);
//                        break;
                    case R.id.action_cart:
                        Intent in1 = new Intent(First_activity.this, CartDetails.class);
//                        in1.putExtra("Singleprice2",single_price_edit);
                        startActivity(in1);
                        break;
                    case R.id.action_logout:
                        Intent in2 = new Intent(First_activity.this, Login.class);
                        sessionManager.logoutUser();
                        startActivity(in2);
                        break;
                }
                return false;
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        //else {
//            super.onBackPressed();
        //    }
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
            Intent in2 = new Intent(First_activity.this, About_us.class);
            startActivity(in2);
            return true;
        } else if(id == R.id.contact){
            Intent in3 = new Intent(First_activity.this, Contact.class);
            startActivity(in3);
            return true;
        } else if (id == R.id.rate){
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.ply_wood_1) {
            Intent ply_wood_intent = new Intent(First_activity.this, plywoodbrands.class);
            ply_wood_intent.putExtra("plywood", "Plywoods");
            ply_wood_intent.putExtra("user_name", user_name);
            Log.e("Dashboard","username:"+user_name);

            ply_wood_intent.putExtra("user_id",user_id);
            Log.e("Dashboard","userid:"+user_id);
            startActivity(ply_wood_intent);

        } else if (id == R.id.hardware_1) {
            Toast.makeText(First_activity.this, "1", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.doors_1) {
            Toast.makeText(First_activity.this, "2", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.modular_kitchen_1) {
            Toast.makeText(First_activity.this, "3", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.adhesive_1) {
            Intent adhesives_intent = new Intent(First_activity.this, adhesivesbrands.class);
            adhesives_intent.putExtra("adhesives", "Adhesives");
            adhesives_intent.putExtra("name", user_name);
            startActivity(adhesives_intent);
//            Toast.makeText(First_activity.this, "4", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.logout) {
            Intent in2 = new Intent(First_activity.this, Login.class);
            startActivity(in2);

        }

//        else if (id == R.id.home_1) {
//            Intent home = new Intent(First_activity.this, First_activity.class);
//            startActivity(home);
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ply_wood:
                Intent ply_wood_intent = new Intent(First_activity.this, plywoodbrands.class);
                ply_wood_intent.putExtra("plywood", "Plywoods");
                ply_wood_intent.putExtra("name", user_name);
                startActivity(ply_wood_intent);
                break;
            case R.id.hardware:
                Toast.makeText(First_activity.this, "1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.door:
                Toast.makeText(First_activity.this, "2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.modular_kitchen:
                Toast.makeText(First_activity.this, "3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.adhesive:
                Intent adhesives_intent = new Intent(First_activity.this, adhesivesbrands.class);
                adhesives_intent.putExtra("adhesives", "Adhesives");
                adhesives_intent.putExtra("name", user_name);
                startActivity(adhesives_intent);
//                Toast.makeText(First_activity.this, "4", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
