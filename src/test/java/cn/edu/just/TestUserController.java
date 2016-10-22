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
@ContextConfiguration("classpath:spring-mvc.xml")
@WebAppConfiguration
public class TestUserController {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testVerify() throws Exception {
        String json = "{\"data\":[" +
                "{\"username\":\"admin\",\"password\":\"admin\",\"actor\":\"1\"}" +
            "]}";

        mockMvc.perform(post("/user/")
                .characterEncoding("UTF-8").header("method","verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andDo(print());
    }

    @Test
    public void testModifyPassword() throws Exception {
        String json = "{\"data\":[" +
                "{\"username\":\"admin\",\"password\":\"123456\",\"newPassword\":\"admin\",\"actor\":\"1\"}" +
                "]}";

        mockMvc.perform(post("/user/")
                .characterEncoding("UTF-8").header("method","modify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andDo(print());
    }
}
