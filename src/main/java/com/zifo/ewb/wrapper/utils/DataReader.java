package com.zifo.ewb.wrapper.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.zifo.ewb.wrapper.constants.JSONConstants;


/**
 * Utility class to provide methods that helps to read the data from JSON
 *
 * @author zifo
 *
 */
@Service
//@NoArgsConstructor
public class DataReader {

	private static final Logger LOGGER = LogManager.getLogger(DataReader.class);

	private static String dataDimensionName;
	
	private DataReader() {
		
	}
	public static String getDataDimensionName() {
		return dataDimensionName;
	}

	public static void setDataDimensionName(final String dataDimensionName) {
		DataReader.dataDimensionName = dataDimensionName;
	}

	/**
	 * Method to read dimension details of the spreadsheet table
	 *
	 * @param json
	 * @param table
	 * @return
	 */
	public static Map<String, List<String>> readDimensions(final String json, final String table) {
		final Map<String, List<String>> map = new LinkedHashMap<>();

		final ObjectMapper mapper = new ObjectMapper();
		try {
			final JsonNode root = mapper.readTree(json);
			final JsonNode apiResponse = root.path(JSONConstants.BATCH_RESPONSE).path(JSONConstants.API_RESPONSES);

			for (final JsonNode responses : apiResponse) {
				final String dataDimensionName = responses.path(JSONConstants.TABLES).path(table)
						.path(JSONConstants.DATA_DIMENSION).getTextValue();
				DataReader.setDataDimensionName(dataDimensionName);
				final JsonNode node = responses.path(JSONConstants.TABLES).path(table);
				for (final JsonNode dataDimensions : node.path(JSONConstants.DIMENSIONS)) {
					final List<String> list = new ArrayList<>();
					final String name = dataDimensions.path("name").getTextValue();
					for (final JsonNode values : dataDimensions.path(JSONConstants.ITEM_NAMES)) {
						final String value = values.asText();
						list.add(value);
					}
					map.put(name, list);
				}

			}

		} catch (final IOException ioException) {
			LOGGER.error(ioException.getMessage(), ioException);
		}
		return map;
	}

	/**
	 * Method that checks the given value is present as node in given json
	 *
	 * @param node
	 * @param value
	 * @return
	 */
	public static boolean checkJson(final JsonNode node, final String value) {

		return node.path(value).isMissingNode();
	}

}
