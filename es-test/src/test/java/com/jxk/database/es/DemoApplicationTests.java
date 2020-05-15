package com.jxk.database.es; /**
 * @description:
 * @author: jxk
 * @create: 2020-03-31 09:47
 **/

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jxk.database.es.dos.Person;
import com.jxk.database.es.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void contextLoads() {
        Person person = Person.builder().id(15L).publishTime(LocalDateTime.now()).build();
        personRepository.save(person);
    }

    @Test
    public void test() throws JsonProcessingException {
        Person person = Person.builder().id(15L).publishTime(LocalDateTime.now()).build();
        System.out.println(person);
    }

}