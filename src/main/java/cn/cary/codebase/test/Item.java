package cn.cary.codebase.test;

import lombok.Data;

/**
 * @program: codebase
 * @description:
 * @author: 郑剑锋
 * @create: 2021-04-14 17:18
 **/
@Data
public class Item{
    private int oldIndex;
    private int newIndex;
    private String title;
    public Item(int oldIndex,String title){
        this.oldIndex=oldIndex;
        this.title=title;
    }
}
