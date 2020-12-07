package com.stackroute.giphermanager.model;

/**
 * This is Gipher Request Parameter Data.
 * 
 * @author L.GANESH
 *
 */
public class GipherRequest {

	
	private int pageNo;
	private String searchKey;
	private int noOfRecordsToFetch;
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public int getNoOfRecordsToFetch() {
		return noOfRecordsToFetch;
	}
	public void setNoOfRecordsToFetch(int noOfRecordsToFetch) {
		this.noOfRecordsToFetch = noOfRecordsToFetch;
	}
	
}
