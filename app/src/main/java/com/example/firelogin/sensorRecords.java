package com.example.firelogin;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class sensorRecords{

    public static final String QUERY_GET_RECORDS = "http://192.168.0.24:80/records";


    Context context;
    JSONArray records = new JSONArray();

    public sensorRecords(Context context) {this.context = context;}

    public interface VolleyResponseListener{
        void onError(String message);
        void onResponseRecords(JSONArray response);
    }


    public void getRecords(VolleyResponseListener volleyResponseListener){

        // Using standard request
        // Instantiate the RequestQueue.
        String url = QUERY_GET_RECORDS;

        // Setting up for request JSON OBJECT
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    records = response.getJSONArray("todayTempHumid");
                } catch (JSONException e) {
                    Toast.makeText(context, "ERROR: " + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                volleyResponseListener.onResponseRecords(records);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError(error.toString());
            }
        });

        // Add the request to the RequestQueue.
        MySingleton.getInstance(context).addToRequestQueue(request);

    }



}
