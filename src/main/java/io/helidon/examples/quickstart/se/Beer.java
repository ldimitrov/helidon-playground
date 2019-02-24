package io.helidon.examples.quickstart.se;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Beer {
    private String id;
    private String name;
    private String type;
    private Integer strength;
}
