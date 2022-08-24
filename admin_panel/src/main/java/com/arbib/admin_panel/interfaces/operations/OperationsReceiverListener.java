package com.arbib.admin_panel.interfaces.operations;

import com.arbib.admin_panel.objects.Operation;

import java.util.ArrayList;

public interface OperationsReceiverListener {
    void onReceive(ArrayList<Operation> operations);
}
