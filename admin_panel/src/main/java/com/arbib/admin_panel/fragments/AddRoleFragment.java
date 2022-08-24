package com.arbib.admin_panel.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arbib.admin_panel.MyDatabase;
import com.arbib.admin_panel.R;
import com.arbib.admin_panel.interfaces.ResponseListener;
import com.arbib.admin_panel.interfaces.operations.OperationsReceiverListener;
import com.arbib.admin_panel.interfaces.role.FindRoleListener;
import com.arbib.admin_panel.objects.Operation;
import com.arbib.admin_panel.objects.Role;

import java.util.ArrayList;
import java.util.HashMap;


public class AddRoleFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView edt_select_operations, edt_name, edt_description;
    private MyDatabase database;
    private HashMap<String, Operation> operationHashMap;
    private ArrayList<Operation> selected_operations;
    private boolean is_selected = false;
    private Button btn_add_role;
    private ImageView icon_back;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_role, container, false);
        inflate();



        database = new MyDatabase(getActivity());
        operationHashMap = new HashMap<>();
        selected_operations = new ArrayList<>();


        database.getAllOperations(new OperationsReceiverListener() {
            @Override
            public void onReceive(ArrayList<Operation> operations) {
                boolean[] checkItem = new boolean[operations.size()];

                fillOperationsMap(operations);
                customSpinner(getOperationLibelle(operations), checkItem);
            }
        });


        return view;
    }

    private void inflate() {
        edt_select_operations = view.findViewById(R.id.edt_operations_result);
        edt_name = view.findViewById(R.id.edt_name);
        edt_description = view.findViewById(R.id.edt_desc);
        btn_add_role = view.findViewById(R.id.btn_add_role);
        icon_back = view.findViewById(R.id.icon_back);
        btn_add_role.setOnClickListener(AddRoleFragment.this);
        icon_back.setOnClickListener(AddRoleFragment.this);
    }


    private String[] getOperationLibelle(ArrayList<Operation> operations) {
        String[] list = new String[operations.size()];
        for (int i = 0; i < operations.size(); i++) {
            list[i] = operations.get(i).getLibelle();
        }
        return list;
    }

    private void fillOperationsMap(ArrayList<Operation> operations) {
        for (Operation op : operations) {
            operationHashMap.put(op.getLibelle(), op);
        }
    }

    private ArrayList<Operation> selectedOperations(String[] libelles){
        ArrayList<Operation> operations = new ArrayList<>();
        for(String str : libelles){
            operations.add(operationHashMap.get(str));
        }
        return operations;
    }


    private void customSpinner(String[] list, boolean[] checkedItems) {
        edt_select_operations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                mBuilder.setTitle("select operations");
                ArrayList<Integer> mUserItems = new ArrayList<>(); //hold selected item position
                mBuilder.setMultiChoiceItems(list, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked) {
                            mUserItems.add(position);
                        } else {
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String[] selectedItems = new String[mUserItems.size()];
                        for(int i=0; i< mUserItems.size(); i++){
                            selectedItems[i] = list[mUserItems.get(i)];
                        }
                        selected_operations = selectedOperations(selectedItems);
                        for (int i=0 ; i< selected_operations.size() ; i++){
                            if(i==0){
                                edt_select_operations.setText(selected_operations.get(i).getLibelle());
                            }else {
                                edt_select_operations.setText(edt_select_operations.getText().toString()+" + "+selected_operations.get(i).getLibelle());
                            }
                        }
                        is_selected = true;
                    }
                });

                mBuilder.setNegativeButton("dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton("clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            edt_select_operations.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_role:
                if(is_selected && !isEmpty()){
                    String name = edt_name.getText().toString();
                    ArrayList<String> arrayList = new ArrayList<>();
                    for(Operation op : selected_operations){
                        arrayList.add(op.getLibelle());
                    }
                    database.roleExist(arrayList, name, new FindRoleListener() {
                        @Override
                        public void OnFound(Boolean exist) {
                            if(exist){
                                Toast.makeText(getActivity(), "this role is exist", Toast.LENGTH_SHORT).show();
                            }else {
                                String desc = edt_description.getText().toString();
                                Role role = new Role(name, desc, selected_operations.size(), selected_operations);
                                database.insertRole(role);
                                Toast.makeText(getActivity(), "role added succesfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });                 
                }else {
                    Toast.makeText(getActivity(), "fill all fields", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.icon_back:
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new RolesFragment()).commit();
                break;
        }
    }
    
    private Boolean isEmpty(){
        return edt_name.getText().toString().equals("") || edt_description.getText().toString().equals("");
    }
}