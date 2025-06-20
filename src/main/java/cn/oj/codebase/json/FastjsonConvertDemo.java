package cn.oj.codebase.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName FastjsonConvertDemo.java
 * @Description fastjson常用转换
 * @createTime 2021年12月13日 16:27:00
 */
@Slf4j
public class FastjsonConvertDemo {

    @Data
    class Person {

        @JSONField(name = "AGE")
        private int age;

        @JSONField(name = "FULL NAME")
        private String fullName;

        @JSONField(name = "DATE OF BIRTH")
        private Date dateOfBirth;

        public Person(int age, String fullName, Date dateOfBirth) {
            super();
            this.age = age;
            this.fullName = fullName;
            this.dateOfBirth = dateOfBirth;
        }

    }

    private List<Person> personList;

    public FastjsonConvertDemo() {
        this.personList = new ArrayList<>();
        personList.add(new Person(15, "John Doe", new Date()));
        personList.add(new Person(20, "Janette Doe", new Date()));
    }

    // 对象转json字符串
    private void toJsonString() {
        log.info(JSON.toJSONString(personList));
    }

    public static void main(String[] args) {
        FastjsonConvertDemo fastjsonConvertDemo = new FastjsonConvertDemo();
        fastjsonConvertDemo.toJsonString();
    }


}
