package com.zifo.ewb.pojo;

import org.springframework.stereotype.Component;

@Component
public class UserModel {
	
	private String userLoginName;
	private String userName;
	private String emails;
	private String departments;
	private String disabled;
	private String assignableRoleLevel;
	private String groups;
	private String systemRoles;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public String getDepartments() {
		return departments;
	}

	public void setDepartments(String departments) {
		this.departments = departments;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getAssignableRoleLevel() {
		return assignableRoleLevel;
	}

	public void setAssignableRoleLevel(String assignableRoleLevel) {
		this.assignableRoleLevel = assignableRoleLevel;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	public String getSystemRoles() {
		return systemRoles;
	}

	public void setSystemRoles(String systemRoles) {
		this.systemRoles = systemRoles;
	}

}
