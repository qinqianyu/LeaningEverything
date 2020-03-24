package team.gjz.database.redis.Json;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CpuCut {
    String time[];
    Integer value[];
}
