package com.djevent;

import com.controller.MonitorController;
import com.phase.EventPhase;

public class Speak implements EventAction{
    private final EventPhase eventPhase;
    private final MonitorController monitor;
    int evenRef;
    public Speak(EventPhase eventPhase, MonitorController monitor) {
        this.eventPhase = eventPhase;
        this.monitor = monitor;
    }

    @Override
    public void execute() {
        if (!eventPhase.isEventAvailable()) return;
        switch (eventPhase.getEventRef()) {
            case 0:
                monitor.addText("Vous essayez vraiment de communiquer avec un mur ?");
                break;
            case 1:
                monitor.addText("Pourquoi chercher a parler a une fontaine ?");
                break;
        }
    }
}
