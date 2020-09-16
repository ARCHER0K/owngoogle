package com.example.owngoogle.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IndexValidationServiceImplUnitTest {

	private IndexValidationServiceImpl service = new IndexValidationServiceImpl();

	@Test
	void testIsPassed() {
		service.validate("www.gmail.com", 1);
	}

	@Test
	void throwExceptionWhenDeepnessIsNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.validate("www.gmail.com", null);
		});
	}

	@Test
	void throwExceptionWhenQueryIsNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.validate(null, 1);
		});
	}

	@Test
	void throwExceptionWhenQueryIsEmpty() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.validate("", 1);
		});
	}

	@Test
	void throwExceptionWhenDeepnessIsZero() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.validate("www.gmail.com", 0);
		});
	}

	@Test
	void throwExceptionWhenDeepnessIsNegative() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.validate("www.gmail.com", -4);
		});
	}

	@Test
	void throwExceptionWhenQueryIsNotSite() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.validate("wwwgmailcom", 1);
		});
	}

	@Test
	void throwExceptionWhenSiteDoesNotContainWww() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.validate("gmail.com", 1);
		});
	}
}