package scheduler;

import event.Event;
import event.EventType;
import numbergenerator.RandomNumberGenerator;
import numbergenerator.uniform.UniformDistribution;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;

public class Scheduler {

    private static final int speed = 1;
    private int state;
    private Queue<Event> q1;
    private Queue<Event> q2;
    private PriorityQueue<Integer> time;
    private PriorityQueue<Event> events;
    private Event inService;
    private RandomNumberGenerator uniform;

    public Scheduler(){
        this.state = 0;
        this.q1 = new LinkedList<>();
        this.q2 = new LinkedList<>();
        this.inService = null;
        this.events = new PriorityQueue<>();
        this.time = new PriorityQueue<>();
        this.uniform = new UniformDistribution(2, 6);
        this.startQueue1(0);
        this.startQueue2(0);
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
        Event e;
        int t;
        try{
            t = this.time.element();
            e = this.events.element();
        } catch (NoSuchElementException ex) {
            return null;
        }
        System.out.print("Nome do evento: " + e.getName() + ", ");
        System.out.print("Tipo do evento: " + e.getType() + ", ");
        System.out.println("Momento do evento: " + t);
        System.out.println("Elementos na fila 1: " + this.q1.toString());
        System.out.println("Elementos na fila 2: " + this.q2.toString());
        if (this.state == 1) {
            System.out.println("Elemento em serviço: " + this.inService.getName());
        } else {
            System.out.println("Elemento em serviço: Nenhum");
        }
        return e;
    }


    private void startQueue1(int time){
        Event e = new Event(EventType.ARRIVAL_CLASS_1);
        this.scheduleEvent(e, time + this.uniform.sample());
    }

    private void startQueue2(int time){
        Event e = new Event(EventType.ARRIVAL_CLASS_2);
        this.scheduleEvent(e, time + this.uniform.sample());
    }

    private void scheduleEvent(Event e, int time){
        this.events.add(e);
        this.time.add(time);
    }

    private void endOfService(Event e, int time){
        Event event = new Event(e.getName(), EventType.EOS);
        this.inService = event;
        this.scheduleEvent(event, time + this.uniform.sample());
    }

    private void changeState(Event e, int secs){
        this.state = 1;
        this.endOfService(e, secs);
    }

    private void verifyEvents(int t){
        if (!this.q1.isEmpty() && !this.q2.isEmpty()) {
            this.state = 0;
        } else if (this.q1.isEmpty()){
            Event e = this.q1.peek();
            this.endOfService(e, t);
        } else {
            Event e = this.q2.peek();
            this.endOfService(e, t);
        }
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
            this.startQueue1(time);
        } else if (e.getType() == EventType.ARRIVAL_CLASS_2) {
            if (this.getState() == 0) { // status free
                this.changeState(e, time);
            } else { // status busy
                this.q2.add(e);
            }
            this.startQueue2(time);
        } else if (e.getType() == EventType.EOS){
            this.verifyEvents(time);
        }
        this.checkEvents(time);
    }
}
