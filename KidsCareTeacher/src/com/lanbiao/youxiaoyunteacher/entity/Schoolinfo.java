package com.lanbiao.youxiaoyunteacher.entity;
import java.sql.Timestamp;

/**
 * Schoolinfo entity. @author MyEclipse Persistence Tools
 */

public class Schoolinfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String schoolName;
	private String contacts;
	private String phone;
	private Integer type;
	private String qq;
	private String schoolwehsite;
	private String address;
	private String logoaddress;
	private String portraiture;
	private String zipcode;
	private String email;
	private String remark;
	private String city;
	private String region;
	private Timestamp setuptime;
	private Integer grading;
	private Integer sort;

	// Constructors

	/** default constructor */
	public Schoolinfo() {
	}

	/** full constructor */
	public Schoolinfo(String schoolName, String contacts, String phone,
			Integer type, String qq, String schoolwehsite, String address,
			String logoaddress, String portraiture, String zipcode,
			String email, String remark, String city, String region,
			Timestamp setuptime, Integer grading, Integer sort) {
		this.schoolName = schoolName;
		this.contacts = contacts;
		this.phone = phone;
		this.type = type;
		this.qq = qq;
		this.schoolwehsite = schoolwehsite;
		this.address = address;
		this.logoaddress = logoaddress;
		this.portraiture = portraiture;
		this.zipcode = zipcode;
		this.email = email;
		this.remark = remark;
		this.city = city;
		this.region = region;
		this.setuptime = setuptime;
		this.grading = grading;
		this.sort = sort;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSchoolName() {
		return this.schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getContacts() {
		return this.contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getSchoolwehsite() {
		return this.schoolwehsite;
	}

	public void setSchoolwehsite(String schoolwehsite) {
		this.schoolwehsite = schoolwehsite;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLogoaddress() {
		return this.logoaddress;
	}

	public void setLogoaddress(String logoaddress) {
		this.logoaddress = logoaddress;
	}

	public String getPortraiture() {
		return this.portraiture;
	}

	public void setPortraiture(String portraiture) {
		this.portraiture = portraiture;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Timestamp getSetuptime() {
		return this.setuptime;
	}

	public void setSetuptime(Timestamp setuptime) {
		this.setuptime = setuptime;
	}

	public Integer getGrading() {
		return this.grading;
	}

	public void setGrading(Integer grading) {
		this.grading = grading;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}