package com.zifo.ewb.wrapper.populatedata.helper;

import java.text.MessageFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.idbs.ewb.spreadsheet.pojo.Options;
import com.idbs.ewb.spreadsheet.pojo.ResponseDTO;
import com.zifo.ewb.wrapper.constants.MessageConstants;

/**
 * Class that contains messages to be seen by users
 * @author zifo
 *
 */
public final class Messenger {
	
	/**
	 * Logger instance to write logs 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Messenger.class);
	
	/**
	 * Private constructor to prevent object creation for utility class.
	 */
	private Messenger() {
	}
	
	/**
	 * Method that create failure response with the requested message
	 * @param longMessage
	 * @return
	 */
	public static ResponseDTO generateFailureResponse(final String longMessage,final String shortMessage) {
		final ResponseDTO response = new ResponseDTO();
		LOGGER.info(MessageFormat.format("Long {0}", longMessage));
		response.setLongMessage(longMessage);
		response.setShortMessage(shortMessage);
		response.setStatus(MessageConstants.FAILURE);
		final Options options = new Options();
		options.setMessageType(MessageConstants.OK_DIALOG);
		response.setOptions(options);
		LOGGER.info(response.getLongMessage());
		return response;
	}
	/**
	 *  Method that create success response with the requested message
	 * @param message
	 * @return
	 */
	public static  ResponseDTO generateSuccessResponse(final String longMessage, final String shortMessage) {
		final ResponseDTO response = new ResponseDTO();
		response.setLongMessage(longMessage);
		response.setShortMessage(shortMessage);
		response.setStatus(MessageConstants.SUCCESS);
		final Options options = new Options();
		options.setMessageType(MessageConstants.OK_DIALOG);
		response.setOptions(options);
		return response;
	}

}
