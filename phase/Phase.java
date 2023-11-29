package com.phase;


import javafx.scene.layout.FlowPane;

import java.util.function.Consumer;

abstract public class Phase {

    protected final FlowPane buttonPane;
    protected Consumer<PhaseManager.TypePhase> onExit;

    public Phase(FlowPane buttonPane){
        this.buttonPane = buttonPane;
    }
    abstract void onStart();
    abstract void onEnd();
    public void setOnExit(Consumer<PhaseManager.TypePhase> onExit) {
        this.onExit = onExit;
    }
}
