package com.arbib.admin_panel.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arbib.admin_panel.MyDatabase;
import com.arbib.admin_panel.R;
import com.arbib.admin_panel.adapters.RoleAdapter;
import com.arbib.admin_panel.interfaces.ItemOnClickListener;
import com.arbib.admin_panel.interfaces.role.RolesReceiverListener;
import com.arbib.admin_panel.objects.Role;

import java.util.ArrayList;


public class RolesFragment extends Fragment implements ItemOnClickListener, View.OnClickListener {

   private View view;
   private RecyclerView recyclerView;
   private ArrayList<Role> base_array;
   private MyDatabase database;
   private RoleAdapter adapter;
   private EditText edt_search;
   private Button btn_add_role;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_roles, container, false);
        inflate();
        getRoles();
        setEdtTextOnListener();





        return view;
    }

    private void setEdtTextOnListener(){
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = edt_search.getText().toString().toLowerCase();
                ArrayList<Role> tmp_array = new ArrayList<>();
                for(Role role : base_array){
                    if(role.getLibelle().contains(input)) tmp_array.add(role);
                }
                showRecyclerView(tmp_array);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void getRoles(){
        database.getAllRoles(new RolesReceiverListener() {
            @Override
            public void OnReceive(ArrayList<Role> roles) {
                base_array = roles;
                showRecyclerView(roles);
            }

            @Override
            public void OnError(Exception e) {

            }
        });
    }

    private void inflate(){
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        base_array = new ArrayList<>();
        database = new MyDatabase(getActivity());
        edt_search = view.findViewById(R.id.edt_search);
        btn_add_role = view.findViewById(R.id.btn_add_role);

        btn_add_role.setOnClickListener(RolesFragment.this);
    }

    private void showRecyclerView(ArrayList<Role> roles){
        adapter = new RoleAdapter(roles, RolesFragment.this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void OnItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("key", base_array.get(position));

        RoleDetailsFragment roleDetailsFragment = new RoleDetailsFragment();
        roleDetailsFragment.setArguments(bundle);

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, roleDetailsFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_add_role){
            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddRoleFragment()).commit();
        }
    }
}




