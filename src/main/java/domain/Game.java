package domain;

import java.util.ArrayList;

import java.util.List;


public class Game {

    private List<Fan> fansObserver = new ArrayList();
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        notifyAllObservers();
    }

    public boolean attachObserver(Fan fan){
        if(fansObserver.add(fan))
            return true;
        return false;
    }

    public boolean removeObserver(Fan fan){
        if(fansObserver.remove(fan))
            return true;
        return false;
    }

    public boolean checkObserver(Fan fan){
        if(fansObserver.contains(fan))
            return true;
        return false;
    }

    public void notifyAllObservers(){
        for (Fan fan : fansObserver) {
            fan.update();
        }
    }


}
