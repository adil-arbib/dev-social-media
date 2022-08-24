package com.arbib.admin_panel.interfaces;

public interface ResponseListener {
    void onSuccess(Object object);
    void onError(Exception exception);
}
