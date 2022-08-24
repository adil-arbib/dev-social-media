package com.arbib.admin_panel.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arbib.admin_panel.R;
import com.arbib.admin_panel.interfaces.ItemOnClickListener;
import com.arbib.admin_panel.objects.Admin;

import java.util.ArrayList;

public class AdminsAdapter extends RecyclerView.Adapter<AdminsAdapter.ViewHolder> {

    private ArrayList<Admin> admins;
    private final ItemOnClickListener listener;

    public AdminsAdapter(ArrayList<Admin> admins, ItemOnClickListener listener){
        this.admins = admins;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdminsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_lastname.setText(admins.get(position).getLastname());
        holder.txt_firstname.setText(admins.get(position).getFirstname());
        holder.txt_email.setText(admins.get(position).getEmail());
        holder.txt_role.setText(admins.get(position).getRole());
    }

    @Override
    public int getItemCount() {
        return admins.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_firstname, txt_lastname, txt_email, txt_role;
        public ViewHolder(@NonNull View itemView, ItemOnClickListener listener) {
            super(itemView);
            txt_firstname = itemView.findViewById(R.id.firstname);
            txt_lastname = itemView.findViewById(R.id.lastname);
            txt_email = itemView.findViewById(R.id.email);
            txt_role = itemView.findViewById(R.id.role);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.OnItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
