package com.shoutout.android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {

    private TextView Login_text;
    private TextInputLayout Email_Edit_Text,Password_Edit_Text,Full_Name_Edit_Text;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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
    }

    private void SendUserToLoginActivity() {
        Intent SignUp_intent = new Intent(SignUpActivity.this,LoginActivity.class);
        SignUp_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(SignUp_intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setEditTexts() {

        //Initialize both edit text
        Email_Edit_Text = (TextInputLayout) findViewById(R.id.Sign_up_Email_Edit_text_input_layout);
        Password_Edit_Text = (TextInputLayout) findViewById(R.id.Sign_up_password__edit_text_input_layout);
        Full_Name_Edit_Text = (TextInputLayout) findViewById(R.id.Sign_up_FullName_Edit_text_input_layout);


        //Customize your Email and Password Edit text Here
        Email_Edit_Text.setBoxStrokeColor(getResources().getColor(R.color.soft_pink));
        Email_Edit_Text.setHintTextColor(getColorStateList(R.color.soft_pink));
        Email_Edit_Text.setBoxCornerRadii(20,20,20,20);

        Password_Edit_Text.setBoxStrokeColor(getResources().getColor(R.color.soft_pink));
        Password_Edit_Text.setHintTextColor(getColorStateList(R.color.soft_pink));
        Password_Edit_Text.setBoxCornerRadii(20,20,20,20);

        Full_Name_Edit_Text.setBoxStrokeColor(getResources().getColor(R.color.soft_pink));
        Full_Name_Edit_Text.setHintTextColor(getColorStateList(R.color.soft_pink));
        Full_Name_Edit_Text.setBoxCornerRadii(20,20,20,20);

        //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    }

    private String getColoredSpanned(String text, String color) {
        //Here input is in Html format so, if you want to customize this just do it
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

}