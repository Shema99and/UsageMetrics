package com.zifo.ewb.wrapper.populatedata.helper;

import com.idbs.ewb.spreadsheet.pojo.Options;
import com.idbs.ewb.spreadsheet.pojo.ResponseDTO;
import com.zifo.ewb.wrapper.constants.MessageConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessengerTest {

    @Test
    void testGenerateFailureResponse() {
        String longMessage = "Something went wrong";
        String shortMessage = "Error";

        ResponseDTO response = Messenger.generateFailureResponse(longMessage, shortMessage);

        assertNotNull(response);
        assertEquals(longMessage, response.getLongMessage());
        assertEquals(shortMessage, response.getShortMessage());
        assertEquals(MessageConstants.FAILURE, response.getStatus());

        Options options = response.getOptions();
        assertNotNull(options);
        assertEquals(MessageConstants.OK_DIALOG, options.getMessageType());
    }

    @Test
    void testGenerateSuccessResponse() {
        String longMessage = "Operation successful";
        String shortMessage = "Success";

        ResponseDTO response = Messenger.generateSuccessResponse(longMessage, shortMessage);

        assertNotNull(response);
        assertEquals(longMessage, response.getLongMessage());
        assertEquals(shortMessage, response.getShortMessage());
        assertEquals(MessageConstants.SUCCESS, response.getStatus());

        Options options = response.getOptions();
        assertNotNull(options);
        assertEquals(MessageConstants.OK_DIALOG, options.getMessageType());
    }
}
