package net.bitnine.agensgraphsample.mybatis.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.bitnine.agensgraph.deps.org.json.simple.JSONObject;


public class Common {

    @Setter
    @Getter
    @AllArgsConstructor
    public static class ReadPropertyResponseDto {
        private JSONObject properties;
    }
}

