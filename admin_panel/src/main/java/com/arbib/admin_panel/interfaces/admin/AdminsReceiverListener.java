package com.arbib.admin_panel.interfaces.admin;

import com.arbib.admin_panel.objects.Admin;

import java.util.ArrayList;

public interface AdminsReceiverListener {
    void OnReceive(ArrayList<Admin> adminsList);
    void OnError(Exception e);

}
