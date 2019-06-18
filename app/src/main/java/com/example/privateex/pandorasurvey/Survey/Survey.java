package com.example.privateex.pandorasurvey.Survey;

import android.app.Application;

public class Survey extends Application{
    public static String host = "192.168.100.102:8000";
    public static String url_branches = host + "/api/survey/branches";

    public static String url_test_imei_registered = host + "/api/survey/imei/registered";

    public static String url_test_costumer_registered = host + "/api/survey/customer/registered";

    public static String url_create_costumer = host + "/api/survey/customer/create";

    public static String url_create_survey = host + "/api/survey/create";


//    public static String url_create_product = "http://192.168.100.100/arvs/create_newProd.php";

   // public static int  TAG_SUCCESS = "success";

}
