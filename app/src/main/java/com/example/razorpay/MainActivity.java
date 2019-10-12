package com.example.razorpay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    String TAG = "Payment Error";
    Button pay;
    private ApiInterface mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAPIService = ApiUtils.getAPIService();
        Checkout.preload(getApplicationContext());

        pay = (Button) findViewById(R.id.razor_pay);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });
    }


    public void startPayment() {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();

        /**
         * Set your logo here
         */
//        checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            Call<Order_Id> calldash = mAPIService.getRegister(1, "INR", "order_rcptid_11");
            calldash.enqueue(new Callback<Order_Id>() {
                @Override
                public void onResponse(Call<Order_Id> call, Response<Order_Id> response) {
                    if(response.isSuccessful()) {
                        if(response.body() == null){
                            Toast.makeText(MainActivity.this, "Null response from server", Toast.LENGTH_LONG).show();

                        }
                        else {
                            Toast.makeText(MainActivity.this, "Order_Id "+response.body().getOrder_id(), Toast.LENGTH_LONG).show();

                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Response from server is not successful", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Order_Id> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failure "+t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });


            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            options.put("name", "Techrits");

            /**
             * Description can be anything
             * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
             *     Invoice Payment
             *     etc.
             */
            options.put("description", "Test Order");
//            options.put("order_id", "order_9A33XWu170gUtm");
            options.put("currency", "INR");

            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            options.put("amount", "100");

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failure : "+s, Toast.LENGTH_LONG).show();
    }
}
