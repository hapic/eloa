/*
 * Powered By [rapid-framework]
 * Web Site: #
 * By:eloa
 * Since 2015 - 2016
 */


package com.el.oa.domain.room.vo;

import java.io.Serializable;


public class BoardroomVo implements Serializable {


	//alias
	private Integer id;  
	/**
	*会议室名称
	*/
	private String name;  
	/**
	*会议室状态
	*/
	private Integer status;  
	/**
	*修改时间
	*/
	private java.util.Date modified;  
	/**
	*创建时间
	*/
	private java.util.Date created;  
	

    
    public void setId(Integer id) {  
        this.id = id;  
    }  
      
    public Integer getId() {  
        return this.id;  
    }  
    
    public void setName(String name) {  
        this.name = name;  
    }  
      
    public String getName() {  
        return this.name;  
    }  
    
    public void setStatus(Integer status) {  
        this.status = status;  
    }  
      
    public Integer getStatus() {  
        return this.status;  
    }  
    
    public void setModified(java.util.Date modified) {  
        this.modified = modified;  
    }  
      
    public java.util.Date getModified() {  
        return this.modified;  
    }  
    
    public void setCreated(java.util.Date created) {  
        this.created = created;  
    }  
      
    public java.util.Date getCreated() {  
        return this.created;  
    }  
	
	

}
