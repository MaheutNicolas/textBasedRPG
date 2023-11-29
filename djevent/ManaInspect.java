package com.djevent;

import com.controller.MonitorController;
import com.phase.EventPhase;

public class ManaInspect implements EventAction{
    private final EventPhase eventPhase;
    private final MonitorController monitor;
    int evenRef;
    public ManaInspect(EventPhase eventPhase, MonitorController monitor) {
        this.eventPhase = eventPhase;
        this.monitor = monitor;
    }

    @Override
    public void execute() {
        if(!eventPhase.isEventAvailable())return;
        switch (eventPhase.getEventRef()) {
            case 0:
                monitor.addText("Le mur ne contient aucune trace de magie");
                break;
            case 1:
                eventPhase.eventReward(1);
                break;
        }
    }
}
