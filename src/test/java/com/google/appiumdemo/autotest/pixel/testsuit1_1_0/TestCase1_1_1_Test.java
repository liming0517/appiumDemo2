package com.google.appiumdemo.autotest.pixel.testsuit1_1_0;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.appiumdemo.SettingsautoApplication;
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
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@SpringBootTest(classes = SettingsautoApplication.class)
@WebAppConfiguration
@ContextConfiguration
public class TestCase1_1_1_Test extends AbstractTestNGSpringContextTests {

    public MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @BeforeClass
    public void setUp() {
        //MockMvcBuilders.webAppContextSetup(wac).build()创建一个MockMvc进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testcase11() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/pixel/t11"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = JSON.parseObject(content);
        Assert.assertEquals(jsonObject.get("status"), 2000);
        Assert.assertEquals(jsonObject.get("message"), "success");
        //throw new SkipException("skip this method");
    }


    @Test
    public void testcase42() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/pixel/t42"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = JSON.parseObject(content);
        Assert.assertEquals(jsonObject.get("status"), 2000);
        Assert.assertEquals(jsonObject.get("message"), "success");    }

}
