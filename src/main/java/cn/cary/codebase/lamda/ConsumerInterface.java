package cn.cary.codebase.lamda;

/**
 * @author 郑剑锋
 * @Description 自定义函数接口
 * @createTime 2022.05.27 09:49:00
 */
@FunctionalInterface
public interface ConsumerInterface<T> {

    void accept(T t);

    public static void main(String[] args) {
        ConsumerInterface<String> consumer = str -> System.out.println(str);
    }
}
