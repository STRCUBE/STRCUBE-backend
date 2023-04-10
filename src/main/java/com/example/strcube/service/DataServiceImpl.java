package com.example.strcube.service;

import com.example.strcube.DTO.DataDto;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class DataServiceImpl implements DataService{
    String url = "jdbc:mysql://172.16.201.190:3306/STRCUBE?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false&createDatabaseIfNotExist=true";
    String username = "venkatesh"; // replace with your username
    String password = "KVrsmck@21"; // replace with your password
    @Override
    public List<DataDto> sendData() {
        List<DataDto> dataDtos=new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             Statement stmt2=conn.createStatement();) {
            ResultSet rs = stmt.executeQuery("select * from Summary");
            while(rs.next()){
                ResultSet rs2 = stmt2   .executeQuery("select * from GroupByMapping where Group_Id="+rs.getInt("Group_Id"));
                List<String> groupByAttributes=new ArrayList<>();
                while(rs2.next()){
                    groupByAttributes.add((rs2.getString(3)));
                }
                DataDto dataDto=DataDto.builder()
                        .queryId(rs.getInt("Query_Id"))
                        .aggregateFunction(rs.getString("Aggregate_Function"))
                        .factVariable(rs.getString("Fact_Variable"))
                        .groupByAttributes(groupByAttributes)
                        .result(rs.getDouble("Result"))
                        .build();
                dataDtos.add(dataDto);
//                System.out.print("QueryId "+rs.getInt("Query_Id"));
//                System.out.print("fact "+rs.getString("Fact_Variable"));
//                System.out.print("fun "+rs.getString("Aggregate_Function"));
//                System.out.print("gid "+rs.getInt("Group_Id"));
//                System.out.println("result "+rs.getDouble("Result"));
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return dataDtos;
    }

    @Override
    public List<List<Object>> sendGroupByData(String queryId) {
//        List<Object> objects=new ArrayList<>();
//        List<HashMap<String,Object>> list=new ArrayList<>();
//        try (Connection conn = DriverManager.getConnection(url, username, password);
//             Statement stmt = conn.createStatement()) {
//            ResultSet rs = stmt.executeQuery("select * from GroupByResultQueryId_"+queryId);
//            ResultSetMetaData rsMetaData=rs.getMetaData();
//            int count = rsMetaData.getColumnCount();
//
//            while(rs.next()){
//                HashMap<String,Object> map=new HashMap<>();
//                for(int i = 1; i<=count; i++) {
//                    map.put(rsMetaData.getColumnName(i),rs.getObject(i));
//                }
//                list.add(map);
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        return list;
        List<List<Object>> result=new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from GroupByResultQueryId_"+queryId);
            ResultSetMetaData rsMetaData=rs.getMetaData();
            int count = rsMetaData.getColumnCount();
            List<Object> header=new ArrayList<>();
            for(int i = 1; i<=count; i++) {
                header.add(rsMetaData.getColumnName(i));
            }
            result.add(header);
            while(rs.next()){
                List<Object> row=new ArrayList<>();
                for(int i = 1; i<=count; i++) {
                    row.add(rs.getObject(i));
                }
                result.add(row);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;

    }
}
