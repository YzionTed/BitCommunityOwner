package com.BIT.fuxingwuye.activities.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.BIT.fuxingwuye.R;
import com.BIT.fuxingwuye.activities.MainTabActivity;
import com.BIT.fuxingwuye.activities.login.LoginActivity;
import com.BIT.fuxingwuye.activities.replenishData.ReplenishDataActivity;
import com.BIT.fuxingwuye.activities.residential_quarters.Housing;

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
                Intent intent=new Intent(RegisterSuccess.this, ReplenishDataActivity.class);
                intent.putExtra("gohousing",1);
                startActivity(intent);
            }
        });

    }
}
