package com.arbib.admin_panel.interfaces.role;

import java.util.HashMap;

public interface RoleDateListener {
    void OnReceive(HashMap<String, String> map);
    void OnError(Exception e);
}
