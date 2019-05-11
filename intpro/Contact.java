package com.binary2quantumtechbase.andapp.intpro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class Contact extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
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
            Intent in2 = new Intent(Contact.this, About_us.class);
            startActivity(in2);
            return true;
        } else if(id == R.id.contact){

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
            intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=venkatkural.travelmate&hl=en");
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this site!");
            startActivity(Intent.createChooser(intent, "Share"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Contact.this, First_activity.class);
        startActivity(intent);
        finish();
    }
}
