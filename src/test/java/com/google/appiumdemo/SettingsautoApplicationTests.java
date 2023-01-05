package com.google.appiumdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@SpringBootTest(classes = SettingsautoApplication.class)
@WebAppConfiguration
@ContextConfiguration
public class SettingsautoApplicationTests extends AbstractTestNGSpringContextTests {

    public MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @BeforeClass
    public void setUp() {
        //MockMvcBuilders.webAppContextSetup(wac).build()创建一个MockMvc进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testcase1_1_1() throws Exception {
       // Assert.assertEquals("1","2");
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/pixel/testcase1.1.1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        mvcResult.getResponse().getContentAsString();
    }


}
