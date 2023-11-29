package com.town;

abstract public class Place {
    protected Runnable onExit;
    public Place(){

    }
    public abstract void enter();
    public abstract void exit();

    public void setOnExit(Runnable onExit){
        this.onExit = onExit;
    }


}
