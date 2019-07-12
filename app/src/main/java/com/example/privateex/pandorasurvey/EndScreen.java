package com.example.privateex.pandorasurvey;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@SuppressWarnings("ALL")
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

        /**
         * @author John Patrick S. Papares
         * @since July 10, 2019 9:46PM PST
         * This is a direct instantiation and execution of SendJsonViaThread().execute()
         */
       // new SendJsonViaThread().execute(Survey.AnswerSurvey);
//        new SendJsonViaThreadProducts().execute(Survey.AnswerSurveyProducts);
//        new SendJsonViaThreadAds().execute(Survey.AnswerSurveyAds);

        /**
         * @author John Patrick S. Papares
         * @since July 10, 2019 9:46PM PST
         * I removed JSONSendingSurvey(); in line:75 because I add some parameters to this method.
         */
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

    /**
     * @author John Patrick S. Papares
     * @since July 10, 2019 9:46 PST
     * I add SendJsonViaThread class that extends an abstract class named AsyncTask
     * to perform a process asynchronously.
     */
//    @SuppressLint("StaticFieldLeak")
//    private class SendJsonViaThread extends AsyncTask<List<String>, Integer, String> {
//
//        @Override
//        protected String doInBackground(List<String>... arrayLists) {
//            for (String arrayContent : arrayLists[0]) {
//                JSONSendingSurvey(arrayContent);
//                // publishProgress(0);
//            }
//            return null;
//        }
////        @Override
////        protected void onCancelled() {
////            super.onCancelled();
////        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            Toast.makeText(EndScreen.this, "Successfully Upload!", Toast.LENGTH_SHORT).show();
//            JSONRequestEmail();
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Intent intent = new Intent(EndScreen.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }, 2000);
//        }
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    private class SendJsonViaThreadProducts extends AsyncTask<List<String>, Integer, String> {
//
//        @Override
//        protected String doInBackground(List<String>... arrayLists) {
//            for (String arrayContent : arrayLists[0]) {
//                JSONSendingSurveyProducts(arrayContent);
//            }
//            return null;
//        }
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//
//        }
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    private class SendJsonViaThreadAds extends AsyncTask<List<String>, Integer, String> {
//
//        @Override
//        protected String doInBackground(List<String>... arrayLists) {
//            for (String arrayContent : arrayLists[0]) {
//                JSONSendingSurveyAds(arrayContent);
//            }
//            return null;
//        }
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//
//        }
//    }
    private static final String TAG = "EndScreen";
    //Sending User's Survey Information
    /**
     * @author John Patrick S. Papares
     * I add some parameters to this method to make it reusable while looping some POST in other thread.
     * @param arrayContentToSend string from the List<String>
     */
    private void JSONSendingSurvey(){

        request = new StringRequest(Request.Method.POST, Survey.url_create_survey, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: " + response);
                //region unused block of codes
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
                //endregion
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
                params.put("social_media_id", Survey.AnswerSurvey.toString());  //I change the string value of this.
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

    private void JSONSendingSurveyProducts(final String arrayContentToSend){

        request = new StringRequest(Request.Method.POST, Survey.url_create_survey, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(EndScreen.this, response, Toast.LENGTH_SHORT).show();

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
                params.put("product_id", arrayContentToSend);
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

    private void JSONSendingSurveyAds(final String arrayContentToSend){

        request = new StringRequest(Request.Method.POST, Survey.url_create_survey, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(EndScreen.this, response, Toast.LENGTH_SHORT).show();

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
                params.put("ads_id", arrayContentToSend);
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
