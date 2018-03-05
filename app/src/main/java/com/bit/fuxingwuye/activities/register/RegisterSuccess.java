package com.bit.fuxingwuye.activities.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bit.fuxingwuye.R;
import com.bit.fuxingwuye.activities.MainTabActivity;
import com.bit.fuxingwuye.activities.login.LoginActivity;
import com.bit.fuxingwuye.activities.replenishData.ReplenishDataActivity;
import com.bit.fuxingwuye.activities.residential_quarters.Housing;

public class RegisterSuccess extends AppCompatActivity {
    Button goHousing,nowmain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);
        goHousing= (Button) findViewById(R.id.goHousing);
        nowmain= (Button) findViewById(R.id.nowmain);
        nowmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterSuccess.this, Housing.class);
                startActivity(intent);
                finish();

            }
        });
        goHousing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Housing.HOUSING_TAG = 1;
                Intent intent=new Intent(RegisterSuccess.this, Housing.class);
                startActivity(intent);
                finish();
//                Intent intent=new Intent(RegisterSuccess.this, ReplenishDataActivity.class);
//                intent.putExtra("gohousing",1);
//                startActivity(intent);
            }
        });

    }
}
