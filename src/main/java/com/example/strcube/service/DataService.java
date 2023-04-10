package com.example.strcube.service;

import com.example.strcube.DTO.DataDto;

import java.util.HashMap;
import java.util.List;

public interface DataService {
    List<DataDto> sendData();

    List<List<Object>> sendGroupByData(String queryId);
}
