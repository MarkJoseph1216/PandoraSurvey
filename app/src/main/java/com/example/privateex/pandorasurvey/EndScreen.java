package com.example.privateex.pandorasurvey;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.privateex.pandorasurvey.Survey.Survey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EndScreen extends AppCompatActivity {

    ImageView imgPandora;
    TextView txtThankyou;
    private RequestQueue requestQueue;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_end_screen);

        imgPandora = (ImageView) findViewById(R.id.imgPandora);
        txtThankyou = (TextView) findViewById(R.id.txtThankyou);
        imgPandora.startAnimation(AnimationUtils.loadAnimation(EndScreen.this, R.anim.random));
        final Animation myAnim = AnimationUtils.loadAnimation(EndScreen.this, R.anim.bounce);
        txtThankyou.startAnimation(myAnim);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        txtThankyou.startAnimation(myAnim);

        requestQueue = Volley.newRequestQueue(EndScreen.this);

//
//        Survey.AnswerSurvey.add("1");
//        Survey.AnswerSurvey.add("2");
//        Survey.AnswerSurvey.add("3");

        JSONSendingSurvey();
    }

    //Disable Back Pressed
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //Sending User's Survey Information
    private void JSONSendingSurvey(){
        request = new StringRequest(Request.Method.POST, Survey.url_create_survey, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");
                                if (message.equals("success")) {
                                    JSONRequestEmail();
                                    Toast.makeText(EndScreen.this, "Successfully Upload!", Toast.LENGTH_SHORT).show();

                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(EndScreen.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, 2000);
                                }
                                else {
                                    Toast.makeText(EndScreen.this, "" + message, Toast.LENGTH_SHORT).show();
                                }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("cust_id", Survey.ID);
                params.put("social_media_id", Survey.AnswerSurvey.toString());
                params.put("others", Survey.Others);
                params.put("product_id", Survey.AnswerSurveyProducts.toString());
                params.put("buying_for_others", Survey.Gifts);
                params.put("ads_id", Survey.AnswerSurveyAds.toString());
//                JSONObject jsonObject = null;
//                JSONArray jsonArray=new JSONArray();
//                for (int i = 0; i < AnswerSurvey.size(); i++) {
//                    jsonObject = new JSONObject();
//                    try {
//
//                        jsonObject.put("truckType",AnswerSurvey.get(i).toString());
//
//                        jsonArray.put(jsonObject);
//
//                        Log.d("Error", jsonObject.toString());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                JSONArray list = new JSONArray();
//                for(int i =0; i<AnswerSurvey.size();i++) {
//                    JSONObject jrr = new JSONObject();
//                    jrr.put();
//                    list.put(jrr);
//
               // }
             //   Log.d("JsonArray: ", list.toString());
//                String jsonArrayString = list.toString();
//                params.put("social_media_id", "1");
////                params.put("social_media_id", "2");
                return params;
            }
        };
        requestQueue.add(request);
    }

    //Sending Email to Customers
    private void JSONRequestEmail(){
        request = new StringRequest(Request.Method.POST, Survey.url_request_email, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if (message.equals("success")) {
                    }
                    else {
                        Toast.makeText(EndScreen.this, "" + message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", Survey.ID);

                return params;
            }
        };
        requestQueue.add(request);
    }
}
