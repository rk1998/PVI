package com.pvi.jd.gt.personalvirtualinventories;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.*;
import android.support.annotation.NonNull;

public final class LifeCycleTest implements LifecycleOwner {
    private final LifecycleRegistry registry = new LifecycleRegistry(this);

    private LifeCycleTest() {

    }

    private LifeCycleTest handleLifecycleEvent(@NonNull Lifecycle.Event event) {
        registry.handleLifecycleEvent(event);
        return this;
    }

    public LifeCycleTest create() {
        return handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    public LifeCycleTest start() {
        return handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    public LifeCycleTest resume() {
        return handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    public LifeCycleTest pause() {
        return handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }

    public LifeCycleTest stop() {
        return handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    public LifeCycleTest destroy() {
        return handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    @NonNull
    public Lifecycle.State getCurrentState() {
        return registry.getCurrentState();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return registry;
    }

    public static LifeCycleTest initialized() {
        return new LifeCycleTest();
    }

    public static LifeCycleTest resumed() {
        return initialized().resume();


    }
}
