package cn.oj.codebase.patterns.observer;

public class ObserverImpl implements Observer {
    private String name;
    public ObserverImpl(String name) {
        this.name = name;
    }

    @Override
    public void note(String note) {
        System.out.println(name + ":收到:" + note);
    }
}
