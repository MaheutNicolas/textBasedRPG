package com.djevent;

import com.controller.MonitorController;
import com.phase.EventPhase;

public class Hit implements EventAction{
    private final EventPhase eventPhase;
    private final MonitorController monitor;

    public Hit(EventPhase eventPhase, MonitorController monitor) {
        this.eventPhase = eventPhase;
        this.monitor = monitor;
    }

    @Override
    public void execute() {
        if(!eventPhase.isEventAvailable())return;
        switch (eventPhase.getEventRef()) {
            case 0:
                eventPhase.eventReward(0);
                break;
            case 1:
                monitor.addText("Vous frappez la fontaine mais rien ne se passe");
                break;
        }
    }
}
