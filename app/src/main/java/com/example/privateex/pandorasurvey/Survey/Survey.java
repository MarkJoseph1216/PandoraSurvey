package com.example.privateex.pandorasurvey.Survey;

import android.app.Application;

public class Survey extends Application{
    public static String host = "http://192.168.100.103:9090";
    //public static String hostDAN = "http://demo-pandora-survey.luxxeserver.com";
    public static String hostDAN = "http://192.168.100.102/pandora";
    public static String hostARVS = "http://192.168.100.102/pandora";

    public static String url_branches = hostDAN + "/api/branches/branches.php";
    public static String url_test_imei_registered = hostDAN + "/api/imei/survey/registered.php";
    public static String imei_registered = hostDAN + "/api/imei/survey/imei_register.php";
    public static String url_survey = hostDAN + "/api/survey/create.php";

    public static String url_test_costumer_registered = host + "/api/surve//registered";
    public static String url_create_costumer = hostDAN + "/api/survey/customer/create.php";

    public static String url_check_costumer = hostDAN + "/api/survey/customer/check_customer.php";
    public static String url_creater_new_costumer = hostDAN + "/api/survey/customer/create_customer.php";

    public static String url_create_survey = hostDAN + "/api/survey/create.php";
    public static String sample_url = hostARVS + "/arvs/customer_create.php";

    public static String branchCode;
    public static String title = "";

    public static String Facebook = "false";
    public static String Instagram = "false";
    public static String Twitter = "false";
    public static String Snapchat = "false";
    public static String Bracelet = "false";
    public static String Charm = "false";
    public static String Necklace = "false";
    public static String Ring = "false";
    public static String Earrings = "false";
    public static String Others = "false";
    public static String Gifts = "false";
    public static String Newspaper = "false";
    public static String Magazine = "false";
    public static String SocialMedia = "false";
    public static String Billboard = "false";
    public static String FriendFamily = "false";
    public static String StoreVisit = "false";

//  public static String url_create_product = "http://192.168.100.100/arvs/create_newProd.php"
}
