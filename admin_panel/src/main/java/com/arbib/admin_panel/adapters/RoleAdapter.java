package com.arbib.admin_panel.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arbib.admin_panel.R;
import com.arbib.admin_panel.interfaces.ItemOnClickListener;
import com.arbib.admin_panel.objects.Role;

import java.util.ArrayList;

public class RoleAdapter extends RecyclerView.Adapter<RoleAdapter.ViewHolder> {

    private ArrayList<Role> roles;
    private ItemOnClickListener listener;

    public RoleAdapter(ArrayList<Role> roles, final ItemOnClickListener listener){
        this.roles = roles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_role, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RoleAdapter.ViewHolder holder, int position) {
        Role role = roles.get(position);
        holder.txt_role_name.setText(role.getLibelle());
        holder.txt_role_nbr_operations.setText(String.valueOf(role.getNbr_operations()));

    }



    @Override
    public int getItemCount() {
        return roles.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_role_name, txt_role_nbr_operations;
        public ViewHolder(@NonNull View itemView, ItemOnClickListener listener) {
            super(itemView);
            txt_role_name = itemView.findViewById(R.id.role_name);
            txt_role_nbr_operations = itemView.findViewById(R.id.role_nbr_operation);

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
