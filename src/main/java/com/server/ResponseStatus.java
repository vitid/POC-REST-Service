package com.server;

public class ResponseStatus {
	private int code;
	private String msg;
	public ResponseStatus(){
		this(0,"");
	}
	public ResponseStatus(String msg){
		this(1,msg);
	}
	public ResponseStatus(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ResponseStatus)) return false;
		
		ResponseStatus response = (ResponseStatus)obj;
		return this.code == response.code && this.msg == response.msg;
	}
}
