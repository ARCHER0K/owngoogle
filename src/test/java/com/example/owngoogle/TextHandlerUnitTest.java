package com.example.owngoogle;


import com.example.owngoogle.utils.TextHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TextHandlerUnitTest {

	private TextHandler textHandler = new TextHandler(10);

	@Test
	void returnStringFromStart() {
		final String result = textHandler.prepareText("asdfg zxcvb", "zxc asd");
		Assertions.assertEquals("asdfg zxc", result);
	}

	@Test
	void returnStringFromMiddle() {
		final String result = textHandler.prepareText("qwert asdfg zxcvb", "asdfg");
		Assertions.assertEquals("t asdfg z", result);
	}

	@Test
	void returnStringFromEnd() {
		final String result = textHandler.prepareText("qwert asdfg zxcvb", "zxcvb");
		Assertions.assertEquals("g zxcvb", result);
	}

	@Test
	void returnStringFromEndWithExtra() {
		final String result = textHandler.prepareText("qwert asdfg zxcvb", "asd");
		Assertions.assertEquals("rt asdfg ", result);
	}

	@Test
	void returnWholeString() {
		final String result = textHandler.prepareText("asd", "asd");
		Assertions.assertEquals("asd", result);
	}

	@Test
	void returnEmptyResult() {
		final String result = textHandler.prepareText("qwert asdfg zxcvb", "asdqwe");
		Assertions.assertNull(result);
	}
}