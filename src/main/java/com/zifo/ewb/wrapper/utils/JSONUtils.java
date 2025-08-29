package com.zifo.ewb.wrapper.utils;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.zifo.ewb.wrapper.constants.JSONConstants;
import com.zifo.ewb.wrapper.exceptions.ReadException;


/**
 * Utility class provide common JSON related functions
 * 
 * @author zifo
 *
 */
public final class JSONUtils {

	/**
	 * 
	 */
	private static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * Logger instance to write logs
	 */
	private static final Logger LOGGER = LogManager.getLogger(JSONUtils.class);

	/**
	 * Private constructor to prevent object creation for utility class.
	 */
	private JSONUtils() {

	}

	/**
	 * This method convert object to string
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static String convertToString(final Object object) {

		String value;
		try {
			value = MAPPER.writeValueAsString(object);
		} catch (final IOException ioExcep) {
			value = StringUtils.EMPTY;
			LOGGER.error(ioExcep.getMessage());
		}
		return value;

	}

	/**
	 * This method convert string to JsonNode
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static JsonNode convertToJson(final String data) {

		JsonNode node = null;
		try {
			node = MAPPER.readTree(data);
		} catch (final IOException e) {
			LOGGER.error(e.getMessage());
		} 
		return node;
	}

	/**
	 * @param value
	 * @return
	 */
	public static String checkForNull(final String value) {
		String checkedValue;
		if (value == null) {
			checkedValue = StringUtils.EMPTY;
		} else {
			checkedValue = value;
		}
		return checkedValue;
	}

	/**
	 * @param value
	 * @return
	 */
	public static int checkForNullOnInt(final String value) {
		int checkedValue;
		if (value == null || value.equals("")) {
			checkedValue = 0;
		} else {
			checkedValue = Integer.valueOf(value);
		}
		return checkedValue;
	}

	/**
	 * Method to generate json for retrieve table structure
	 * 
	 * @param value
	 * @return
	 * @throws ReadException
	 */

		public static String createJSONToReadTableStructure(final String tableName) {
		    return "{"
		        + "\"batch-request\": ["
		        + "    { \"version\": \"1.0\" },"
		        + "    ["
		        + "        { \"api-id\": \"table.structure\", \"api-version\": \"1.0\" },"
		        + "        {"
		        + "            \"data\": {"
		        + "                \"tables\": [\"" + tableName + "\"],"
		        + "                \"options\": { \"itemNames\": \"all\" }"
		        + "            }"
		        + "        }"
		        + "    ]"
		        + "]"
		        + "}";
		}

	public static String createJSONToClearTable(final String tableName) {
		return "{\r\n" + "    \"batch-request\": [\r\n" + "        {\r\n" + "            \"version\": \"1.0\"\r\n" + "        },\r\n" + "        [\r\n"
				+ "            {\r\n" + "                \"api-id\": \"table.data.clear\",\r\n" + "                \"api-version\": \"1.0\"\r\n"
				+ "            },\r\n" + "            {\r\n" + "                \"data\": {\r\n" + "                    \"ranges\": [\r\n"
				+ "                        {\r\n" + "                            \"table\": \"" + tableName + "\"\r\n" + "                        }\r\n"
				+ "                    ]\r\n" + "                }\r\n" + "            }\r\n" + "        ]\r\n" + "    ]\r\n" + "}";

	}
	/**
	 * Method to generate json for retrieve table data
	 * 
	 * @param value
	 * @return
	 * @throws ReadException
	 */
	public static String createJSONToReadTableData(final String tableName) {

		return "{    \"batch-request\": [        {            \"version\": \"1.0\"        },        [            {                \"api-id\": \"table.data\",               \"api-version\": \"1.0\"            },            {                \"data\": {                    \"queries\": [                        {                           \"table\": \""
				+ tableName
				+ "\",                            \"range\": \"\"                        }                    ]                }            }        ]    ]}";
	}

	public static String createJSONToChangeTableData(final String tableName, final String nonDataDimensionName,
			final String count, final String insertAt) {

		return "{  \r\n" + "   \"batch-request\":[  \r\n" + "      {  \r\n" + "         \"version\":\"1.0\",\r\n"
				+ "         \"options\":{  \r\n" + "            \"force\":true\r\n" + "         }\r\n" + "      },\r\n"
				+ "      [  \r\n" + "         {  \r\n" + "            \"api-id\":\"table.structure.insert\",\r\n"
				+ "            \"api-version\":\"1.0\"\r\n" + "         },\r\n" + "         {  \r\n"
				+ "            \"data\":{  \r\n" + "               \"tables\":{  \r\n" + "                  \""
				+ tableName + "\":{  \r\n" + "                     \"" + nonDataDimensionName + "\":{  \r\n"
				+ "                        \"itemCount\":" + count + ",\r\n" + "                        \"insertAt\":"
				+ insertAt + "}\r\n" + "                  }\r\n" + "               }\r\n" + "            }\r\n"
				+ "         }\r\n" + "      ]\r\n" + "   ]\r\n" + "}";
	}

	/**
	 * Method that read data from json node
	 * 
	 * @param node
	 * @param value
	 * @return
	 * @throws ReadException
	 * @throws ErrorException
	 */
	public static String readJson(final JsonNode node, final String value) throws ReadException {

		JsonNode path;
		path = node.path(value);
		String textValue;
		if (!path.path(JSONConstants.ERROR).isMissingNode()) {
			throw new ReadException("Please provide valid" + value + " value");
		} else if (!path.path(JSONConstants.DATEANDTIME).isMissingNode()) {
			textValue = path.path(JSONConstants.DATEANDTIME).asText();
		} else if (!path.path(JSONConstants.NUMBER).isMissingNode()) {
			textValue = path.path(JSONConstants.NUMBER).asText();
		} else if (!path.path(JSONConstants.HYPERLINK).isMissingNode()) {
			textValue = path.path(JSONConstants.HYPERLINK).asText();
		} else if (!path.path(JSONConstants.STRING).isMissingNode()) {
			textValue = path.path(JSONConstants.STRING).asText();
		} else {
			textValue = StringUtils.EMPTY;
		}

		return textValue;

	}

	/**
	 * @param obj
	 * @return
	 */
	public static String getString(final Object obj) {
		return obj == null ? StringUtils.EMPTY : obj.toString();
	}
}
