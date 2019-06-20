package com.example.privateex.pandorasurvey.Survey;

import android.app.Application;

public class Survey extends Application{
    public static String host = "http://192.168.100.103:9090";
    public static String hostDAN = "http://192.168.100.102/pandora";

    public static String url_branches = hostDAN + "/api/branches/branches.php";
    public static String url_test_imei_registered = hostDAN + "/api/imei/survey/registered.php";
    public static String imei_registered = hostDAN + "/api/imei/survey/imei_register.php";

    public static String url_test_costumer_registered = host + "/api/surve//registered";
    public static String url_create_costumer = hostDAN + "/api/survey/customer/create.php";

    public static String url_check_costumer = hostDAN + "/api/survey/customer/check_customer.php";

    public static String url_creater_new_costumer = hostDAN + "/api/survey/customer/create_customer.php";

    public static String url_create_survey = host + "/api/survey/create";

    public static String branchCode;

//  public static String url_create_product = "http://192.168.100.100/arvs/create_newProd.php"
}
