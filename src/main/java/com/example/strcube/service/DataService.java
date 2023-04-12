package com.example.strcube.service;

import com.example.strcube.DTO.DataDto;

import java.util.HashMap;
import java.util.List;

public interface DataService {
    List<List<Object>> sendData(String queryId,String type);
}
