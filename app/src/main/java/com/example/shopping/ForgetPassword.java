package com.example.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopping.Ui.LoginFragment;
import com.example.shopping.storage.Database;

public class ForgetPassword extends AppCompatActivity implements View.OnClickListener {
    private EditText email,newPassword,confirmPassword;
    private Button confirm;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        email=findViewById(R.id.check_email);
        newPassword=findViewById(R.id.newPassword);
        confirmPassword=findViewById(R.id.confirmpassword);
        confirm=findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
        database=new Database(this);
    }

    @Override
    public void onClick(View view) {
        String _email = email.getText().toString();
        String _password = newPassword.getText().toString();
        String _passwordconfirm = confirmPassword.getText().toString();

        boolean checkEmail = database.checkMail(_email);
        if(_email.isEmpty()||_password.isEmpty()||_passwordconfirm.isEmpty())
        {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_LONG).show();

        }
        else
        {
            if (checkEmail==true){

                if(_password.equals(_passwordconfirm))
                {
                    database.updatePassword(_email,_password);
                    Intent intent = new Intent(this, LoginFragment.class);
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(this, "Password do not match", Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(this, "Sorry Wrong Email ", Toast.LENGTH_LONG).show();
            }
        }
    }
}
