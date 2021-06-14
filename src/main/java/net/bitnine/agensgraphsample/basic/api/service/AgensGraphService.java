package net.bitnine.agensgraphsample.basic.api.service;

import net.bitnine.agensgraph.deps.org.json.simple.JSONArray;
import net.bitnine.agensgraph.deps.org.json.simple.JSONObject;
import net.bitnine.agensgraph.deps.org.json.simple.parser.JSONParser;
import net.bitnine.agensgraph.deps.org.json.simple.parser.ParseException;
import net.bitnine.agensgraphsample.basic.api.util.AgensJSONParse;
import net.bitnine.agensgraphsample.basic.helper.ConnectionHelper;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class AgensGraphService {

    private ResultSet rs;
    private String graphid;

    private final DataSource dataSource;
    private Connection con;
    private PreparedStatement pstmt;

    AgensJSONParse agensJSONParse;
    JSONObject returnJO;
    JSONArray returnJA;
    JSONParser parser;

    private JSONObject jsonObject;
    private JSONParser jsonParser;


    @Autowired
    ConnectionHelper connectionHelper;

    public AgensGraphService(DataSource dataSource) {
        graphid = "";
        this.dataSource = dataSource;
        agensJSONParse = new AgensJSONParse();
        jsonParser = new JSONParser();
        connectionHelper = new ConnectionHelper(dataSource);
        parser = new JSONParser();
    }

    @PostConstruct
    public void init() {
        con = connectionHelper.getConnection();
    }

    public void closeResource() throws SQLException {
        if (rs != null) {
            rs.close();
        }
//        if (con != null) {
//            con.close();
//        }
        if (pstmt != null) {
            pstmt.close();
        }
    }
    //create vertex
    public String executeVertexCreate(String requestbody) throws SQLException {
        try {
            jsonObject = (JSONObject) jsonParser.parse(requestbody);
            pstmt = con.prepareStatement("create (a:" + jsonObject.get("vLabel") + " { (?) }) return id(a)");

            pstmt.setString(1,agensJSONParse.adjustPropertyQuote(requestbody));


            System.out.println(agensJSONParse.adjustPropertyQuote(requestbody));
            System.out.println("create (a:" + jsonObject.get("vLabel") + " { (?) }) return id(a)");
            rs = pstmt.executeQuery();

            if (rs.next()) ;
                return rs.getString(1);
        } catch (PSQLException | NullPointerException | ParseException e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
       throw new SQLException();
    }

    //Read vertex
    public String executeReadVertexId(String requestparam) throws SQLException {

        try {
            pstmt = con.prepareStatement("match (a) where id(a) = (?)::graphid return properties(a);");
            pstmt.setString(1, requestparam);
            rs = pstmt.executeQuery();
            if (rs.next())
                return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        throw new SQLException();
    }

    //Update vertex
    public String executeVertexUpdate(String requestparam, String requestbody) throws SQLException, ParseException {

        pstmt = con.prepareStatement("match (n) where id(n)="+requestparam+" set n = { (?) } return id(n)");
        pstmt.setString(1,  agensJSONParse.adjustPropertyQuote(requestbody));
        rs = pstmt.executeQuery();
        try {
            if (rs.next()) ;
                return rs.getString(1);
        } catch (PSQLException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        throw new SQLException();
    }

    //Delete vertex
    public String executeVertexDelete(String requestparam) throws SQLException, ClassNotFoundException {
        int affected = 0;
        try {
            pstmt = con.prepareStatement("match (a) where id(a) = (?)::graphid detach delete (a); ");
            pstmt.setString(1, requestparam);
            affected = pstmt.executeUpdate();
            return Integer.toString(affected);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        throw new SQLException();
    }

    //create edge
    public String executeEdgeCreate(String requestbody) throws ParseException, SQLException {
        try {
            jsonObject = (JSONObject) jsonParser.parse(requestbody);
            pstmt = con.prepareStatement( "match (a),(b) "
                    + "where id(a) = "
                    + jsonObject.get("source")
                    + " and id(b) = "
                    + jsonObject.get("target")
                    + "create (a)-[c:"
                    + jsonObject.get("eLabel")
                    + "{ (?) }]->(b) return id(c)" );
            pstmt.setString(1, agensJSONParse.adjustPropertyQuote(requestbody));
            rs = pstmt.executeQuery();

            if (rs.next()) ;
                return rs.getString(1);
        } catch (PSQLException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        throw new SQLException();
    }

    //Read edge
    public String executeReadEdgeId(String requestparam) throws SQLException {
        try {

            pstmt = con.prepareStatement("match (b)-[a]-(c) where id(a) = (?)::graphid return properties(a);");
            pstmt.setString(1, requestparam);
            rs = pstmt.executeQuery();
            if (rs.next())
                return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        throw new SQLException();
    }

    //update edge
    public String executeEdgeUpdate(String requestparam, String requestbody) throws SQLException, ParseException {
        try {

            pstmt = con.prepareStatement("match ()-[n]-() where id(n)=" + requestparam + " set n = { (?) } return id(n)");
            pstmt.setString(1,agensJSONParse.adjustPropertyQuote(requestbody));
            rs = pstmt.executeQuery();
            if (rs.next())
                return rs.getString(1);
        } catch (PSQLException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        throw new SQLException();
    }

    //Delete edge
    public String executeEdgeDelete(String requestparam) throws SQLException {
        int affected = 0;
        try {

            pstmt = con.prepareStatement("match ()-[a]-() where id(a) = (?)::graphid detach delete a");
            pstmt.setString(1, requestparam);
            affected = pstmt.executeUpdate();
            return Integer.toString(affected);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        throw new SQLException();
    }

    //read all vertex
    public JSONArray executeReadVertexAll() throws SQLException {
        try {
            rs = con.prepareStatement("match (a) return label(a),id(a),properties(a)").executeQuery();
            while (rs.next()) {
                returnJO = new JSONObject();
                returnJA = new JSONArray();
                returnJO.put("vLabel", rs.getString(1));
                returnJO.put("id", rs.getString(2));
                returnJO.put("properties", parser.parse(rs.getString(3)));
                returnJA.add(returnJO);
            }
            return returnJA;
        } catch (SQLException | ParseException e) {
            e.printStackTrace();

        } finally {
            closeResource();
        }
        throw new SQLException();
    }

    //read all edge
    public JSONArray executeReadEdgeAll() throws SQLException {
        try {

            rs = con.prepareStatement("match (a)-[b]->(c) return label(b),id(b),id(a) as start,id(c) as end,properties(b);").executeQuery();
            while (rs.next()) {
                returnJO = new JSONObject();
                returnJA = new JSONArray();
                returnJO.put("vLabel", rs.getString(1));
                returnJO.put("id", rs.getString(2));
                returnJO.put("start", rs.getString(3));
                returnJO.put("end", rs.getString(4));
                returnJO.put("properties", parser.parse(rs.getString(5)));
                returnJA.add(returnJO);
            }
            return returnJA;
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        throw new SQLException();
    }

    //read vertex,edge
    public String executeReadGraphid(String requestparam) throws SQLException {
        try {
            pstmt = con.prepareStatement(
                    "match (a) where id(a) = (?)::graphid return properties(a);");
            pstmt.setString(1, requestparam);
            rs = pstmt.executeQuery();
            if (rs.next())
                return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        throw new SQLException();
    }

    //Read vertex lavel
    public String executeReadVertexLabel(String requestparam) throws SQLException {
        try {
            pstmt = con.prepareStatement(
                    "match (a) where id(a) = (?)::graphid return label(a);");
            pstmt.setString(1, requestparam);
            rs = pstmt.executeQuery();
            if (rs.next())
                return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
        throw new SQLException();
    }
}


