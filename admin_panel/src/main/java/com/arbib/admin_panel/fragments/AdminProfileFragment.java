package com.arbib.admin_panel.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arbib.admin_panel.MyDatabase;
import com.arbib.admin_panel.R;
import com.arbib.admin_panel.objects.Admin;

import java.util.ArrayList;
import java.util.Calendar;


public class AdminProfileFragment extends Fragment implements View.OnClickListener{

    private View view;
    private static final String TAG = "AdminProfile";
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private EditText edt_firstname, edt_lastname, edt_email, edt_phone,edt_role, edt_birthday;
    private Spinner edt_state;
    private TextView txt_fullname, txt_role;

    private ImageView icon_date_picker, icon_state;
    private Button btn_save_personal_info, btn_reset, btn_save_role, btn_save_state;
    private MyDatabase database;
    private Admin current_admin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_profile, container, false);
        inflate();
        showDataInSpinner();

        Bundle bundle = this.getArguments();

        current_admin = null;
        if(bundle != null){
            current_admin = bundle.getParcelable("key");
            showData(current_admin);

        }

        database = new MyDatabase(getActivity());

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                edt_birthday.setText(day+" / "+month+" / "+year);
            }
        };



        return view;
    }

    private void showData(Admin admin){
        edt_firstname.setText(admin.getFirstname());
        edt_lastname.setText(admin.getLastname());
        edt_email.setText(admin.getEmail());
        edt_phone.setText(admin.getPhone());
        edt_birthday.setText(admin.getBirthday());
        edt_role.setText(admin.getRole());
        txt_fullname.setText(admin.getLastname() +" "+admin.getFirstname());
        txt_role.setText(admin.getRole());
        int index = admin.getState().equals("approved") ? 0 : 1;
        edt_state.setSelection(index);
        if(!admin.getState().equals("approved")){
            icon_state.setVisibility(View.GONE);
        }
    }

    private void inflate(){
        edt_birthday = view.findViewById(R.id.edt_birthday);
        icon_date_picker = view.findViewById(R.id.icon_date_picker);
        edt_firstname = view.findViewById(R.id.edt_firstname);
        edt_lastname = view.findViewById(R.id.edt_lastname);
        edt_email = view.findViewById(R.id.edt_email);
        edt_phone = view.findViewById(R.id.edt_phone);
        edt_role = view.findViewById(R.id.edt_role);
        edt_state = view.findViewById(R.id.edt_state);
        txt_fullname = view.findViewById(R.id.fullname);
        txt_role = view.findViewById(R.id.role);
        btn_save_personal_info = view.findViewById(R.id.btn_save_personalInfo);
        icon_state = view.findViewById(R.id.state);
        btn_reset = view.findViewById(R.id.btn_reset);
        btn_save_role = view.findViewById(R.id.btn_save_role);
        btn_save_state = view.findViewById(R.id.btn_save_state);

        icon_date_picker.setOnClickListener(AdminProfileFragment.this);
        btn_save_personal_info.setOnClickListener(AdminProfileFragment.this);
        btn_reset.setOnClickListener(AdminProfileFragment.this);
        btn_save_role.setOnClickListener(AdminProfileFragment.this);
        btn_save_state.setOnClickListener(AdminProfileFragment.this);
    }

    private void showDataInSpinner(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("approved");
        arrayList.add("not approved");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, arrayList);
        edt_state.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.icon_date_picker:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Dialog_NoActionBar,
                        onDateSetListener,
                        year, month, day
                );

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


                break;
            case R.id.btn_save_personalInfo:
                if(personalInfo_isEmpty()){
                    Toast.makeText(getActivity(), "fill all fields", Toast.LENGTH_SHORT).show();
                }else {
                    Admin admin = getPersonalInfo();
                    database.adminUpdate(admin);
                    showData(admin);
                    Toast.makeText(getActivity(), "updated successfully", Toast.LENGTH_SHORT).show();


                }
            case R.id.btn_reset:
                showData(current_admin);
                break;

            case R.id.btn_save_role:
                if(role_isEmpty()){
                    Toast.makeText(getActivity(), "role is empty", Toast.LENGTH_SHORT).show();
                }

        }
    }


    /**
     *
     * @return true if role editText is empty
     */
    private Boolean role_isEmpty(){
        return edt_role.getText().toString().equals("");
    }





    /**
     *
     * @return Admin object represent the input data
     */
    private Admin getPersonalInfo(){

        return new Admin(current_admin.getId(),
                edt_firstname.getText().toString(),
                edt_lastname.getText().toString(),
                edt_email.getText().toString(),
                edt_birthday.getText().toString(),
                edt_role.getText().toString(),
                edt_phone.getText().toString(),
                edt_state.getSelectedItem().toString()
                );
    }


    /**
     *
     * @return true if any field is empty in personal info
     */
    private Boolean personalInfo_isEmpty(){
        return  edt_firstname.getText().toString().equals("") ||
                edt_lastname.getText().toString().equals("") ||
                edt_email.getText().toString().equals("") ||
                edt_birthday.getText().toString().equals("") ||
                edt_role.getText().toString().equals("") ||
                edt_phone.getText().toString().equals("");
    }
}