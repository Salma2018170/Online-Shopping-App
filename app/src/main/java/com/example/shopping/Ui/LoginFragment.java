package com.example.shopping.Ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.shopping.ForgetPassword;
import com.example.shopping.R;
import com.example.shopping.RecyclerViewActivity;
import com.example.shopping.storage.Constants;
import com.example.shopping.storage.Database;


public class LoginFragment extends Fragment implements View.OnClickListener {

    public LoginFragment() {
        // Required empty public constructor
    }

    private TextView email, password;
    private CheckBox Remember_Me;
    private TextView forget_Password;
    private Button login;
    SharedPreferences sharedPreferences;
    Database database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login2, container, false);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        Remember_Me = view.findViewById(R.id.remember_me);
        forget_Password = view.findViewById(R.id.forget_password);
        database=new Database(getActivity());
        login = view.findViewById(R.id.login);

        login.setOnClickListener(this);
        forget_Password.setOnClickListener(this);

        sharedPreferences=getActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.getBoolean(Constants.LoginTable.REMEMBER_ME, false)) {
            goToRecyclerViewActivity();
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login) {
            String _email = email.getText().toString();
            String _password = password.getText().toString();
            int id=database.checkMailAndPassword(_email,_password);
            if (_email.isEmpty() || _password.isEmpty()) {
                Toast.makeText(getContext(), "Fields are empty", Toast.LENGTH_LONG).show();

            }
            else {
                if(id!=0) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(Constants.LoginTable.ID,id);
                    editor.putString(Constants.LoginTable.EMAIL, _email);
                    editor.putBoolean(Constants.LoginTable.REMEMBER_ME, Remember_Me.isChecked());
                    editor.apply();
                    Toast.makeText(getContext(), "Login successfully", Toast.LENGTH_SHORT).show();
                    goToRecyclerViewActivity();
                }
                else
                {
                    Toast.makeText(getContext(), "The Email Address or Password you entered is incorrect", Toast.LENGTH_SHORT).show();

                }
            }
        }
        else if(view.getId()==R.id.forget_password)
        {
            goToForgetPassword();
        }
    }

    private void goToForgetPassword() {
        Intent intent =new Intent(getActivity(), ForgetPassword.class);
        startActivity(intent);
    }

    private void goToRecyclerViewActivity() {
        Intent intent = new Intent(getActivity(), RecyclerViewActivity.class);
          startActivity(intent);
      //  getActivity().finish();
    }

}
