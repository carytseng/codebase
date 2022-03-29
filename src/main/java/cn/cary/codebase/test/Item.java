package cn.cary.codebase.test;

import lombok.Data;

import java.util.Stack;

/**
 * @program: codebase
 * @description:
 * @author: 郑剑锋
 * @create: 2021-04-14 17:18
 **/
@Data
public class Item {
    private int oldIndex;
    private int newIndex;
    private String title;

    public Item(int oldIndex, String title) {
        this.oldIndex = oldIndex;
        this.title = title;
    }

    public static void main(String[] args) {
        int i = 5/2;
        System.out.println(i);
    }
}
