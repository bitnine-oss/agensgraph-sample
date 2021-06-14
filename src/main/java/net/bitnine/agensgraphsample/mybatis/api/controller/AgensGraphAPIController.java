package net.bitnine.agensgraphsample.mybatis.api.controller;

import net.bitnine.agensgraph.deps.org.json.simple.JSONArray;
import net.bitnine.agensgraph.deps.org.json.simple.parser.ParseException;
import net.bitnine.agensgraphsample.mybatis.api.service.AgensGraphService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;


@RestController
public class AgensGraphAPIController {

    private final AgensGraphService agensGraphService;

    public AgensGraphAPIController(AgensGraphService agensGraphService) {
        this.agensGraphService = agensGraphService;
    }


    @GetMapping("/api/info/restTest")
    public String restTest(@RequestParam String str) {
        return "test";
    }


    //Create vertex
    @PostMapping(path = "/api/create/vertex")
    public String executeVertexCreate(
            @RequestBody String requestbody, HttpServletResponse response) throws SQLException, ParseException {
        return agensGraphService.executeVertexCreate(requestbody);
    }

    //Read vertex
    @GetMapping(path = "/api/read/vertex/{graphId}")
    public String executeReadVertexId(@PathVariable String graphId) throws SQLException {
        return agensGraphService.executeReadVertexId(graphId);
    }

    //Update vertex
    @PutMapping(path = "/api/update/vertex/{graphId}")
    public String executeVertexUpdate(@PathVariable String graphId, @RequestBody String requestbody) throws SQLException, ParseException {
        return agensGraphService.executeVertexUpdate(graphId, requestbody);
    }

    //Delete vertex
    @DeleteMapping(path = "/api/delete/vertex/{graphId}")
    public String executeVertexDelete(@PathVariable String graphId) throws SQLException, ParseException, ClassNotFoundException {
        return agensGraphService.executeVertexDelete(graphId);
    }


    //Create Edge
    @PostMapping(path = "/api/create/edge")
    public String executeEdgeCreate(@RequestBody String requestbody) throws SQLException, ParseException {
        return agensGraphService.executeEdgeCreate(requestbody);
    }

    //Read edge
    @GetMapping(path = "/api/read/edge/{graphId}")
    public String executeReadEdgeId(@PathVariable String graphId) throws SQLException {
        return agensGraphService.executeReadEdgeId(graphId);
    }

    //Update edge
    @PutMapping(path = "/api/update/edge/{graphId}")
    public String executeEdgeUpdate(@PathVariable String graphId, @RequestBody String requestbody) throws SQLException, ParseException {
        return agensGraphService.executeEdgeUpdate(graphId, requestbody);
    }

    //Delete edge
    @DeleteMapping(path = "/api/delete/edge/{graphId}")
    public String executeEdgeDelete(@PathVariable String graphId) throws SQLException{
        return agensGraphService.executeEdgeDelete(graphId);
    }


    //Read label
    @GetMapping(path = "/api/readlabel/{graphId}")
    public String executeReadGraphLabel(@PathVariable String graphId) throws SQLException {
        return agensGraphService.executeReadVertexLabel(graphId);
    }

    //Read All Vertex
    @GetMapping(path = "/api/read/vertex/all")
    public JSONArray executeReadVertexAll() throws SQLException {
        return agensGraphService.executeReadVertexAll();
    }

    //Read All Edge
    @GetMapping(path = "/api/read/edge/all")
    public JSONArray executeReadEdgeAll() throws SQLException {
        return agensGraphService.executeReadEdgeAll();
    }

    //Read Vertex,Edge
    @GetMapping(path = "/api/read/{graphId}")
    public String executeReadGraphid(@PathVariable String graphId) throws SQLException {
        return agensGraphService.executeReadGraphid(graphId);
    }

}
