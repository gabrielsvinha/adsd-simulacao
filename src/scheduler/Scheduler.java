package scheduler;

import event.Event;
import event.EventType;

import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {

    private static final int speed = 1;
    private int state;
    private Queue<Event> q1;
    private Queue<Event> q2;
    private Event inService;

    public Scheduler(){
        this.state = 0;
        this.q1 = new LinkedList<>();
        this.q2 = new LinkedList<>();
        this.inService = null;
    }

    public void run(int time){
        int secs = 0;
        try {
            while (secs < time){
                Thread.sleep(speed);
                secs++;
                this.checkEvents(secs);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getState() {
        return state;
    }

    private Event getNextEvent(int time){
        System.out.print("Nome do evento: " + e.getName() + ", ");
        System.out.print("Tipo do evento: " + e.getType() + ", ");
        System.out.print("Momento do evento: " + time + ", ");
        System.out.println("Elementos na fila 1: " + this.q1.toString());
        System.out.println("Elementos na fila 3: " + this.q2.toString());
        if (this.state == 0) {
            System.out.println("Elemento em serviço: " + this.inService.getName());
        } else {
            System.out.println("Elemento em serviço: Nenhum");
        }
        return null;
    }


    private void endOfService(Event e, int time){

    }

    private void changeState(Event e, int secs){
        this.state = 1;
        this.endOfService(e, secs);
    }

    private void checkEvents(int time){
        Event e = this.getNextEvent(time);
        if (e == null) {
            return;
        }
        if (e.getType() == EventType.ARRIVAL_CLASS_1) {
            if (this.getState() == 0) { // status free
                this.changeState(e, time);
            } else { // status busy
                this.q1.add(e);
            }
        } else if (e.getType() == EventType.ARRIVAL_CLASS_2) {
            if (this.getState() == 0) { // status free
                this.changeState(e, time);
            } else { // status busy
                this.q2.add(e);
            }
        } else if (e.getType() == EventType.EOS){

        }
        this.checkEvents(time);
    }
}
