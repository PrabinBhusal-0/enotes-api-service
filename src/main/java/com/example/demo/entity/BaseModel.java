package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;


@Getter
@Setter
@MappedSuperclass
public class BaseModel {
	
private Boolean isActive;
	
	private Boolean isDeleted;
	
	private Integer createdBy;

	@CreationTimestamp
    @Column(nullable = false, updatable = false)
	private Date createdOn;
	
	private Integer UpdatedBy;
	
	private Date updatedOn;

}
