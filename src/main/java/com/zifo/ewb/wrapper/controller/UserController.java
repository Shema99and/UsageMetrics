package com.zifo.ewb.wrapper.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idbs.ewb.rest.services.SpreadSheetService;
import com.idbs.ewb.spreadsheet.pojo.RequestDTO;
import com.idbs.ewb.spreadsheet.pojo.ResponseDTO;
import com.zifo.ewb.pojo.UserModel;
import com.zifo.ewb.wrapper.constants.MessageConstants;
import com.zifo.ewb.wrapper.constants.StudyDirectorConstants;
import com.zifo.ewb.wrapper.populatedata.helper.AccessTokenProvider;
import com.zifo.ewb.wrapper.populatedata.helper.Messenger;
import com.zifo.ewb.wrapper.utils.DataReader;
import com.zifo.ewb.wrapper.utils.JSONUtils;
import com.zifo.userinformation.UserInfoImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/API/v1")
@Api(tags = "User API")
@RequiredArgsConstructor
public class UserController {

	private final AccessTokenProvider tokenProvider;
	private final UserInfoImpl userInfoImpl;
	private final SpreadSheetService spreadSheetService;
	private final Logger logger = LogManager.getLogger(UserController.class);

	private static final String APPLICATION_JSON = "application/json";
	private static final String TEXT_PLAIN = "text/plain";

	@ApiOperation(value = "get user information")
	@PostMapping(path = "/GSK/userInfo", consumes = TEXT_PLAIN, produces = APPLICATION_JSON)
	public ResponseDTO getUserInformation(@RequestBody final String requestDTO) {
		ResponseDTO responseDTO = null;
		try {
			final ObjectMapper object = new ObjectMapper();
			final RequestDTO readval = object.readValue(requestDTO, RequestDTO.class);

			logger.info("Inside try");

			final String accessCode = readval.getOauth().getAccessCode();
			final String versionID = readval.getSpreadsheet().getVersionId();
			final String accessToken = tokenProvider.requestAccessToken(accessCode);

			spreadSheetService.getData(versionID, JSONUtils.createJSONToClearTable(StudyDirectorConstants.USAGE_METRICS),
					accessToken);

			List<UserModel> userDetails = userInfoImpl.getUserDetails();
			int noOfMembers = userDetails.size();

			logger.info("userDetails count: {}", userDetails.size());

			final String tableStructureData = spreadSheetService.getData(versionID,
					JSONUtils.createJSONToReadTableStructure(StudyDirectorConstants.USAGE_METRICS), accessToken);

			logger.info(tableStructureData);

			final Map<String, List<String>> readDimension = DataReader.readDimensions(tableStructureData,
					StudyDirectorConstants.USAGE_METRICS);

			logger.info("Available keys in readDimension: {}", readDimension.keySet());
			int rowsCount = readDimension.get(StudyDirectorConstants.USAGEINDEX).size();
			int insertAt = rowsCount;
			logger.info("rowsCount: {}", rowsCount);
			logger.info("insertAt: {}", insertAt);

			int numberOfRowsRequired = noOfMembers;
			logger.info("numberOfRowsRequired: {}", numberOfRowsRequired);
			

			if (rowsCount < numberOfRowsRequired) {
				numberOfRowsRequired = numberOfRowsRequired - rowsCount;
				logger.info("Inside creation");
				String requestJSON = JSONUtils.createJSONToChangeTableData(StudyDirectorConstants.USAGE_METRICS,
						StudyDirectorConstants.USAGEINDEX, Integer.toString(numberOfRowsRequired),
						Integer.toString(insertAt));
				logger.info("requestJSON: {}", requestJSON);
				spreadSheetService.getData(versionID, requestJSON, accessToken);
			}
			logger.info("Data is updated");
			responseDTO = Messenger.generateSuccessResponse(MessageConstants.DATA_SUCCESS_MSG,
					MessageConstants.USER_SUC);
		}

		catch (Exception ex) {
			responseDTO = Messenger.generateFailureResponse(MessageConstants.INTL_SERVER_ERROR,
					MessageConstants.USER_FAIL
					);
			logger.error(ex);
		}
		return responseDTO;
	}
}