package team.gjz.database.redis.Json.beans;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CpuCut {
    String time[];
    Integer value[];
}
