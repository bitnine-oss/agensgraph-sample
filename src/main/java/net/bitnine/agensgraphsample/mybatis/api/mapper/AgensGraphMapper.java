package net.bitnine.agensgraphsample.mybatis.api.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface AgensGraphMapper {

    public void setGraphPath();
    
    public String createVertex(HashMap<String, Object> param);

    public String readVertexId(String param);

    public String updateVertex(HashMap<String, Object> param);

    public int deleteVertex(String param);

    public String createEdge(HashMap<String, Object> param);

    public String readEdgeId(String param);

    public String updateEdge(HashMap<String, Object> param);

    public int deleteEdge(String param);

    public List<HashMap<String, Object>> readVertexAll();

    public List<HashMap<String, Object>> readEdgeAll();

    public String readGraphId(String param);

    public String readVertexLabel(String param);
}
