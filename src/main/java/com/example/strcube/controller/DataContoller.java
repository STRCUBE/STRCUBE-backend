package com.example.strcube.controller;

import com.example.strcube.DTO.DataDto;
import com.example.strcube.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class DataContoller {
    private final DataService dataService;
    @GetMapping("get-logs")
    public List<List<Object>> sendLogs(@RequestParam("queryId") String queryId){
        return dataService.sendData(queryId,"logs");
    }   @GetMapping("get-data")
    public List<List<Object>> sendData(@RequestParam("queryId") String queryId){
        return dataService.sendData(queryId,"data");
    }  @GetMapping("get-queries")
    public List<List<Object>> sendData(){
        return dataService.sendData("q1","queries");
    }
}
