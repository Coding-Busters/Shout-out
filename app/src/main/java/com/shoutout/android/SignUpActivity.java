package com.shoutout.android;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private TextView Login_text;
    private TextInputLayout Email_Edit_Text_layout,Password_Edit_Text_layout,Full_Name_Edit_Text_layout;
    private FirebaseAuth mAuth;
    private Button SignUpButton;
    private DatabaseReference UsersRef;
    private String CurrentUserId;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        SignUpButton = findViewById(R.id.Sign_up_button);

        setEditTexts();
        Login_text = findViewById(R.id.Sign_up_text_log_in);

        String str = getColoredSpanned("I'm already a member,", "#272526");
        String sign_up_text = getColoredSpanned("Sign In","#FF4D83");
        Login_text.setText(Html.fromHtml(str+sign_up_text));
        Login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToLoginActivity();
            }
        });

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });
    }

    private void CreateNewAccount() {
    final String fullname = Full_Name_Edit_Text_layout.getEditText().getText().toString();
    final String email = Email_Edit_Text_layout.getEditText().getText().toString();
    final String password = Password_Edit_Text_layout.getEditText().getText().toString();

    try {
        if(TextUtils.isEmpty(fullname)){
            Toast.makeText(this, "Please fill all the requirements", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please fill all the requirements", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please fill all the requirements", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password) && TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please fill all the requirements", Toast.LENGTH_SHORT).show();
        }
        else if(!TextUtils.isEmpty(password) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    HashMap UserMap = new HashMap();
                    UserMap.put("FullName",fullname);
                    UserMap.put("Password",password);

                    CurrentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    UsersRef.child(CurrentUserId).updateChildren(UserMap);
                    SendEmailVerificationMessage();
                }
                }
            });

        }else {
            Toast.makeText(this, "something gone wrong", Toast.LENGTH_LONG).show();
        }
    }catch (Exception e){

    }
    }

    private void SendEmailVerificationMessage() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                    SendUserToLoginActivity();
                    Toast.makeText(SignUpActivity.this, "Registration Successfully, we've sent you a email. Please check to verify your account", Toast.LENGTH_SHORT).show();
                    mAuth.signOut();
                    }else {
                        String Message = task.getException().toString();
                        mAuth.signOut();
                        Toast.makeText(SignUpActivity.this, "Error"+Message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void SendUserToLoginActivity() {
        Intent SignUp_intent = new Intent(SignUpActivity.this,LoginActivity.class);
        SignUp_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(SignUp_intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setEditTexts() {

        //Initialize both edit text
        Email_Edit_Text_layout = (TextInputLayout) findViewById(R.id.Sign_up_Email_Edit_text_input_layout);
        Password_Edit_Text_layout = (TextInputLayout) findViewById(R.id.Sign_up_password__edit_text_input_layout);
        Full_Name_Edit_Text_layout = (TextInputLayout) findViewById(R.id.Sign_up_FullName_Edit_text_input_layout);


        //Customize your Email and Password Edit text Here
        Email_Edit_Text_layout.setBoxStrokeColor(getResources().getColor(R.color.soft_pink));
        Email_Edit_Text_layout.setHintTextColor(getColorStateList(R.color.soft_pink));
        Email_Edit_Text_layout.setBoxCornerRadii(20,20,20,20);

        Password_Edit_Text_layout.setBoxStrokeColor(getResources().getColor(R.color.soft_pink));
        Password_Edit_Text_layout.setHintTextColor(getColorStateList(R.color.soft_pink));
        Password_Edit_Text_layout.setBoxCornerRadii(20,20,20,20);

        Full_Name_Edit_Text_layout.setBoxStrokeColor(getResources().getColor(R.color.soft_pink));
        Full_Name_Edit_Text_layout.setHintTextColor(getColorStateList(R.color.soft_pink));
        Full_Name_Edit_Text_layout.setBoxCornerRadii(20,20,20,20);

        //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    }

    private String getColoredSpanned(String text, String color) {
        //Here input is in Html format so, if you want to customize this just do it
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

}