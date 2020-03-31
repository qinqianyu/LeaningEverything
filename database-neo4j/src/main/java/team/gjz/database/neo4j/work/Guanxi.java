package team.gjz.database.neo4j.work;

import lombok.Data;

@Data
public class Guanxi {
    private int start;
    private int end;
    private int end2;
    private int end3;
    private int end4;
    private String startname;
    private String endname;
    private String end2name;
    private String end3name;
    private String end4name;

    public Guanxi(int start, int end, int end2, int end3, int end4, String startname, String endname, String end2name, String end3name, String end4name) {
        this.start = start;
        this.end = end;
        this.end2 = end2;
        this.end3 = end3;
        this.end4 = end4;
        this.startname = startname;
        this.endname = endname;
        this.end2name = end2name;
        this.end3name = end3name;
        this.end4name = end4name;
    }

    public Guanxi() {
    }
}
