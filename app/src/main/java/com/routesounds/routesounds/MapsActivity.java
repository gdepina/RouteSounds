package com.routesounds.routesounds;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Firebase mRef;
    private  LatLng since;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        address = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (!address.equals("")) {
            since = getLatLongFromAddress(Uri.encode(address));
        }

        Firebase.setAndroidContext(this);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

        if (!address.equals("")) {
            PolylineOptions options = new PolylineOptions();
            options.color(Color.parseColor("#CC0000FF"));
            options.width(5);
            options.visible(true);
            options.add(since);
            options.add(new LatLng(-34.6288693, -58.3564459));
            mMap.addPolyline(options);
            mMap.addMarker(new MarkerOptions().position(since).title(address));
        }
        // Add a marker in Sydney and move the camera
            LatLng usinaarte = new LatLng(-34.6288693, -58.3564459);

            mMap.addMarker(new MarkerOptions().position(usinaarte).title("Usina del Arte"));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(12));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(usinaarte));

        mRef = new Firebase("https://flickering-inferno-8984.firebaseio.com/bands");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
                for (DataSnapshot bandSnapshot : snapshot.getChildren()) {
                    Band b = bandSnapshot.getValue(Band.class);
                    String[] splited = b.getAddress().split(",");
                    LatLng pos = new LatLng(Double.parseDouble(splited[0]), Double.parseDouble(splited[1]));
                    mMap.addMarker(new MarkerOptions().position(pos).title(b.getName()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.routesoundsmarker)));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    public void returnHome(View view) {
        finish();
    }


    public void sendFeed(View view) {
        Intent intent = new Intent(this, FeedListActivity.class);
        startActivity(intent);
    }

    public static LatLng getLatLongFromAddress(String youraddress) {
        String uri = "http://maps.google.com/maps/api/geocode/json?address=" +
                youraddress + "+buenos+aires&sensor=false";
        HttpGet httpGet = new HttpGet(uri);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());

            double lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            double lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

            Log.d("latitude", "" + lat);
            Log.d("longitude", "" + lng);
            LatLng coordinates = new LatLng(lat,lng);
            return coordinates;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
