package cn.cary.codebase.generator.sys.controller;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

/**
 * @program: codebase
 * @description:
 * @author: 郑剑锋
 * @create: 2021-04-18 09:50
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BaseControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    MockMvc mockMvc;
}
