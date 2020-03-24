package team.gjz.database.redis.conectTest;

import lombok.Data;

import java.io.Serializable;

@Data
public class Student  implements Serializable {
    private static final long serialVersionUID = -5809782578272943999L;
    private Integer sid;
    private String sname;
    private String ssex;
    private Integer sage;

    public Student(Integer sid, String sname, String ssex, Integer sage) {
        this.sid = sid;
        this.sname = sname;
        this.ssex = ssex;
        this.sage = sage;
    }

}
