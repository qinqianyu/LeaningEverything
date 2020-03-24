package team.gjz.database.redis.Json.beans;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: jxk
 * @create: 2020-03-11 16:34
 **/
@Data
@Builder
public class Guize implements Serializable {
    private static final long serialVersionUID = 5531689L;
    private String id;
    private String code;
    private List<List<String>> na;
    private List<List<String>> po;
}
