package com.marryou.metadata.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marryou.commons.utils.json.JsonDateSerializer;
import com.marryou.commons.utils.json.annotation.JsonIgnore;
import com.marryou.metadata.entity.base.BaseEntity;
import com.marryou.metadata.enums.RoleEnum;
import com.marryou.metadata.enums.StatusEnum;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * user
 * @author linhy
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "user_base")
public class UserEntity extends BaseEntity {

	private static final long serialVersionUID = 6273325472710752512L;

	private String loginName;

	private String password;

	private String userName;

	private Integer sex;

	private Date birth;

	private String phone;

	private String address;

	private String email;

	private RoleEnum role;

	private StatusEnum status;

	private Long companyId;


	public UserEntity() {
	}

	@Column(name = "login_name")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@JsonIgnore
	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "sex")
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "birth")
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "role")
	public RoleEnum getRole() {
		return role;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
	}

	@Column(name = "status")
	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	@Column(name = "company_id")
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "UserEntity{" +
				"loginName='" + loginName + '\'' +
				", password='" + password + '\'' +
				", userName='" + userName + '\'' +
				", sex=" + sex +
				", birth=" + birth +
				", phone='" + phone + '\'' +
				", address='" + address + '\'' +
				", email='" + email + '\'' +
				", role=" + role +
				", status=" + status +
				", companyId=" + companyId +
				"} " + super.toString();
	}
}
