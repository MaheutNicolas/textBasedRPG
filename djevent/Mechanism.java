package com.djevent;

import com.controller.MonitorController;
import com.phase.EventPhase;

public class Mechanism implements EventAction{
    private final EventPhase eventPhase;
    private final MonitorController monitor;
    int evenRef;
    public Mechanism(EventPhase eventPhase, MonitorController monitor) {
        this.eventPhase = eventPhase;
        this.monitor = monitor;
    }

    @Override
    public void execute() {
        if(!eventPhase.isEventAvailable())return;
        switch (eventPhase.getEventRef()) {
            case 0:
            case 1:
                monitor.addText("Vous cherchez sans succès un méchanisme a activé");
                break;
        }
    }
}
