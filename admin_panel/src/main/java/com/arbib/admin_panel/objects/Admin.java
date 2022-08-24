package com.arbib.admin_panel.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Admin implements Parcelable {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String birthday;
    private String role;
    private String phone;
    private String state;

    public Admin(String id, String firstname, String lastname, String email,
                 String birthday, String role, String phone, String state) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.birthday = birthday;
        this.role = role;
        this.phone = phone;
        this.state = state;
    }

    public Admin(String firstname, String lastname, String email,
                 String birthday, String role, String phone, String state) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.birthday = birthday;
        this.role = role;
        this.phone = phone;
        this.state = state;
    }

    protected Admin(Parcel in) {
        id = in.readString();
        firstname = in.readString();
        lastname = in.readString();
        email = in.readString();
        birthday = in.readString();
        role = in.readString();
        phone = in.readString();
        state = in.readString();
    }

    public static final Creator<Admin> CREATOR = new Creator<Admin>() {
        @Override
        public Admin createFromParcel(Parcel in) {
            return new Admin(in);
        }

        @Override
        public Admin[] newArray(int size) {
            return new Admin[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(firstname);
        parcel.writeString(lastname);
        parcel.writeString(email);
        parcel.writeString(birthday);
        parcel.writeString(role);
        parcel.writeString(phone);
        parcel.writeString(state);
    }
}
