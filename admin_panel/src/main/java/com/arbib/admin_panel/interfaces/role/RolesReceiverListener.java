package com.arbib.admin_panel.interfaces.role;

import com.arbib.admin_panel.objects.Role;

import java.util.ArrayList;

public interface RolesReceiverListener {
    void OnReceive(ArrayList<Role> roles);
    void OnError(Exception e);
}
