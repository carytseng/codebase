package cn.cary.codebase.lamda;

/**
 * @author 郑剑锋
 * @Description TODO
 * @createTime 2022.05.27 09:24:00
 */
public class AnonymousClasses {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("ojbk");
            }
        });

        new Thread(() -> System.out.println("ojbk"));


    }
}
