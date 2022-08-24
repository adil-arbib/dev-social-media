package com.arbib.admin_panel.interfaces.skills;

import com.arbib.admin_panel.objects.Skill;

import java.util.ArrayList;

public interface ListSkillsListener {
    void onSkillsReceived(ArrayList<Skill> skills);
    void onError(Exception error);
}
