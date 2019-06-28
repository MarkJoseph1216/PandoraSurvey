package com.example.privateex.pandorasurvey;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;
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

        JSONSendingSurvey();
//        Thread timer = new Thread(){
//            public void run(){
//                try {
//                    sleep(2000);
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//                finally {
//                }
//            }
//        }; timer.start();
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
                params.put("facebook", Survey.Facebook);
                params.put("instagram", Survey.Instagram);
                params.put("twitter", Survey.Twitter);
                params.put("snapchat", Survey.Snapchat);
                params.put("others", Survey.Others);
                params.put("bracelet", Survey.Bracelet);
                params.put("charm", Survey.Charm);
                params.put("necklace", Survey.Necklace);
                params.put("ring", Survey.Ring);
                params.put("earrings", Survey.Earrings);
                params.put("gifts", Survey.Gifts);
                params.put("newspaper", Survey.Newspaper);
                params.put("magazine", Survey.Magazine);
                params.put("social_media", Survey.SocialMedia);
                params.put("billboard", Survey.Billboard);
                params.put("friend_family", Survey.FriendFamily);
                params.put("store_visit", Survey.StoreVisit);

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

                params.put("cust_id", Survey.ID);

                return params;
            }
        };
        requestQueue.add(request);
    }
}
