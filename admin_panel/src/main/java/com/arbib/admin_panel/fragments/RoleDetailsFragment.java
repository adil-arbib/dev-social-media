package com.arbib.admin_panel.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.arbib.admin_panel.adapters.OperationAdapter;
import com.arbib.admin_panel.interfaces.ComplicatedItemOnClickListener;
import com.arbib.admin_panel.interfaces.ItemOnClickListener;
import com.arbib.admin_panel.interfaces.ResponseListener;
import com.arbib.admin_panel.interfaces.operations.OperationsReceiverListener;
import com.arbib.admin_panel.interfaces.role.FindRoleListener;
import com.arbib.admin_panel.interfaces.role.RoleDateListener;
import com.arbib.admin_panel.objects.Operation;
import com.arbib.admin_panel.objects.Role;

import java.util.ArrayList;
import java.util.HashMap;


public class RoleDetailsFragment extends Fragment implements View.OnClickListener, ComplicatedItemOnClickListener {
    private View view;
    private Role current_role, new_role;
    private ConstraintLayout const_role, const_operation;
    private View line_role, line_operation;
    private TextView lbl_role, lbl_operation, txt_create_at, txt_update_at , lbl_role_name;
    private ImageView icon_back, icon_delete_role;
    private Button btn_save_role, btn_reset_role, btn_add_operation;
    private EditText edt_role_name, edt_role_description;
    private MyDatabase database;
    private Boolean updated;
    private RecyclerView rcView_operations;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_role_details, container, false);
        inflate();
        setComponentsOnClickListener();
        showOperationLayout();

        Bundle bundle = this.getArguments();

        current_role = null;
        new_role = null;
        updated = false;
        if(bundle != null){
            current_role = bundle.getParcelable("key");
        }
        showRoleData(current_role);


        getAssociatedOperations();





        return view;
    }

    private void showRecyclerView(ArrayList<Operation> operations){
        rcView_operations.setAdapter(new OperationAdapter(operations, RoleDetailsFragment.this));
    }

    private void getAssociatedOperations(){
        String id = current_role.getId();
        database.getAssociatedOperations(id, new OperationsReceiverListener() {
            @Override
            public void onReceive(ArrayList<Operation> operations) {
                showRecyclerView(operations);
            }
        });


    }


    private void showRoleData(Role role){
        database.getRoleDates(role.getId(), new RoleDateListener() {
            @Override
            public void OnReceive(HashMap<String, String> map) {
                edt_role_name.setText(role.getLibelle());
                edt_role_description.setText(role.getDescription());
                txt_create_at.setText(map.get("create at"));
                txt_update_at.setText(map.get("update at"));
                lbl_role_name.setText(role.getLibelle());
            }

            @Override
            public void OnError(Exception e) {

            }
        });
    }


    private void showRoleLayout(){
        const_role.setVisibility(View.VISIBLE);
        const_operation.setVisibility(View.GONE);
        line_role.setVisibility(View.VISIBLE);
        line_operation.setVisibility(View.GONE);

    }

    private void showOperationLayout(){
        const_role.setVisibility(View.GONE);
        const_operation.setVisibility(View.VISIBLE);
        line_role.setVisibility(View.GONE);
        line_operation.setVisibility(View.VISIBLE);
    }

    private void inflate(){
        const_role = view.findViewById(R.id.const_role_details);
        const_operation = view.findViewById(R.id.const_operations);
        line_role = view.findViewById(R.id.line_role);
        line_operation = view.findViewById(R.id.line_operation);
        lbl_role = view.findViewById(R.id.lbl_role_details);
        lbl_operation = view.findViewById(R.id.lbl_operations);
        icon_back = view.findViewById(R.id.icon_back_arrow);
        btn_save_role = view.findViewById(R.id.btn_save_role);
        btn_reset_role = view.findViewById(R.id.btn_reset_role);
        edt_role_name = view.findViewById(R.id.edt_role_name);
        edt_role_description = view.findViewById(R.id.edt_role_description);
        database = new MyDatabase(getActivity());
        txt_create_at = view.findViewById(R.id.txt_create_at);
        txt_update_at = view.findViewById(R.id.txt_update_at);
        icon_delete_role = view.findViewById(R.id.icon_delete_role);
        lbl_role_name = view.findViewById(R.id.lbl_role_name);
        rcView_operations = view.findViewById(R.id.rcView_operation);
        rcView_operations.setLayoutManager(new LinearLayoutManager(getActivity()));
        btn_add_operation = view.findViewById(R.id.btn_add_operation);
        btn_add_operation.setOnClickListener(RoleDetailsFragment.this);


    }

    private void setComponentsOnClickListener(){
        lbl_role.setOnClickListener(RoleDetailsFragment.this);
        lbl_operation.setOnClickListener(RoleDetailsFragment.this);
        icon_back.setOnClickListener(RoleDetailsFragment.this);
        btn_save_role.setOnClickListener(RoleDetailsFragment.this);
        btn_reset_role.setOnClickListener(RoleDetailsFragment.this);
        icon_delete_role.setOnClickListener(RoleDetailsFragment.this);
    }

    @SuppressLint({"RestrictedApi", "NonConstantResourceId"})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lbl_role_details:
                showRoleLayout();
                break;
            case R.id.lbl_operations:
                showOperationLayout();
                break;
            case R.id.icon_back_arrow:
                goBack();
                break;
            case R.id.btn_save_role:
                updateRole();

                break;
            case R.id.btn_reset_role:
                resetRole();
                break;
            case R.id.icon_delete_role:
                deleteRoleDialog();
                break;
            case R.id.btn_add_operation:
                showDialog();
                break;



        }
    }

    private void showDialog() {

    }

    private void deleteRoleDialog(){
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_delete_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.show();
        Button btn_yes = dialog.findViewById(R.id.btn_yes);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.deleteRole(updated ? new_role.getLibelle() : current_role.getLibelle(), new ResponseListener() {
                    @Override
                    public void onSuccess(Object object) {
                        makeToast("this role is deleted");
                        dialog.cancel();
                    }

                    @Override
                    public void onError(Exception exception) {

                    }
                });
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    private void resetRole(){
        edt_role_name.setText(current_role.getLibelle());
        edt_role_description.setText(current_role.getDescription());
    }

    private void updateRole() {
        updated = true;
        new_role = new Role(current_role);
        new_role.setDescription(edt_role_description.getText().toString());
        new_role.setLibelle(edt_role_name.getText().toString());
        database.checkRoleName(new_role.getId(), new_role.getLibelle(), new FindRoleListener() {
            @Override
            public void OnFound(Boolean exist) {
                if(exist){
                    makeToast("this role name is exist");
                }else {
                    database.updateRole(new_role);
                    makeToast("updated successfully");
                    showRoleData(new_role);
                }
            }
        });

    }



    private void goBack(){
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new RolesFragment()).commit();
    }

    private void refresh(){
        Bundle bundle = new Bundle();
        bundle.putParcelable("key", new_role);

        RoleDetailsFragment roleDetailsFragment = new RoleDetailsFragment();
        roleDetailsFragment.setArguments(bundle);

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, roleDetailsFragment);
        fragmentTransaction.commit();
    }

    private void makeToast(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onClick(int position, boolean view) {
        if(view){
            Toast.makeText(getActivity(), "clicked view icon at "+position, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getActivity(), "clicked delete icon at "+position, Toast.LENGTH_SHORT).show();

        }
    }
}