package com.stackroute.giphermanager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * This is Gipher Recomendation POJO.
 * 
 * @author L.GANESH
 *
 */
@Document
public class GipherRecomendation {

	@Id
	private String id;
	
	private String giphyId;
	private String title;
	private String giphyUrl;
	private Long count;
	
	public GipherRecomendation(String id, String giphyId, String title, String giphyUrl, Long count) {
		super();
		this.id = id;
		this.giphyId = giphyId;
		this.title = title;
		this.giphyUrl = giphyUrl;
		this.count = count;
	}
	public GipherRecomendation(String giphyId, String title, String giphyUrl, Long count) {
		super();
		this.giphyId = giphyId;
		this.title = title;
		this.giphyUrl = giphyUrl;
		this.count = count;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGiphyId() {
		return giphyId;
	}
	public void setGiphyId(String giphyId) {
		this.giphyId = giphyId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGiphyUrl() {
		return giphyUrl;
	}
	public void setGiphyUrl(String giphyUrl) {
		this.giphyUrl = giphyUrl;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
}
