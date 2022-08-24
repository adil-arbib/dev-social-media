package com.arbib.admin_panel.interfaces.skills;

import com.arbib.admin_panel.objects.Skill;

public interface skillListener {
    void onSkillReceived(Skill skill);
    void onError(Throwable error);
}
