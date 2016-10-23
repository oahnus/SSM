package cn.edu.just;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:spring-mvc.xml")
public class TestTeacherController {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void getTeacherList() throws Exception {
        String json = "{\"depart\":\"计算机\"}";

        mockMvc.perform(post("/teacher/")
                .characterEncoding("UTF-8").header("method","get")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andDo(print());
    }

    @Test
    public void getTeacherInfo() throws Exception {
        mockMvc.perform(post("/teacher/10046/")
                .header("method","get")).andDo(print());
    }

    @Test
    public void getProfession() throws Exception {
        mockMvc.perform(post("/teacher/")
                .header("method","profession")).andDo(print());
    }




}
