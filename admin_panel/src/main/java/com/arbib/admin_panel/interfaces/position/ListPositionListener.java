package com.arbib.admin_panel.interfaces.position;

import com.arbib.admin_panel.objects.Position;

import java.util.ArrayList;

public interface ListPositionListener {
    void onReceived(ArrayList<Position> positions);
    void onError(Exception exception);
}
