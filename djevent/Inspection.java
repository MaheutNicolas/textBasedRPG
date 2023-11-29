package com.djevent;

import com.controller.MonitorController;
import com.phase.EventPhase;

public class Inspection implements EventAction{
    private final EventPhase eventPhase;
    private final MonitorController monitor;
    int evenRef;
    public Inspection(EventPhase eventPhase, MonitorController monitor) {
        this.eventPhase = eventPhase;
        this.monitor = monitor;
    }

    @Override
    public void execute() {
        if(!eventPhase.isEventAvailable())return;
        switch (eventPhase.getEventRef()) {
            case 0:
                monitor.addText("Vous n'appercevez rien de particulier");
                break;
            case 1:
                monitor.addText("L'eau est clair et sort du haut de la fontaine," +
                        " vous n'aperçevez rien de particulier");
                break;
        }
    }
}
