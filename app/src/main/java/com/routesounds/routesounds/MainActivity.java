package com.routesounds.routesounds;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public final static String EXTRA_MESSAGE = "com.routesounds.routesounds.MESSAGE";
    RadioGroup radioGroup;
    TextView recorrido;
    TextView cargarBanda;
    LinearLayout recorridoLayout;
    ScrollView bandaLayout;
    Button boton;
    Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*radioGroup = (RadioGroup)findViewById(R.id.contactgroup);
        radioGroup.setOnCheckedChangeListener(radioGroupOnCheckedChangeListener);
*/
        /*TextView txt = (TextView) findViewById(R.id.custom_font);
        Typeface font = Typeface.createFromAsset(getAssets(), "MovistarHeadline-Bold.ttf");
        txt.setTypeface(font);*/

        recorrido = (TextView) findViewById(R.id.recorrido);
        recorrido.setOnClickListener(this);
        cargarBanda = (TextView) findViewById(R.id.cargar_banda);
        cargarBanda.setOnClickListener(this);

        bandaLayout = (ScrollView) findViewById(R.id.bandcontact);
        recorridoLayout = (LinearLayout) findViewById(R.id.user);
        boton = (Button) findViewById(R.id.boton);
        Firebase.setAndroidContext(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id) {
            case R.id.recorrido:
                bandaLayout.setVisibility(View.GONE);
                recorridoLayout.setVisibility(View.VISIBLE);
                cargarBanda.setBackgroundColor(Color.parseColor("#E35F76"));
                recorrido.setBackgroundColor(Color.parseColor("#FFFFFF"));
                boton.setText("DESCUBRE");
                break;

            case R.id.cargar_banda:
                bandaLayout.setVisibility(View.VISIBLE);
                recorridoLayout.setVisibility(View.GONE);
                cargarBanda.setBackgroundColor(Color.parseColor("#FFFFFF"));
                recorrido.setBackgroundColor(Color.parseColor("#E35F76"));
                boton.setText("CARGAR");
                break;
        }
    }
/*
    RadioGroup.OnCheckedChangeListener radioGroupOnCheckedChangeListener =
            new RadioGroup.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    LinearLayout llayout =  (LinearLayout)findViewById(R.id.bandcontact);
                    LinearLayout llayoutuser =  (LinearLayout)findViewById(R.id.user);
                    RadioButton checkedRadioButton = (RadioButton)radioGroup.findViewById(checkedId);
                    int checkedIndex = radioGroup.indexOfChild(checkedRadioButton);
                    if (checkedRadioButton.getText().toString().equals("CARGAR BANDA")){
                        llayoutuser.setVisibility(View.GONE);
                    }
                    else{
                        llayoutuser.setVisibility(View.VISIBLE);
                    }

                    if (checkedRadioButton.getText().toString().equals("MI RECORRIDO")){
                        llayout.setVisibility(View.GONE);
                    }
                    else{
                        llayout.setVisibility(View.VISIBLE );
                    }


                }};
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveData(){

        EditText name = (EditText) findViewById(R.id.name);
        EditText website = (EditText) findViewById(R.id.website);
        EditText address = (EditText) findViewById(R.id.address);

        if (!name.getText().toString().equals("") && !address.getText().toString().equals("") ) {
            try {
                mRef = new Firebase("https://flickering-inferno-8984.firebaseio.com");

                Firebase bandRef = mRef.child("newBands").child(name.getText().toString());
                Band newBand = new Band();
                newBand.setName(name.getText().toString());
                newBand.setWebsite(website.getText().toString());
                newBand.setAddress(address.getText().toString());

                bandRef.setValue(newBand);

                Toast.makeText(getApplicationContext(), "Guardado exioso", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {

            }

        }
    }

    /** Called when the user clicks the Send button */
    public void sendForm(View view) {

        saveData();
        Intent intent = new Intent(this, MapsActivity.class);
        EditText editText = (EditText) findViewById(R.id.interest);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}
