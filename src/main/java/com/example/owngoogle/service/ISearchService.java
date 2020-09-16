package com.example.owngoogle.service;

import com.example.owngoogle.model.ResultModel;

public interface ISearchService {

	ResultModel find(String query, int page, String sort);
}
