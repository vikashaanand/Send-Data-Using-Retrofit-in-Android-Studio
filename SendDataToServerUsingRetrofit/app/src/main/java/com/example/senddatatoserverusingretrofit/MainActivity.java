package com.example.senddatatoserverusingretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText edtName, edtRoll;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = findViewById(R.id.edtName);
        edtRoll = findViewById(R.id.edtRoll);
        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });


    }

    private void sendData() {

        String name = edtName.getText().toString().trim();
        String roll = edtRoll.getText().toString().trim();

        //Check That the EditTexts are not empty

        if(name.isEmpty()){

            edtName.setError("Name Required");
            edtName.requestFocus();
            return;
        }

        if(roll.isEmpty()){
            edtRoll.setError("Roll Required");
            edtRoll.requestFocus();
            return;
        }

        //Here we will write the code for sending the data to server

        Call<ResponseModel> call = RetrofitClient.getInstance().getAPI().sendData(name, Integer.parseInt(roll));

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                //This method will be called on successful server call

                ResponseModel obj = response.body();

                if(obj.isStatus()){
                    //What we want to do on successful insertion
                }else{
                    //What in case of some error
                }

                Toast.makeText(MainActivity.this, obj.getRemarks(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

                //This method will called in case of failure

                Toast.makeText(MainActivity.this, "Network Failed", Toast.LENGTH_SHORT).show();

            }
        });

    }
}