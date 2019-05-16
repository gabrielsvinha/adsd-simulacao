package event;

import sun.java2d.pipe.hw.AccelDeviceEventNotifier;

import java.util.LinkedList;
import java.util.Queue;

public class Event implements Comparable<Event>{

    private EventType type;
    private String name;

    public Event(EventType type){
        this.name = "";
        this.type = type;
    }

    public Event(String name, EventType type){
        this.name = name;
        this.type = type;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        Event e = (Event)o;
        return e.getName().equals(this.getName()) && e.getType() == this.getType();
    }

    @Override
    public int compareTo(Event o) {
        if (this.getType() == EventType.ARRIVAL_CLASS_1){
            return 1;
        } else if (this.getType() == EventType.ARRIVAL_CLASS_2 && o.getType() == EventType.EOS) {
            return -1;
        } else {
            return 0;
        }
    }
}
