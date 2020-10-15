package com.zhaoj.common;

import lombok.Data;

import java.io.Serializable;

/**
 *   接口返回数据格式
 */
@Data
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 成功标志
	 */
	private boolean success = true;

	/**
	 * 返回处理消息
	 */
	private String message = "操作成功！";

	/**
	 * 返回代码
	 */
	private Integer code = 0;
	/**
	 * 返回数据对象 data
	 */
	private T rows;
	/**
	 * 当前页码
	 */
	private Integer page;
	/**
	 * 信息总页数
	 */
	private Integer total;
	/**
	 * 信息总记录数
	 */
	private Integer records;

	/**
	 * 时间戳
	 */
	private long timestamp = System.currentTimeMillis();

	public Result() {
		
	}
	
	public Result<T> success(String message) {
		this.message = message;
		this.code = CommonConstant.SC_OK_200;
		this.success = true;
		return this;
	}
	
	
	public static Result<Object> ok() {
		Result<Object> r = new Result<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setMessage("成功");
		return r;
	}
	
	public static Result<Object> ok(String msg) {
		Result<Object> r = new Result<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setMessage(msg);
		return r;
	}
	
	public static Result<Object> ok(Object data) {
		Result<Object> r = new Result<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setRows(data);
		return r;
	}

	/**
	 *
	 * @param data
	 * @param page
	 * @param total
	 * @param records
	 * @return jqGrid数据格式
	 */
	public static Result<Object> ok(Object data, Integer page, Integer total,Integer records) {
		Result<Object> r = new Result<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setRows(data);
		r.setPage(page);
		r.setTotal(total);
		r.setRecords(records);
		return r;
	}
	
	public static Result<Object> error(String msg) {
		return error(CommonConstant.SC_INTERNAL_SERVER_ERROR_500, msg);
	}
	
	public static Result<Object> error(int code, String msg) {
		Result<Object> r = new Result<Object>();
		r.setCode(code);
		r.setMessage(msg);
		r.setSuccess(false);
		return r;
	}

	public Result<T> error500(String message) {
		this.message = message;
		this.code = CommonConstant.SC_INTERNAL_SERVER_ERROR_500;
		this.success = false;
		return this;
	}

}