package com.zifo.ewb.wrapper.constants;

/**
 *
 * Message constants are present in this class
 *
 * @author zifo
 *
 */
public class MessageConstants {
	private MessageConstants() {
	}
	
	public static final String STATUS_SUCCESS = "success";
	
	public static final String STATUS_FAILURE = "failure";

	public static final String ERROR_404_MSG = "Unable to communicate with the Study Director dependent Web Service";

	public static final String ERROR_401_MSG = "Authorization has been denied for this request.";

	public static final String INTL_SERVER_ERROR = "Internal server error occurred. Please contact your Administrator";

	public static final String USER_INFO = "User Info {0}";

	public static final String USER_SUC = java.text.MessageFormat.format(USER_INFO, STATUS_SUCCESS);

	public static final String USER_FAIL = java.text.MessageFormat.format(USER_INFO, STATUS_FAILURE);

	public static final String DATA_SUCCESS_MSG = "User Information retrived successfully";

	public static final String SUCCESS = "SUCCESS";

	public static final String FAILURE = "FAILURE";

	public static final String OK_DIALOG = "notification";

	public static final String STUDY_NO_INVALID = "Please select a valid Study Number and try again";

	public static final String TABLE_NOT_FOUND = "Table not found";

	public static final String INVALID_TEMPLATE_MSG = "Please make sure a valid template is used. The following table(s) are missing : ";

	public static final String SQ_BRACKET_OPEN = "[";

	public static final String SQ_BRACKET_CLOSE = "]";

}
