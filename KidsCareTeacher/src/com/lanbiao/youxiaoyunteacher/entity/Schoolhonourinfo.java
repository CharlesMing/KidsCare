package com.lanbiao.youxiaoyunteacher.entity;
import java.sql.Timestamp;

/**
 * Schoolhonourinfo entity. @author MyEclipse Persistence Tools
 */

public class Schoolhonourinfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String honurLogo;
	private Timestamp honourBegintime;
	private Timestamp honourEndtime;
	private Integer honourType;
	private Integer schoolId;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public Schoolhonourinfo() {
	}

	/** full constructor */
	public Schoolhonourinfo(String name, String honurLogo,
			Timestamp honourBegintime, Timestamp honourEndtime,
			Integer honourType, Integer schoolId, Timestamp createTime) {
		this.name = name;
		this.honurLogo = honurLogo;
		this.honourBegintime = honourBegintime;
		this.honourEndtime = honourEndtime;
		this.honourType = honourType;
		this.schoolId = schoolId;
		this.createTime = createTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHonurLogo() {
		return this.honurLogo;
	}

	public void setHonurLogo(String honurLogo) {
		this.honurLogo = honurLogo;
	}

	public Timestamp getHonourBegintime() {
		return this.honourBegintime;
	}

	public void setHonourBegintime(Timestamp honourBegintime) {
		this.honourBegintime = honourBegintime;
	}

	public Timestamp getHonourEndtime() {
		return this.honourEndtime;
	}

	public void setHonourEndtime(Timestamp honourEndtime) {
		this.honourEndtime = honourEndtime;
	}

	public Integer getHonourType() {
		return this.honourType;
	}

	public void setHonourType(Integer honourType) {
		this.honourType = honourType;
	}

	public Integer getSchoolId() {
		return this.schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}