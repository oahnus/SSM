package cn.edu.just.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextConfig {
    public final static String XML_PATH = "classpath:spring-mybatis.xml";

    private static ApplicationContext applicationContext;

    private ApplicationContextConfig(){}

    public static ApplicationContext getApplicationContext(){
        if(applicationContext==null){
            applicationContext = new ClassPathXmlApplicationContext(XML_PATH);
            return applicationContext;
        }else {
            return applicationContext;
        }
    }
}
