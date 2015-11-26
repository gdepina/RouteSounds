package com.routesounds.routesounds;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedListActivity extends ActionBarActivity {

    private Firebase mRef;
    ArrayList<Band> bandList = new ArrayList<Band>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_list);
        initList();
        Firebase.setAndroidContext(this);

        //ArrayAdapter<Band> itemsAdapter = new ArrayAdapter<Band>(this, android.R.layout.simple_list_item_1, bandList);
        BandAdapter itemsAdapter = new BandAdapter(this, bandList);

        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Band band = bandList.get(position);
                String ytUrl = band.getWebsite();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(ytUrl));
                startActivity(i);

            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed, menu);
        return true;
    }
    public void sendMaps(View view) {
        finish();
    }
    //List<Map<String,String>> bandList = new ArrayList<Map<String,String>>();
    private void initList(){


        mRef = new Firebase("https://flickering-inferno-8984.firebaseio.com/bands");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
                for (DataSnapshot bandSnapshot : snapshot.getChildren()) {
                    Band b = bandSnapshot.getValue(Band.class);
                    bandList.add(b);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public void returnHome(View view) {
        finish();
    }
}
