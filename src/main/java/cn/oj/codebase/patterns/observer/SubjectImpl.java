package cn.oj.codebase.patterns.observer;

import java.util.ArrayList;
import java.util.List;

public class SubjectImpl implements Subject {
    private List<Observer> observers = new ArrayList<>();
    @Override
    public boolean addObserver(Observer observer) {
        return observers.add(observer);
    }

    @Override
    public boolean removeObserver(Observer observer) {
        return observers.remove(observer);
    }

    @Override
    public void notifyObservers(String note) {
        observers.forEach(observer -> observer.note(note));
    }

    public void publish(String note) {
        notifyObservers(note);
    }

    public static void main(String[] args) {
        Observer a1 = new ObserverImpl("a1");
        Observer a2 = new ObserverImpl("a2");
        Observer a3 = new ObserverImpl("a3");
        SubjectImpl subject = new SubjectImpl();
        subject.addObserver(a1);
        subject.addObserver(a2);
        subject.addObserver(a3);
        subject.publish("发表文章:孔子说");
    }
}
