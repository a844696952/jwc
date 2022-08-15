package com.yice.edu.cn.jy.source21.service;

import com.yice.edu.cn.common.pojo.jy.source21.model.question.QuestionType;
import com.yice.edu.cn.jy.source21.constant.APIConstant;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.*;
import com.yice.edu.cn.jy.source21.util.APIRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * API Common 服务
 * 
 * @author zhoushubing
 *
 */
@Service
public class APICommonService {

	protected APIRequest apiRequest = APIRequest.getInstance();

	/**
	 * 根据学段获取教材科目
	 * 
	 * @param _stage
	 *            学段ID 1:小学，2:初中，3:高中
	 * @return
	 */
	public List<Subject> getSubjects(Integer _stage) {
		return apiRequest.requestList(APIConstant.URL_SUBJECTS, Subject.class, new APIRequestParams() {
			{
				this.stage = _stage;
			}
		});
	}

	/**
	 * 获取版本列表
	 * 
	 * @param _stage
	 *            学段ID 1:小学，2:初中，3:高中
	 * @param _subjectId
	 *            科目ID
	 * @return
	 */
	public List<Version> getVersions(Integer _stage, Integer _subjectId) {
		return apiRequest.requestList(APIConstant.URL_VERSIONS, Version.class, new APIRequestParams() {
			{
				this.stage = _stage;
				this.subjectId = _subjectId;
			}
		});
	}
	/**
	 * 获取版本列表
	 * type 设置为2 版本类型 1:将得到全部版本(适用于筛选文档资源), 2:将得到经过过滤的版本(适用于筛选题目) 经过过滤的版本能最大限度查找到题目，如果不填写则默认为 1
	 *
	 * @param _stage
	 *            学段ID 1:小学，2:初中，3:高中
	 * @param _subjectId
	 *            科目ID
	 * @return
	 */
	public List<Version> getVersions4Question(Integer _stage, Integer _subjectId) {
		return apiRequest.requestList(APIConstant.URL_VERSIONS, Version.class, new APIRequestParams() {
			{
				this.stage = _stage;
				this.subjectId = _subjectId;
				this.type = "2";
			}
		});
	}

	/**
	 * 获取教材册别信息
	 * 
	 * @param _versionId
	 *            版本ID
	 * @return
	 */
	public List<Book> getBooks(Integer _versionId) {
		return apiRequest.requestList(APIConstant.URL_BOOKS, Book.class, new APIRequestParams() {
			{
				this.versionId = _versionId;
				this.type = "2";
			}
		});
	}

	/**
	 * 获取章节信息
	 * 
	 * @param _bookId
	 * @return
	 */
	public List<Chapter> getChapters(Integer _bookId) {
		return apiRequest.requestList(APIConstant.URL_CHAPTERS, Chapter.class, new APIRequestParams() {
			{
				this.bookId = _bookId;
				this.type = "2";
			}
		});
	}

	/**
	 * 获取知识点列表
	 * 
	 * @param _stage
	 *            学段ID 1:小学，2:初中，3:高中
	 * @param _subjectId
	 *            科目ID
	 * @return
	 */
	public List<Knowledge> getKnowledgePoints(Integer _stage, Integer _subjectId) {
		return apiRequest.requestList(APIConstant.URL_SUBJECT_KNOWLEDGEPOINTS, Knowledge.class, new APIRequestParams() {
			{
				this.subjectId = _subjectId;
				this.stage = _stage;
			}
		});
	}
	public List<Knowledge> getKnowledgePoints4Question(Integer _stage, Integer _subjectId) {
		return apiRequest.requestList(APIConstant.URL_SUBJECT_KNOWLEDGEPOINTS, Knowledge.class, new APIRequestParams() {
			{
				this.subjectId = _subjectId;
				this.stage = _stage;
				this.type = "2";
			}
		});
	}

	/**
     * 通过章节获取知识点
	 * @param _categoryId
     * @return
     */
	public List<CategoryKnowledge> getKnowledgePoints4Category(Integer _categoryId) {
		return apiRequest.requestList(APIConstant.URL_CATEGORY_KNOWLEDGEPOINTS, CategoryKnowledge.class, new APIRequestParams() {
			{
				this.categoryId = String.valueOf(_categoryId);
				this.type = "2";
			}
		});
	}
	/**
	 * 获取省份地区信息
	 * 
	 * @return
	 */
	public List<Province> getProvinces() {
		return apiRequest.requestList(APIConstant.URL_PROVINCES, Province.class);
	}

	/**
	 * 通过章节获取知识点
	 * @return
	 */
	public List<QuestionType> getQuestionsTypes(Integer _stage,Integer _subjectId) {
		return apiRequest.requestList(APIConstant.URL_QUESTION_TYPES, QuestionType.class, new APIRequestParams() {
			{
				this.stage = _stage;
				this.subjectId = _subjectId;
			}
		});
	}

}
