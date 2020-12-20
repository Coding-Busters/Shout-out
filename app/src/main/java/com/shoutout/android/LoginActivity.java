package com.shoutout.android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private TextView Signup_text;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setEditTexts();
        Signup_text = findViewById(R.id.login_text_sign_up);

        String str = getColoredSpanned("I'm a new user.", "#272526");
        String sign_up_text = getColoredSpanned("Sign Up","#FF4D83");
        Signup_text.setText(Html.fromHtml(str+sign_up_text));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setEditTexts() {

        //Initialize both edit text
        final TextInputLayout Email_Edit_Text = (TextInputLayout) findViewById(R.id.login_Email_Edit_text_input_layout);
        final TextInputLayout Password_Edit_Text = (TextInputLayout) findViewById(R.id.login_password__edit_text_input_layout);

        //Customize your Email and Password Edit text Here
        Email_Edit_Text.setBoxStrokeColor(getResources().getColor(R.color.soft_pink));
        Email_Edit_Text.setHintTextColor(getColorStateList(R.color.soft_pink));
        Email_Edit_Text.setBoxCornerRadii(20,20,20,20);

        Password_Edit_Text.setBoxStrokeColor(getResources().getColor(R.color.soft_pink));
        Password_Edit_Text.setHintTextColor(getColorStateList(R.color.soft_pink));
        Password_Edit_Text.setBoxCornerRadii(20,20,20,20);

        //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    }

    private String getColoredSpanned(String text, String color) {
        //Here input is in Html format so, if you want to customize this just do it
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

}