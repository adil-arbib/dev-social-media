package com.arbib.admin_panel.interfaces.experience;

import com.arbib.admin_panel.objects.Experience;

public interface ExperienceReceiverListener {
    void onReceive(Experience experience);
    void onError(Exception exception);
}
