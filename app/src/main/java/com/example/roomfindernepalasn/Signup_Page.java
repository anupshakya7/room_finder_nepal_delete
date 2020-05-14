package com.example.roomfindernepalasn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_Page extends AppCompatActivity {

    EditText txtFullName,txtUserName,txtEmail,txtPassword,txtConfirmPassword;
    RadioButton Male,Female;
    Button btnRegister;
    ProgressBar pb;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;

    String gender="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__page);

        txtFullName=(EditText) findViewById(R.id.fullName);
        txtUserName=(EditText) findViewById(R.id.userName);
        txtEmail=(EditText) findViewById(R.id.emailAddress);
        txtPassword=(EditText) findViewById(R.id.password);
        txtConfirmPassword=(EditText) findViewById(R.id.confirmPassword);
        Male=(RadioButton)findViewById(R.id.male);
        Female=(RadioButton)findViewById(R.id.female);
        btnRegister=(Button)findViewById(R.id.btnRegister);
        pb=(ProgressBar)findViewById(R.id.progreeBar);

        databaseReference = firebaseDatabase.getInstance().getReference("Customer");
        firebaseAuth=FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullName=txtFullName.getText().toString();
                final String userName=txtUserName.getText().toString();
                final String email=txtEmail.getText().toString();
                String password=txtPassword.getText().toString();
                String confirmPassword=txtConfirmPassword.getText().toString();


                if(Male.isChecked()){
                    gender="Male";
                }
                if(Female.isChecked()){
                    gender="Female";
                }

                if(TextUtils.isEmpty(fullName)){
                    Toast.makeText(Signup_Page.this, "Enter Your Full Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(Signup_Page.this, "Enter Your User Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Signup_Page.this, "Enter Your Email Address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Signup_Page.this, "Enter Your Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(confirmPassword)){
                    Toast.makeText(Signup_Page.this, "Enter Your Confirm Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length()<8){
                    Toast.makeText(Signup_Page.this,"Password too Short", Toast.LENGTH_SHORT).show();
                    return;
                }



                if(password.equals(confirmPassword)) {

                    Toast.makeText(Signup_Page.this, "Password Match", Toast.LENGTH_SHORT).show();

                    pb.setVisibility(View.VISIBLE);

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Signup_Page.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    pb.setVisibility(View.GONE);

                                    if (task.isSuccessful()) {
                                        Customer information = new Customer(
                                                fullName,
                                                userName,
                                                email,
                                                gender

                                        );

                                        FirebaseDatabase.getInstance().getReference("Customer")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                Toast.makeText(Signup_Page.this, "Registration Completed", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), Login_Page.class));
                                                finish();
                                            }
                                        });

                                    } else {
                                        Toast.makeText(Signup_Page.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }else{
                    Toast.makeText(Signup_Page.this,"Password Not Match",Toast.LENGTH_SHORT).show();
                    return;

                }

            }
        });
    }
}
