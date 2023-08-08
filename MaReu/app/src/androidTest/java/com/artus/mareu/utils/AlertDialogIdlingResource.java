package com.artus.mareu.utils;

import androidx.test.espresso.IdlingResource;
import java.util.concurrent.atomic.AtomicBoolean;

public class AlertDialogIdlingResource implements IdlingResource {
    private boolean isIdle;
    private ResourceCallback resourceCallback;

    @Override
    public String getName() {
        return AlertDialogIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        resourceCallback = callback;
    }

    public void setIdleState(boolean isIdle) {
        this.isIdle = isIdle;
        if (isIdle && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }
    }
}
