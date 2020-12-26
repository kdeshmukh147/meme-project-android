package com.kamleshd.memescreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.ViewTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

     String[] curentValue = new String[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            load();
    }

    private void load ()
    {

        String urls;

        ImageView imageView= (ImageView) findViewById(R.id.imageViews);
//        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://meme-api.herokuapp.com/gimme";
        RequestManager k= Glide.with(this);

// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.


                        try {
                         String   urlsa = response.getString("url");

                         curentValue[0] =urlsa;
//                          k.load(urlsa).into(imageView);
                            Glide.with(MainActivity.this).load(urlsa).into(imageView);
                            Log.d("urlTag", "response is "+urlsa);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("urlTag", "response error "+e);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("urlTag", "something wrong");
            }
        });

// Add the request to the RequestQueue.
//        queue.add(jsonObjectRequest);
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }

    public void ShareMeme(View view) {
        Log.d("urlshare", "something "+curentValue[0]);

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, curentValue[0]);
        sendIntent.setType("text/plain");
// Create intent to show the chooser dialog
        Intent chooser = Intent.createChooser(sendIntent,"Open with");

// Verify the original intent will resolve to at least one activity
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    public void NextMeme(View view) {

        load();

    }
}