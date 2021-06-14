package net.bitnine.agensgraphsample.mybatis.api.service;

import net.bitnine.agensgraph.deps.org.json.simple.JSONArray;
import net.bitnine.agensgraph.deps.org.json.simple.JSONObject;
import net.bitnine.agensgraph.deps.org.json.simple.parser.JSONParser;
import net.bitnine.agensgraphsample.mybatis.api.mapper.AgensGraphMapper;
import net.bitnine.agensgraphsample.mybatis.api.util.AgensJSONParse;
import net.bitnine.agensgraphsample.mybatis.helper.ConnectionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Service
public class AgensGraphService {

    @Autowired
    DataSource dataSource;

    @Autowired
    AgensGraphMapper agensGraphMapper;

    public Connection getConenction() {
        Connection con = null;
        try {
            con = ConnectionHelper.createConnection(dataSource);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return con;
    }

    //create vertex
    public String executeVertexCreate(String requestbody) throws net.bitnine.agensgraph.deps.org.json.simple.parser.ParseException, SQLException {
        String graphid = "";
        agensGraphMapper.setGraphPath();
       // Connection con = getConenction();
        try {
            //  ResultSet resultSet = con.createStatement().executeQuery(AgensJSONParse.createVertexCypher(requestbody));
            //resultSet.next();
            //graphid = resultSet.getString(1);
            //resultSet.close();
            //resultSet.close();


            // Changed : javaconnection -> mybatis
            // properties : property form(ex. {"property1":"value1", "property2":"value2"...}
            HashMap<String, Object> paramMap = AgensJSONParse.createVertex(requestbody);

            // If system is expand, we have to process exception for graphid is null
            graphid = agensGraphMapper.createVertex(paramMap) ;

            System.out.println("created vertex : graph id is : " + graphid);

        //    return graphid;
        } catch(Exception e){
            e.printStackTrace();
        }
//        } catch (PSQLException e) {
//            e.printStackTrace();
//        }
//        catch (NullPointerException e) {
//            e.printStackTrace();
//        } finally {
//            if (con != null) {
//                con.close();
//            }
//        }
        return graphid;
    }

    //Read vertex
    public String executeReadVertexId(String requestparam) throws SQLException {
        String graphid = "";
        agensGraphMapper.setGraphPath();

        try {
            graphid = agensGraphMapper.readVertexId(requestparam);
        } catch(Exception e){
            e.printStackTrace();
        }

        return graphid;
    }

    //Update vertex
    public String executeVertexUpdate(String requestrapam, String requestbody) throws SQLException, net.bitnine.agensgraph.deps.org.json.simple.parser.ParseException {

        String graphid = "";
        agensGraphMapper.setGraphPath();
        try {
            // requestparam : vertex id, requestbody : properties(Ex. "{properties" : {"property1": "value1", property2":"value2"...}}"
            HashMap<String, Object> paramMap = AgensJSONParse.updateVertex(requestrapam, requestbody);
            String queryStr = "";
            graphid = agensGraphMapper.updateVertex(paramMap);
        } catch(Exception e){
            e.printStackTrace();
        }
        return graphid;
    }

    //Delete vertex
    public String executeVertexDelete(String requestparam) throws SQLException, ClassNotFoundException {
        int affected = 0;
        agensGraphMapper.setGraphPath();
        try {
            // requestparam : graphid
            affected = agensGraphMapper.deleteVertex(requestparam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.toString(affected);
    }


    //create edge
    public String executeEdgeCreate(String requestbody) throws net.bitnine.agensgraph.deps.org.json.simple.parser.ParseException, SQLException {

        String graphid = "";
        agensGraphMapper.setGraphPath();

        try {
            // requestbody : {"source":"4.6","target":"13.1","eLabel":"eg","properties":{"a":"aaaa"}}
            HashMap<String, Object> paramMap = AgensJSONParse.createEdge(requestbody);
            graphid = agensGraphMapper.createEdge(paramMap) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return graphid;
    }

    //Read edge
    public String executeReadEdgeId(String requestparam) throws SQLException {
        String edgeId = "";
        agensGraphMapper.setGraphPath();
        try {
            edgeId = agensGraphMapper.readEdgeId(requestparam);
        } catch(Exception e){
            e.printStackTrace();
        }
        return edgeId;
    }

    //update edge
    public String executeEdgeUpdate(String requestrapam, String requestbody) throws SQLException, net.bitnine.agensgraph.deps.org.json.simple.parser.ParseException {
        agensGraphMapper.setGraphPath();
        String graphid = "";

        try {
            // requestrapam : edgeid, requestbody : properties(Ex. {"properties":{"property1":"value1", "property2":"value2"....}})
            HashMap<String, Object> paramMap = AgensJSONParse.updateEdge(requestrapam, requestbody);
            graphid = agensGraphMapper.updateEdge(paramMap);
        } catch(Exception e){
            e.printStackTrace();
        }
        return graphid;
    }

    //Delete edge
    public String executeEdgeDelete(String requestparam) throws SQLException {
        int affected = 0;
        agensGraphMapper.setGraphPath();

        try{
            affected = agensGraphMapper.deleteEdge(requestparam);
        } catch(Exception e){
            e.printStackTrace();
        }
        return Integer.toString(affected);
    }


    //read all vertex
    public JSONArray executeReadVertexAll() throws SQLException {

        agensGraphMapper.setGraphPath();
        ResultSet rs;
        JSONObject returnJO = new JSONObject();
        JSONArray returnJA = new JSONArray();
        JSONParser parser = new JSONParser();

        try {
            List<HashMap<String, Object>> rtnList = agensGraphMapper.readVertexAll();

            for (int i = 0, size = rtnList.size(); i < size; i++) {
                returnJO = new JSONObject();

                returnJO.put("vLabel", rtnList.get(i).get("label").toString().replace("\"", ""));
                returnJO.put("id", rtnList.get(i).get("id").toString());
                returnJO.put("properties", parser.parse(rtnList.get(i).get("properties").toString()));
                returnJA.add(returnJO);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return returnJA;
    }

    //read all edge
    public JSONArray executeReadEdgeAll() throws SQLException {
        agensGraphMapper.setGraphPath();
        ResultSet rs;
        JSONObject returnJO = new JSONObject();
        JSONArray returnJA = new JSONArray();
        JSONParser parser = new JSONParser();

        try {
            List<HashMap<String, Object>> rtnList = agensGraphMapper.readEdgeAll();
            for (int i = 0, size = rtnList.size(); i < size; i++) {
                returnJO = new JSONObject();
                returnJO.put("vLabel", rtnList.get(i).get("edgelabel").toString().replace("\"", ""));
                returnJO.put("id", rtnList.get(i).get("edgeid").toString());
                returnJO.put("start", rtnList.get(i).get("start").toString());
                returnJO.put("end", rtnList.get(i).get("end").toString());
                returnJO.put("properties", parser.parse(rtnList.get(i).get("properties").toString()));

                returnJA.add(returnJO);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return returnJA;
    }

    //read vertex,edge
    public String executeReadGraphid(String requestparam) throws SQLException {
        String rtnStr = "";
        agensGraphMapper.setGraphPath();
        try {
            rtnStr = agensGraphMapper.readGraphId(requestparam);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnStr;
    }

    //Read vertex lavel
    public String executeReadVertexLabel(String requestparam) throws SQLException {
        String edgeLabel = "";
        agensGraphMapper.setGraphPath();
        try {
            edgeLabel = agensGraphMapper.readVertexLabel(requestparam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return edgeLabel;
    }


}


