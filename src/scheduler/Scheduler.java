package scheduler;

import event.Event;
import event.EventType;

public class Scheduler {
    private static final int speed = 1;
    private int state;
    public Scheduler(){
        this.state = 0;
    }

    public void run(int time){
        int secs = 0;
        while (secs < time){
            Thread.sleep(speed);
            secs++;
            this.checkEvents(secs);
        }
    }

    private Event getNextEvent(int time){
        return null;
    }

    private void checkEvents(int time){
        Event e = this.getNextEvent(time);
        if (e == null) {
            return;
        }
        if (e.getType() == EventType.ARRIVAL_CLASS_1) {

        } else if (e.getType() == EventType.ARRIVAL_CLASS_2) {

        } else if (e.getType() == EventType.EOS){

        }
        this.checkEvents(time);
    }
}
