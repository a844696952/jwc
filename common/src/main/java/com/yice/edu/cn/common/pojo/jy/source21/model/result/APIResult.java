package com.yice.edu.cn.common.pojo.jy.source21.model.result;

import java.util.Collections;
import java.util.List;

/**
 * API请求结果对象
 * 
 * @author zhoushubing
 *
 * @param <T>
 */
public class APIResult<E> {

	public static class Page {
		// 总数量
		private Integer totalCount;
		// 分页总页数 根据 数据总数 和 每页显示条数计算出当前数据量可分页的页数
		private Integer pageCount;
		// 当前分页页码
		private Integer currentPage;
		// 分页每页显示数据条数
		private Integer perPage;

		public Integer getTotalCount() {
			return totalCount;
		}

		public void setTotalCount(Integer totalCount) {
			this.totalCount = totalCount;
		}

		public Integer getPageCount() {
			return pageCount;
		}

		public void setPageCount(Integer pageCount) {
			this.pageCount = pageCount;
		}

		public Integer getCurrentPage() {
			return currentPage;
		}

		public void setCurrentPage(Integer currentPage) {
			this.currentPage = currentPage;
		}

		public Integer getPerPage() {
			return perPage;
		}

		public void setPerPage(Integer perPage) {
			this.perPage = perPage;
		}

	}

	/**
	 * 接口请求状态码，0代表调用成功，大于0则错误具体的错误信息由同时返回的 msg 描述
	 */
	private Integer code;
	/**
	 * 接口调用描述信息 code为0时为Success code 大于0时为具体的错误信息.
	 */
	private String msg;
	/**
	 * 接口返回的数据，如果接口返回的是数据集时为一个JSON数组，单条数据为JSON对象.
	 */
	private List<E> data;

	private Page page;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<E> getData() {
		if (data == null) {
			data = Collections.emptyList();
		}
		return data;
	}

	public void setData(List<E> data) {
		this.data = data;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
