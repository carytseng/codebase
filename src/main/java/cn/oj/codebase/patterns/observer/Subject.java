package cn.oj.codebase.patterns.observer;

public interface Subject {
    boolean addObserver(Observer observer);
    boolean removeObserver(Observer observer);
    void notifyObservers(String note);
}
