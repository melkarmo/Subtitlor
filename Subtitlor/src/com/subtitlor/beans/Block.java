package com.subtitlor.beans;

// Block représente une ligne de sous-titres
public class Block {
	private int idDb;
	private String fileName;
	private int id;
	private String timeInterval;
	private int idLine;
	private String subtitles;
	
	public int getIdDb() {
		return idDb;
	}
	public void setIdDb(int idDb) {
		this.idDb = idDb;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTimeInterval() {
		return timeInterval;
	}
	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}
	public int getIdLine() {
		return idLine;
	}
	public void setIdLine(int idLine) {
		this.idLine = idLine;
	}
	public String getSubtitles() {
		return subtitles;
	}
	public void setSubtitles(String subtitles) {
		this.subtitles = subtitles;
	}
}
