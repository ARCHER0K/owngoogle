package com.example.owngoogle.service;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.regex.Pattern;

import static org.springframework.util.StringUtils.isEmpty;

@Component
public class IndexValidationServiceImpl implements IIndexValidationService {

	private static final Pattern URL_PATTERN = Pattern.compile("(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,})");

	@Override
	public void validate(String query, Integer deepness) {
		Assert.isTrue(deepness != null && deepness > 0 && deepness <=3, "Incorrect deepness value");
		Assert.isTrue(!isEmpty(query) && URL_PATTERN.matcher(query).matches(), "URL is not valid");
	}
}
