package cn.oj.codebase.jdk17;

public class InstanceDemo {

    public void exe() {
        var st = """
                1.	File > Project Structure > Project，检查 Project SDK 是否是 17。
                	2.	Project language level 选择：17 - Sealed types, always-strict floating-point semantics, pattern matching for switch (preview)。
                	3.	File > Settings > Build, Execution, Deployment > Compiler > Java Compiler，确认 Per-module bytecode version 为 17。
                	4.	然后 重新构建项目：Build > Rebuild Project。
                """;
        System.out.println(st);
    }

    record Rectangle(double length, double width) {
    }

    public void record() {
        Rectangle r = new Rectangle(1, 3);
        System.out.println(r);
    }

    public Integer switchT(Integer day) {
        return switch (day) {
            case 1, 2, 3 -> 3;
            case 7 -> 7;
            default -> 0;
        };
    }

    public void instanceOfT(Object obj) {
        Integer k = null;
        if (obj instanceof InstanceDemo instance) {
            k = instance.switchT(7);
        }
        System.out.println(k);
    }

    public static void main(String[] args) {
        InstanceDemo instanceDemo = new InstanceDemo();
//        instanceDemo.exe();
//        instanceDemo.record();
//        instanceDemo.switchT(6);
        instanceDemo.instanceOfT(instanceDemo);
    }
}
