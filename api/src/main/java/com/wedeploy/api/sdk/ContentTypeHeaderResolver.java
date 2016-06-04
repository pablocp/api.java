package com.wedeploy.api.sdk;

/**
 * Content type resolver. From Jodd.
 */
class ContentTypeHeaderResolver {

	private static final String TSPECIALS = " ;()[]<>:,=?@\"\\";

	private final String type;
	private final String encoding;

	ContentTypeHeaderResolver(String fullContentType) {
		int charsetNdx = fullContentType.lastIndexOf("charset=");

		encoding = charsetNdx != -1 ? extractContentTypeValue(
			fullContentType, charsetNdx + 8) : null;

		type = extractContentTypeValue(fullContentType, 0);
	}

	private String extractContentTypeValue(String type, int startIndex) {

		// skip spaces

		while (startIndex < type.length() && type.charAt(startIndex) == ' ') {
			startIndex++;
		}

		if (startIndex >= type.length()) {
			return null;
		}

		int endIndex = startIndex;

		if (type.charAt(startIndex) == '"') {
			startIndex++;
			endIndex = type.indexOf('"', startIndex);

			if (endIndex == -1) {
				endIndex = type.length();
			}
		} else {
			while (endIndex < type.length() &&
				   (TSPECIALS.indexOf(type.charAt(endIndex)) < 0)) {

				endIndex++;
			}
		}

		return type.substring(startIndex, endIndex);
	}

	/**
	 * Returns content mime type.
	 */
	public String getMimeType() {
		return type;
	}

	/**
	 * Returns content encoding.
	 */
	public String getEncoding() {
		return encoding;
	}

}