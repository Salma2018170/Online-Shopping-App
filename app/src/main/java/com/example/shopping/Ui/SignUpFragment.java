package com.example.shopping.Ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.shopping.R;
import com.example.shopping.RecyclerViewActivity;
import com.example.shopping.storage.SignUp;
import com.example.shopping.storage.Database;

import java.util.Calendar;


public class SignUpFragment extends Fragment implements View.OnClickListener {

    private Calendar cal;
    //Calender
    private static final String TAG ="MainActivity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDataSetListener;

    public EditText name, password, email;
    private Button signUp;
    private String Name;
    private String Password;
    private String Email;
    private SignUp sign;
    private Database signUpDatabase;
    int year;
    int month;
    int day;

    public SignUpFragment() {
        // Required empty public constructor
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        signUpDatabase=new Database(getActivity());
        linkXmlWithJava(view);

        mDisplayDate = view.findViewById(R.id.BirthdayDate);
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        mDisplayDate.setOnClickListener(this);

        mDataSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG,"onDateSet: mm/dd/yyy: " + year + "/" + month + "/" + day);
                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        return view;
    }

    static int count=0;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signup:
                getSignUpData();
                if (!Name.equals("") && !Password.equals("") && !Email.equals("")) {
                    sign = new SignUp(Name, Password, Email);
                    signUpDatabase.createAcount(sign);
                    count++;
                    goToSignUpsData();
                }
                break;
            case R.id.BirthdayDate:
                DatePickerDialog dialog = new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDataSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;
        }
    }

    private void goToSignUpsData() {
        Intent intent = new Intent(getActivity(), RecyclerViewActivity.class);
        intent.putExtra("key",1);
        intent.putExtra("Id",count);
        startActivity(intent);
        getActivity().finish();
    }

    private void getSignUpData() {
        Name = name.getText().toString();
        Password = password.getText().toString();
        Email = email.getText().toString();
    }

    private void linkXmlWithJava(View view) {
        name = view.findViewById(R.id.entername);
        password = view.findViewById(R.id.enterpassword);
        email = view.findViewById(R.id.enteremail);
        signUp = view.findViewById(R.id.signup);
        signUp.setOnClickListener(this);
    }

}