package com.example.owngoogle.utils;

public class TextHandler {

	private final int planTextLimit;

	public TextHandler(int planTextLimit) {
		this.planTextLimit = planTextLimit;
	}

	public String prepareText(String fullText, String query) {
		try {
			final String[] words = query.toLowerCase().split("[ ,]");
			int startWordIndex = -1;
			int endWordIndex = -1;

			int textStartIndex = 0;
			int textEndIndex = fullText.length() - 1;

			final String lowerCaseFullText = fullText.toLowerCase();

			for (String word : words) {
				final int index = lowerCaseFullText.indexOf(word);
				startWordIndex = index >=0 ? index : startWordIndex;
				endWordIndex = startWordIndex + word.length() - 1;
			}
			if (startWordIndex == -1 || endWordIndex == -1) {
				return fullText.substring(0, Math.min(fullText.length(), planTextLimit));
			}

			int shiftSize = (planTextLimit - (endWordIndex - startWordIndex + 1))/2;
			textStartIndex = Math.max(startWordIndex - shiftSize, 0);
			textEndIndex = Math.min(endWordIndex + shiftSize + (shiftSize - (startWordIndex - textStartIndex)), textEndIndex);
			return fullText.substring(textStartIndex, textEndIndex + 1);
		} catch (Exception e ) {
			return fullText.substring(0, Math.min(fullText.length(), planTextLimit));
		}
	}
}
