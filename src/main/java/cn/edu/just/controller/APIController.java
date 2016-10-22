package cn.edu.just.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;

/**
 * Created by oahnus on 2016/10/22.
 */
@Controller
@RequestMapping("/")
@CrossOrigin(maxAge = 3600)
public class APIController {

    @RequestMapping
    @ResponseBody
    public String execute(){
        String apiString = "";
        Resource resource = new ClassPathResource("api.txt");
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream()));
            String line = "";
            while((line=reader.readLine())!=null){
                apiString = apiString+line+"\n";
            }
        } catch (IOException e) {
//            e.printStackTrace();
            return "API文件丢失";
        }
        return apiString;
    }
}
