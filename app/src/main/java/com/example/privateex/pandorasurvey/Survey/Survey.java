package com.example.privateex.pandorasurvey.Survey;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class Survey extends Application {

    public static String host = "http://192.168.100.103:9090";
    //public static String hostDAN = "http://demo-pandora-survey.luxxeserver.com";
    public static String hostDAN = "http://192.168.100.108/pandora";

    public static String url_branches = hostDAN + "/api/branches/branches.php";
    public static String url_test_imei_registered = hostDAN + "/api/imei/survey/registered.php";
    public static String imei_registered = hostDAN + "/api/imei/survey/imei_register.php";

    public static String url_creater_new_costumer = hostDAN + "/api/survey/customer/create.php";
    public static String url_create_survey = hostDAN + "/api/survey/create.php";
    public static String url_request_email = hostDAN + "/api/survey/send_email.php";
    public static String url_validate_number = hostDAN + "/api/survey/customer/check_mobile.php";
    public static String url_check_profile = hostDAN + "/api/survey/customer/check_profile.php";
    public static String url_fetch_categories = hostDAN + "/api/survey/fetch_categories.php";
    public static String url_update_profile = hostDAN + "/api/survey/customer/update_profile.php";

    public static String branchCode;
    public static String title = "";
    public static String ID = "";
    public static String countryCode = "";
    public static String branchName = "";

    //Survey Question
    public static String Facebook = "1";
    public static String Instagram = "1";
    public static String Twitter = "1";
    public static String Snapchat = "1";
    public static String Bracelet = "false";
    public static String Charm = "false";
    public static String Necklace = "false";
    public static String Ring = "false";
    public static String Earrings = "false";
    public static String Others = "";
    public static String Gifts = "";
    public static String Newspaper = "false";
    public static String Magazine = "false";
    public static String SocialMedia = "false";
    public static String Billboard = "false";
    public static String FriendFamily = "false";
    public static String StoreVisit = "false";

    public static List<String> AnswerSurvey = new ArrayList<String>();
    public static List<String> AnswerSurveyProducts = new ArrayList<String>();
    public static List<String> AnswerSurveyAds = new ArrayList<String>();

   //Validation updates of data
   public static boolean update;
}
