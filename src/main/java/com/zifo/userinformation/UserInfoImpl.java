package com.zifo.userinformation;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.idbs.ewb.rest.services.SecurityAdminService;
import com.zifo.ewb.pojo.UserModel;
import com.zifo.user.UserInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInfoImpl implements UserInfo {

	private static final Logger LOGGER = LogManager.getLogger(UserInfoImpl.class);
	private final SecurityAdminService securityAdminService;

		public List<UserModel> getUserDetails() {
			List<UserModel> listUsers = new ArrayList<>();
				JsonNode response = securityAdminService.getUsers();
				JsonNode userList = response.path("userlist");

				if (userList != null && userList.isArray()) {
					for (JsonNode userNode : userList) {
						UserModel user = new UserModel();
						populateBasicUserInfo(user, userNode);
						user.setEmails(extractEmails(userNode));
						user.setAssignableRoleLevel(userNode.path("assignablerolelevel").asText());
						user.setSystemRoles(extractRoles(userNode));
						user.setGroups(safeGetUserGroupName(user.getUserName()));
						listUsers.add(user);
					}
				}
			return listUsers.isEmpty() ? null : listUsers;
		}

		private void populateBasicUserInfo(UserModel user, JsonNode userNode) {
		    user.setUserName(userNode.path("userName").asText());
		    user.setUserLoginName(userNode.path("userFullName").asText());
		    user.setDepartments(userNode.path("department").asText("No Department"));
		    user.setDisabled(String.valueOf(userNode.path("disabled").asBoolean()));
		}

		private String extractEmails(JsonNode userNode) {
		    JsonNode emailsNode = userNode.path("emails").path("email");
		    if (!emailsNode.isMissingNode() && emailsNode.isArray()) {
		        List<String> emails = new ArrayList<>();
		        for (JsonNode email : emailsNode) {
		            if (!email.isNull()) {
		                emails.add(email.asText());
		            }
		        }
		        return String.join(",", emails);
		    }
		    return "No Emails";
		}

		private String extractRoles(JsonNode userNode) {
		    JsonNode rolesNode = userNode.path("systemRoles").path("name");
		    if (rolesNode.isArray()) {
		        List<String> roles = new ArrayList<>();
		        for (JsonNode role : rolesNode) {
		            if (!role.isNull()) {
		                roles.add(role.asText());
		            }
		        }
		        return String.join(",", roles);
		    }
		    return "No Roles";
		}

		private String safeGetUserGroupName(String userName) {
		    try {
		        return getUserGroupName(userName);
		    } catch (Exception e) {
		    	LOGGER.error("Error setting groups for user: {}", userName, e);
		        return "Group fetch failed";
		    }
		}


	private String getUserGroupName(String userLoginName) {
	    List<String> groupNames = new ArrayList<>();
	    	JsonNode groupResponse = securityAdminService.findGroupsForUser(userLoginName);
	        JsonNode groups = groupResponse.path("group");
	        if (groups.isArray()) {
	            for (JsonNode groupNode : groups) {
	                String name = groupNode.path("name").asText();
	                if (name != null && !name.isEmpty()) {
	                    groupNames.add(name);
	                } else {
	                    groupNames.add("No groups");
	                }
	            }
	        }
	    return String.join(",", groupNames);
	}
	
}
