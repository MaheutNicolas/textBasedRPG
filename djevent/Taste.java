package com.djevent;

import com.controller.MonitorController;
import com.phase.EventPhase;
import com.rpg.Player;

public class Taste implements EventAction{
    private final Player player;
    private final EventPhase eventPhase;
    private final MonitorController monitor;

    int evenRef;
    public Taste(EventPhase eventPhase, MonitorController monitor, Player player) {
        this.eventPhase = eventPhase;
        this.monitor = monitor;
        this.player = player;
    }

    @Override
    public void execute() {
        if(!eventPhase.isEventAvailable())return;
        switch (eventPhase.getEventRef()) {
            case 0:
                this.eventPhase.eventLoss(0);
                break;
            case 1:
                this.monitor.addText("Vous goûtez à l'eau de la fontaine et vous êtes soigner");
                player.setHp(player.getMaxHP());
                player.setMana(player.getMaxMana());
                break;
        }
    }
}
