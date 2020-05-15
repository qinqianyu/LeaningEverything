package com.jxk.database.es.dos;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import java.time.LocalDateTime;

/**
 * @description:
 * @author: jxk
 * @create: 2020-03-31 15:26
 **/
@Data
@Builder
@Document(indexName = "person",shards = 3,replicas = 0,refreshInterval ="1s")
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @Field(type = FieldType.Integer)
    private Integer id;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "8yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime publishTime;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String essay;


    @Field(type = FieldType.Keyword)
    private String name;

}
















