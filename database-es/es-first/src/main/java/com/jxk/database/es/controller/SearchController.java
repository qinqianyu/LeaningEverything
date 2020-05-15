package com.jxk.database.es.controller;


import com.jxk.database.es.domian.ResultJson;
import com.jxk.database.es.dos.Person;
import com.jxk.database.es.repository.PersonRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Description
 * author jxk
 * createTime 2020/5/11 10:01
 */
@Api(value = "人员Controller", tags = {"人员访问接口"})
@RequestMapping("/person")
@RestController
public class SearchController {
    private final PersonRepository personRepository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public SearchController(PersonRepository personRepository, ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.personRepository = personRepository;
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }


    @GetMapping("/add")
    @ApiOperation("添加一条信息")
    public ResultJson addInfo(@RequestParam(value = "id") Integer id,
                              @RequestParam(value = "name", defaultValue = "name") String name,
                              @RequestParam(value = "essay", defaultValue = "essay") String essay) {
        Person person = Person.builder().id(id).publishTime(LocalDateTime.now()).name(name).essay(essay).build();
        personRepository.save(person);
        return ResultJson.ok();
    }

    @GetMapping("/update")
    @ApiOperation("更新一条信息")
    public ResultJson updateInfo(@RequestParam(value = "id") String id,
                                 @RequestParam(value = "name", defaultValue = "name") String name,
                                 @RequestParam(value = "essay", defaultValue = "essay") String essay) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.doc(XContentFactory.jsonBuilder().startObject()
                .field("name", name)
                .field("essay", essay)
                .endObject());
        UpdateQuery updateQuery = new UpdateQueryBuilder()
                .withId(id)
                .withClass(Person.class)
                .withUpdateRequest(updateRequest)
                .build();
        UpdateResponse updateResponse = elasticsearchRestTemplate.update(updateQuery);

        return ResultJson.ok(updateResponse);
    }

}
