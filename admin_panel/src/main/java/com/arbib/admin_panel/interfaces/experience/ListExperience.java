package com.arbib.admin_panel.interfaces.experience;

import com.arbib.admin_panel.objects.Experience;

import java.util.ArrayList;

public interface ListExperience {
    void onReceived(ArrayList<Experience> experiences);
    void onError(Exception exception);
}
