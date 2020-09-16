package com.example.owngoogle.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import static org.springframework.util.StringUtils.isEmpty;

@Service
public class SearchValidationServiceImpl implements ISearchValidationService {

	@Override
	public void validate(String query, int page, String sort) {
		Assert.isTrue(!isEmpty(query), "Query is empty");
		Assert.isTrue(page >=0 , "Incorrect page");
	}
}
