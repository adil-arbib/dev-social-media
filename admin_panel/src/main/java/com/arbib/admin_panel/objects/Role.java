package com.arbib.admin_panel.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Role implements Parcelable {
    private String id;
    private String libelle;
    private String description;
    private int nbr_operations;
    private ArrayList<Operation> operations;

    public Role(String id, String libelle, String description,
                int nbr_operations, ArrayList<Operation> operations) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
        this.nbr_operations = nbr_operations;
        this.operations = operations;
    }


    public Role(Role role){
        this.id = role.getId();
        this.libelle = role.getLibelle();
        this.description = role.getDescription();
        this.nbr_operations = role.getNbr_operations();
        this.operations = new ArrayList<>();
        this.operations.addAll(role.getOperations());
    }




    public Role(String libelle, String description,
                int nbr_operations, ArrayList<Operation> operations) {
        this.libelle = libelle;
        this.description = description;
        this.nbr_operations = nbr_operations;
        this.operations = operations;
    }

    protected Role(Parcel in) {
        id = in.readString();
        libelle = in.readString();
        description = in.readString();
        nbr_operations = in.readInt();
    }

    public static final Creator<Role> CREATOR = new Creator<Role>() {
        @Override
        public Role createFromParcel(Parcel in) {
            return new Role(in);
        }

        @Override
        public Role[] newArray(int size) {
            return new Role[size];
        }
    };


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getNbr_operations() {
        return nbr_operations;
    }

    public void setNbr_operations(int nbr_operations) {
        this.nbr_operations = nbr_operations;
    }

    public ArrayList<Operation> getOperations() {
        return operations;
    }

    public void setOperations(ArrayList<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(libelle);
        parcel.writeString(description);
        parcel.writeInt(nbr_operations);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", libelle='" + libelle + '\'' +
                ", description='" + description + '\'' +
                ", nbr_operations=" + nbr_operations +
                ", operations=" + operations.toString() +
                '}';
    }
}
