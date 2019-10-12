package com.example.razorpay;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("order_id/")
    Call<Order_Id> getRegister(@Field("amount") int amount, @Field("currency") String currency, @Field("receipt") String receipt);

}
