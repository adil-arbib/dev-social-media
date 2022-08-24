package com.arbib.admin_panel.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arbib.admin_panel.R;
import com.arbib.admin_panel.interfaces.ComplicatedItemOnClickListener;
import com.arbib.admin_panel.interfaces.ItemOnClickListener;
import com.arbib.admin_panel.objects.Operation;

import java.util.ArrayList;

public class OperationAdapter extends RecyclerView.Adapter<OperationAdapter.ViewHolder> {

    private ArrayList<Operation> operations;
    private ComplicatedItemOnClickListener listener;

    public OperationAdapter(ArrayList<Operation> operations, ComplicatedItemOnClickListener listener){
        this.operations = operations;
        this.listener = listener;
    }


    @NonNull
    @Override
    public OperationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_operations, parent, false);

        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull OperationAdapter.ViewHolder holder, int position) {
        Operation operation = operations.get(position);
        holder.txt_name.setText(operation.getLibelle());
    }

    @Override
    public int getItemCount() {
        return operations.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_name;
        ImageView icon_view, icon_delete;

        public ViewHolder(@NonNull View itemView, ComplicatedItemOnClickListener listener) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            icon_view = itemView.findViewById(R.id.icon_view);
            icon_delete = itemView.findViewById(R.id.icon_delete);

            icon_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.onClick(pos,true);
                        }
                    }
                }
            });

            icon_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.onClick(pos,false);
                        }
                    }
                }
            });
        }
    }
}
