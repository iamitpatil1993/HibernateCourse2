package com.example.hibernate.relationship.singlevalue;

import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.example.hibernate.BaseEntity;

/**
 * 
 * @author amit
 * This is demo to show second way of mapping one-to-one relationship
 * In this demo, we have done one-to-one unidirectional relationship from usre -> file. We have mapped joinColumn in user entity(parent) and not in file entity(child). THis 
 * is one way we should always use to map one-to-one relationships. 
 * This approach have many advantage opposed to one in which we map joinColumn in chile entity.
 * Advantages
 * 1. Child entity becomes completely detached from parent.	It do not have any information about where it is used. So loading of child entity do not require to join it's parent
 *  	and hence becomes light.
 * 2. Since, chile entity do not have any information about it's parenets, we can reuse it for many entities. Like we have chile entity with generic table strcture Address, File
 * 		etc.
 * 3. Since, foreign key is in parent table, while fetching hibernate knows whether entity has value for child entity or not based on foreign key column. 
 * 4. Lazy loading of chile works 100% in this mapping strategy, if we mark relationship to be one-to-one and lazy, hibernate will load child entity lazily when first
 * 		accessed opposed to while loading parent entity. This became possible because, hiberate has information whether chile exists or not for this parent right at the time
 * 		we load parent entity. So, hibernate will load child if available using fk and avoid executing query if ok is null.
 * 5. Soft delete works completely hassle free here, since we are storing fk in parent, it will point single entity in child table. And parent will be having reference to
 * 		latest chile entity.
 * 6. No need to add any constraints in child table.
 * 7. We can add unique constraint in parent table to enforce unique child entity for each parent entity.
 * 
 *  Drawback
 *  1. Since parent will be poiting latest child entity, if we soft delete chile entity and reassign new one, we can not have history of this,  child entity row
 *  	do not have information about it's parent and parent is pointing to latest. So old soft deleted entities remains unassigned.
 */
@Entity
@Table(name = "image")
public class File extends BaseEntity {

	private static final long serialVersionUID = 6381651316067952177L;

	@Basic
	@Id
	@GeneratedValue(generator = "file_id_gen")
	@GenericGenerator(name = "file_id_gen", strategy = "uuid2")
	private UUID imageId;

	@Basic
	@Column(name = "file_name")
	private String fileName;

	@Basic
	@Column(name = "file_type")
	private String fileType;

	@Basic
	@Column(name = "file_extension")
	private String fileExtension;

	@Basic
	@Column(name = "local_path")
	private String localDirPath;

	@Basic
	@Column(name = "external_url")
	private String externalPathUrl;
	
	/*
	 * We are not adding user one-to-one mapping here becuase, we do not need this, also in order to keep this table reusable from other entities we do not need.
	 * We should always avoid mapping relationship if not required. 
	 */
	
	
	public File(String fileName, String fileType, String fileExtension, String localDirPath,
			String externalPathUrl) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileExtension = fileExtension;
		this.localDirPath = localDirPath;
		this.externalPathUrl = externalPathUrl;
	}

	public File() {
	}
	
	public UUID getImageId() {
		return imageId;
	}

	public void setImageId(UUID imageId) {
		this.imageId = imageId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getLocalDirPath() {
		return localDirPath;
	}

	public void setLocalDirPath(String localDirPath) {
		this.localDirPath = localDirPath;
	}

	public String getExternalPathUrl() {
		return externalPathUrl;
	}

	public void setExternalPathUrl(String externalPathUrl) {
		this.externalPathUrl = externalPathUrl;
	}
}
