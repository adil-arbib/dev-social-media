package com.arbib.admin_panel.interfaces.position;

import com.arbib.admin_panel.objects.Position;

public interface positionReceiverListener {
    void onReceive(Position position);
    void onError(Exception exception);
}
