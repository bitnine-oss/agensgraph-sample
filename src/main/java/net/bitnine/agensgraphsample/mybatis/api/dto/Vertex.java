package net.bitnine.agensgraphsample.mybatis.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.bitnine.agensgraph.deps.org.json.simple.JSONObject;
import org.springframework.boot.jackson.JsonComponent;

public class Vertex {

    @Setter
    @Getter
    @JsonComponent

    public static class CreateRequestDto {

        @JsonProperty("vLabel")
        @Schema(description = "라벨", example = "swagger_default_label")
        private String vLabel;

        @Schema(description = "속성", example = "{\"swagger_default_prop\":\"yes\"")
        @JsonProperty("properties")
        private JSONObject properties;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class CreateResponseDto {
        private String graphId;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class ReadResponseDto {
        private JSONObject properties;
    }


}
