package com.example.moez.insuranceandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AjoutReclamationActivity extends AppCompatActivity {

    private String Description;
    private String Subject;
    private EditText editDescription;
    private EditText editSubject;
    private Button addReclamation;
    String entered_dob ;

    SimpleDateFormat dateFormatter ;
    Date d ;

    String url = "http://10.0.2.2:18080/insurance-javaee-web/insurance/reclamation";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_reclamation);



        editDescription =  findViewById(R.id.description);
        editSubject =  findViewById(R.id.subject);
        addReclamation =  findViewById(R.id.ajoutReclamation) ;







        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.start();
        addReclamation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reclamation reclamation = new Reclamation();


                Description =editDescription.getText().toString();
                Subject =editSubject.getText().toString();
                reclamation.setDescription(Description);
                reclamation.setSubject(Subject);
                System.out.println("Description "+Description);
                System.out.println("subject "+Subject);
                sendPost(reclamation);

                Intent i = new Intent(AjoutReclamationActivity.this,ReclamationListActivity.class);
                startActivity(i);
            }
        });
    }
    public void sendPost(final Reclamation reclamation) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String urljson = "http://10.0.2.2:18080/insurance-javaee-web/insurance/reclamation";
                    URL url = new URL(urljson);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("description", reclamation.getDescription());
                    jsonParam.put("subject", reclamation.getSubject());




                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
