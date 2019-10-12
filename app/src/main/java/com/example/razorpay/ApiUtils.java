package com.example.razorpay;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "https://fast-tundra-18804.herokuapp.com/";

    public static ApiInterface getAPIService() {

        return ApiManager.getClient(BASE_URL).create(ApiInterface.class);
    }
}
