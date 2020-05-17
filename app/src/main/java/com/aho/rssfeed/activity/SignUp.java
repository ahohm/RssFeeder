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
import com.aho.rssfeed.R;

public class SignUp extends AppCompatActivity {
    AppDatabase appDatabase;
    EditText etEmail,etUser,etPassword;
    Button bSignup;
    TextView tvemail, tvpass, tvuser, tvlogin;
    CheckBox cbShowPw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        appDatabase= AppDatabase.initDb(getApplicationContext());

        etEmail= findViewById(R.id.etEmail);
        etUser=findViewById(R.id.etUser);
        etPassword=findViewById(R.id.etPassword);
        tvemail= findViewById(R.id.tvemail);
        tvpass= findViewById(R.id.tvpass);
        tvuser= findViewById(R.id.tvuser);
        bSignup= findViewById(R.id.bSignup);
        tvlogin= findViewById(R.id.tvLogin);
        cbShowPw= findViewById(R.id.cbShowPw);

        bSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    insertDataMember();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(SignUp.this,"Empty Field", Toast.LENGTH_SHORT).show();
                }

            }
        });
        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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
    private void insertDataMember(){
        String email= etEmail.getText().toString();
        String user= etUser.getText().toString();
        String password= etPassword.getText().toString();

        DataMember member= new DataMember(email, user, password);

        appDatabase.dao().insertData(member);

        etEmail.setText("");
        etUser.setText("");
        etPassword.setText("");
    }
    public boolean validate(){
        boolean valid= false;
        String email= etEmail.getText().toString();
        String user= etUser.getText().toString();
        String password= etPassword.getText().toString();

        if (email.isEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            valid = false;
            tvemail.setError("Please enter valid email!");
        } else {
            valid = true;
            tvpass.setError(null);
        }

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
