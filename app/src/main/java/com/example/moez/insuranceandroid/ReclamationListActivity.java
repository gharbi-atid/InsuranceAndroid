package com.example.moez.insuranceandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ReclamationListActivity extends AppCompatActivity {

    Button button;
    ListView lv;

    String url= "http://10.0.2.2:18080/insurance-javaee-web/insurance/reclamation/";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reclamation);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);

                            ArrayList<Reclamation> items = new ArrayList<>();
                            for (int i=0; i<array.length();i++){
                                JSONObject obj1 = array.getJSONObject(i);
                                Reclamation c = new Reclamation();
                                c.setSubject(obj1.getString("subject"));
                                c.setDescription(obj1.getString("description"));
                                items.add(c);
                            }

                            lv =  findViewById(R.id.lv_insurence);

                            ReclamationAdapter ad = new ReclamationAdapter(ReclamationListActivity.this, R.layout.activity_list_reclamation, items);

                            lv.setAdapter(ad);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!"+error);
            }
        }) ;

        RequestQueue queue = Volley.newRequestQueue(ReclamationListActivity.this);
        queue.add(stringRequest);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReclamationListActivity.this,AjoutReclamationActivity.class);
                startActivity(i);
            }
        });




    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

        }
        return true;
    }
}
