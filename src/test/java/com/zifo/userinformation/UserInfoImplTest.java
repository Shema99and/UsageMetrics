package com.zifo.userinformation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idbs.ewb.rest.services.SecurityAdminService;
import com.zifo.ewb.pojo.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserInfoImplTest {

    @Mock
    private SecurityAdminService securityAdminService;

    @InjectMocks
    private UserInfoImpl userInfoImpl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserDetails_success() throws Exception {
        String json = """
        {
          "userlist": [
            {
              "userName": "mudid1",
              "userFullName": "John Doe",
              "department": "Engineering",
              "disabled": true,
              "emails": {
                "email": ["john@example.com"]
              },
              "assignablerolelevel": "Level1",
              "systemRoles": {
                "name": ["SysAdmin"]
              }
            }
          ],
          "group": [
            {
              "name": "Admin"
            }
          ]
        }
        """;

        JsonNode mockResponse = objectMapper.readTree(json);
        when(securityAdminService.getUsers()).thenReturn(mockResponse);
        when(securityAdminService.findGroupsForUser("mudid1")).thenReturn(mockResponse);

        List<UserModel> users = userInfoImpl.getUserDetails();

        assertNotNull(users);
        assertEquals(1, users.size());

        UserModel user = users.get(0);
        assertEquals("mudid1", user.getUserName());
        assertEquals("John Doe", user.getUserLoginName());
        assertEquals("Engineering", user.getDepartments());
        assertEquals("true", user.getDisabled());
        assertEquals("john@example.com", user.getEmails());
        assertEquals("Level1", user.getAssignableRoleLevel());
        assertEquals("SysAdmin", user.getSystemRoles());
        assertEquals("Admin", user.getGroups());
    }
}
