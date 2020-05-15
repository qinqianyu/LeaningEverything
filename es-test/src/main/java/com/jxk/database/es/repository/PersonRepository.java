package com.jxk.database.es.repository;

import com.jxk.database.es.dos.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @description:
 * @author: jxk
 * @create: 2020-03-31 15:25
 **/

public interface PersonRepository extends ElasticsearchRepository<Person, Long> {
}