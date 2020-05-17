package com.aho.rssfeed.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aho.rssfeed.Database.AppDatabase;
import com.aho.rssfeed.Database.DataMember;
import com.aho.rssfeed.Database.DataMemberDAO;
import com.aho.rssfeed.R;

public class MainActivity extends AppCompatActivity {
    TextView tvSignup;
    EditText etUser, etPassword;
    Button bSignin;
    TextView tvuser, tvpass;
    CheckBox cbShowPw;

    AppDatabase appDatabase;
    DataMemberDAO memberDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDatabase= AppDatabase.initDb(getApplicationContext());
        etUser= findViewById(R.id.etUser);
        etPassword= findViewById(R.id.etPassword);
        bSignin= findViewById(R.id.bSignin);
        tvSignup= findViewById(R.id.tvSignup);
        tvuser= findViewById(R.id.tvuser);
        tvpass=findViewById(R.id.tvpass);
        cbShowPw= findViewById(R.id.cbShowPw);

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

        bSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    String user= etUser.getText().toString();
                    String pass= etPassword.getText().toString();
                    memberDAO= appDatabase.dao();
                    DataMember dataMember= memberDAO.getMember(user,pass);

                    if(dataMember!= null){
                        Intent intent= new Intent(MainActivity.this,LiveFeeds.class);
                        intent.putExtra("username", user);
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this,"Unregistered user, or incorrect", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Empty field", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cbShowPw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public boolean validate(){
        boolean valid= false;
        String user= etUser.getText().toString();
        String password= etPassword.getText().toString();


        if (user.isEmpty()) {
            valid = false;
            tvuser.setError("Please enter valid username!");
        } else {
            valid = true;
            tvuser.setError(null);
        }

        if (password.isEmpty()) {
            valid = false;
            tvpass.setError("Please enter valid password!");
        } else {
            valid = true;
            tvpass.setError(null);
        }

        return valid;
    }
}
