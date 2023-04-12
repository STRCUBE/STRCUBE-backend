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
    String url = "jdbc:mysql://localhost:3306/STRCUBE?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false&createDatabaseIfNotExist=true";
    String username = "hansal"; // replace with your username
    String password = "2017033800105146"; // replace with your password

    @Override
    public List<List<Object>> sendData(String queryId,String type) {
        List<List<Object>> result=new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {
            ResultSet rs;
            if(type.equals("logs"))
            rs= stmt.executeQuery("select * from QUERY_LOG_"+queryId);
            else
            rs = stmt.executeQuery("select * from QUERY_RESULT_"+queryId);
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
