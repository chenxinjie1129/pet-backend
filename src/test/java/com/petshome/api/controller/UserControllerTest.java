package com.petshome.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshome.api.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 用户控制器测试类
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 测试用户注册接口
     */
    @Test
    public void testRegister() throws Exception {
        // 创建测试用户
        User user = new User();
        user.setUsername("testuser" + System.currentTimeMillis()); // 使用时间戳确保用户名唯一
        user.setPassword("password123");
        user.setMobile("13800" + System.currentTimeMillis() % 100000000); // 使用时间戳生成唯一手机号
        user.setNickname("测试用户");
        user.setGender(1);
        user.setEmail("test@example.com");

        // 发送注册请求
        MvcResult result = mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("注册成功"))
                .andExpect(jsonPath("$.data.userId").exists())
                .andExpect(jsonPath("$.data.username").value(user.getUsername()))
                .andExpect(jsonPath("$.data.token").exists())
                .andReturn();

        // 打印响应结果
        System.out.println("注册响应: " + result.getResponse().getContentAsString());
    }

    /**
     * 测试用户名已存在的情况
     */
    @Test
    public void testRegisterWithExistingUsername() throws Exception {
        // 创建测试用户
        User user = new User();
        user.setUsername("existinguser"); // 假设这个用户名已经存在
        user.setPassword("password123");
        user.setMobile("13900139000");
        user.setNickname("已存在用户");
        user.setGender(1);
        user.setEmail("existing@example.com");

        // 发送注册请求
        MvcResult result = mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andReturn();

        // 打印响应结果
        System.out.println("用户名已存在响应: " + result.getResponse().getContentAsString());
    }

    /**
     * 测试手机号已存在的情况
     */
    @Test
    public void testRegisterWithExistingMobile() throws Exception {
        // 创建测试用户
        User user = new User();
        user.setUsername("newuser" + System.currentTimeMillis());
        user.setPassword("password123");
        user.setMobile("13800138001"); // 假设这个手机号已经存在
        user.setNickname("新用户");
        user.setGender(1);
        user.setEmail("new@example.com");

        // 发送注册请求
        MvcResult result = mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andReturn();

        // 打印响应结果
        System.out.println("手机号已存在响应: " + result.getResponse().getContentAsString());
    }
}
