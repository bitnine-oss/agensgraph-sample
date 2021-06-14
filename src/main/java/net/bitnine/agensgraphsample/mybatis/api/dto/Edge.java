package net.bitnine.agensgraphsample.mybatis.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.bitnine.agensgraph.deps.org.json.simple.JSONObject;
import org.springframework.boot.jackson.JsonComponent;


public class Edge {

    @Setter
    @Getter
    @JsonComponent
    public static class CreateRequestDto {
        @JsonProperty("source")
        private String source;

        @JsonProperty("target")
        private String target;

        @JsonProperty("eLabel")
        private String eLabel;

        @JsonProperty("properties")
        private JSONObject properties;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class CreateResponseDto {
        private String graphId;
    }
}
