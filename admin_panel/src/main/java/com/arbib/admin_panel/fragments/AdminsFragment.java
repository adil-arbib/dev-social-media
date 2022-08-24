package com.arbib.admin_panel.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arbib.admin_panel.MyDatabase;
import com.arbib.admin_panel.R;
import com.arbib.admin_panel.adapters.AdminsAdapter;
import com.arbib.admin_panel.interfaces.ItemOnClickListener;
import com.arbib.admin_panel.interfaces.admin.AdminsReceiverListener;
import com.arbib.admin_panel.objects.Admin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class AdminsFragment extends Fragment implements ItemOnClickListener {
    private View view;
    private RecyclerView recyclerView;
    private AdminsAdapter adapter;
    private MyDatabase database;
    private ArrayList<Admin> admins;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admins, container, false);
        inflate();

        admins = new ArrayList<>();
        database = new MyDatabase(getActivity());




        getData();



        return view;
    }


    private void getData(){
        database.getAllAdmins(new AdminsReceiverListener() {
            @Override
            public void OnReceive(ArrayList<Admin> adminsList) {
                showRecyclerView(adminsList);
                admins.addAll(adminsList);
            }

            @Override
            public void OnError(Exception e) {

            }
        });
    }



    private void showRecyclerView(ArrayList<Admin> admins){
        adapter = new AdminsAdapter(admins, AdminsFragment.this);
        recyclerView.setAdapter(adapter);

    }

    private void inflate(){
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void OnItemClick(int position) {




        Bundle bundle = new Bundle();
        bundle.putParcelable("key", admins.get(position));

        AdminProfileFragment adminProfileFragment = new AdminProfileFragment();
        adminProfileFragment.setArguments(bundle);

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, adminProfileFragment);
        fragmentTransaction.commit();





    }
}