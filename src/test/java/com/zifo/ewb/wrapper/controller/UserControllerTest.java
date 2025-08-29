package com.zifo.ewb.wrapper.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.idbs.ewb.rest.services.SpreadSheetService;
import com.idbs.ewb.spreadsheet.pojo.Oauth;
import com.idbs.ewb.spreadsheet.pojo.RequestDTO;
import com.idbs.ewb.spreadsheet.pojo.ResponseDTO;
import com.idbs.ewb.spreadsheet.pojo.Spreadsheet;
import com.zifo.ewb.pojo.UserModel;
import com.zifo.ewb.wrapper.constants.MessageConstants;
import com.zifo.ewb.wrapper.constants.StudyDirectorConstants;
import com.zifo.ewb.wrapper.populatedata.helper.AccessTokenProvider;
import com.zifo.ewb.wrapper.utils.DataReader;
import com.zifo.userinformation.UserInfoImpl;

class UserControllerTest {

	@InjectMocks
	private UserController userController;

	@Mock
	private AccessTokenProvider tokenProvider;

	@Mock
	private UserInfoImpl userInfoImpl;

	@Mock
	private SpreadSheetService spreadSheetService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}


@Test
void testGetUserInformation_Success() throws Exception {
    // Prepare input JSON
    RequestDTO requestDTO = new RequestDTO();
    Oauth oauth = new Oauth();
    oauth.setAccessCode("dummyAccessCode");
    requestDTO.setOauth(oauth);

    Spreadsheet spreadsheet = new Spreadsheet();
    spreadsheet.setVersionId("v123");
    requestDTO.setSpreadsheet(spreadsheet);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInput = mapper.writeValueAsString(requestDTO);

    // Mock dependencies
    when(tokenProvider.requestAccessToken("dummyAccessCode")).thenReturn("mockToken");
    when(spreadSheetService.getData(any(), any(), any())).thenReturn("mockResponse");
    when(userInfoImpl.getUserDetails()).thenReturn(List.of(new UserModel(), new UserModel()));

    try (MockedStatic<DataReader> mockedStatic = Mockito.mockStatic(DataReader.class)) {
        Map<String, List<String>> mockDimensions = Map.of(
            StudyDirectorConstants.USAGEINDEX, List.of("row1", "row2")
        );
        mockedStatic.when(() -> DataReader.readDimensions(any(), any()))
                    .thenReturn(mockDimensions);

        // Call method
        ResponseDTO response = userController.getUserInformation(jsonInput);

        // Assert
        assertNotNull(response);
        assertEquals("SUCCESS", response.getStatus());
        assertEquals(MessageConstants.DATA_SUCCESS_MSG, response.getLongMessage());
    }
}

	@Test
	void testGetUserInformation_Failure() {
		String invalidJson = "invalid";

		ResponseDTO response = userController.getUserInformation(invalidJson);

		System.out.println("Response: " + response);
		// Assert failure response
		assertNotNull(response);

		assertEquals("FAILURE", response.getStatus()); // or MessageConstants.USER_FAIL if controller returns that
		assertEquals(MessageConstants.INTL_SERVER_ERROR, response.getLongMessage());

	}
}