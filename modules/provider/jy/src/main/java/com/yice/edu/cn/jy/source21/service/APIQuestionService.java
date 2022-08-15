package com.yice.edu.cn.jy.source21.service;
import com.yice.edu.cn.jy.source21.constant.APIConstant;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.Question;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.QuestionType;
import com.yice.edu.cn.common.pojo.jy.source21.model.result.APIResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 
 * API Question 服务
 * 
 * @author zhoushubing
 *
 */
@Service
public class APIQuestionService extends APICommonService {

	/**
	 * 获取试题列表
	 * 
	 * @param params
	 *            请求参数 {@value chapterId | knowledgeId = 必填项} {@value stage &
	 *            subjectId = 必填项}
	 * @return
	 */
	public APIResult<Question> getQuestions(APIRequestParams params) {
		if (params.chapterId == null && params.knowledgeId == null) {
			throw new RuntimeException("chapterId or knowledgeId is null.");
		}
		if (params.stage == null || params.subjectId == null) {
			throw new RuntimeException("stage and subjectId cannot be null.");
		}
		return apiRequest.request(APIConstant.URL_QUESTIONS, Question.class, params);
	}

	/**
	 * 获取试题类型列表
	 * 
	 * @param _stage
	 *            学段ID APIConstant.STAGE_
	 * @param _subjectId
	 *            学科ID
	 * @return
	 */
	public List<QuestionType> getQuestionTypes(int _stage, int _subjectId) {
		return apiRequest.requestList(APIConstant.URL_QUESTION_TYPES, QuestionType.class, new APIRequestParams() {
			{
				this.stage = _stage;
				this.subjectId = _subjectId;
			}
		});
	}

	/**
	 * 获取试题答案和解析
	 * 
	 * @param questions
	 *            试题列表
	 * @return
	 */
	public List<Question> getQuestionsAnswer(List<Question> questions) {
		if (questions.isEmpty()) {
			return questions;
		}
		StringBuffer _questionIds = new StringBuffer();
		for (Question question : questions) {
			if (question.getQuestionId() == null) {
				throw new RuntimeException("question ID is null.");
			}
			_questionIds.append(question.getQuestionId()).append(",");
		}
		APIResult<Question> result = apiRequest.request(APIConstant.URL_QUESTION_DETAIL, Question.class,
				new APIRequestParams() {
					{
						this.questionIds = _questionIds.toString();
					}
				});
		return result.getData();
	}

	/**
	 * 获取试题答案和解析
	 * 
	 * @param question
	 *            试题对象
	 * @return
	 */
	public Question getQuestionAnswer(Question question) {
		List<Question> questions = new ArrayList<Question>(1);
		questions.add(question);
		return getQuestionsAnswer(questions).get(0);
	}
	/**
	 * 获取试题答案和解析
	 *
	 * @param ids
	 *            试题列表
	 * @return
	 */
	public List<Question> getQuestionDetail(List<String> ids) {
		if (ids.isEmpty()) {
			return null;
		}
		APIResult<Question> result = apiRequest.request(APIConstant.URL_QUESTION_DETAIL, Question.class,
			new APIRequestParams() {
				{
					this.questionIds = ids.stream().collect(Collectors.joining(","));
				}
			});
		return result.getData();
	}

	/**
	 * 获取试题答案和解析
	 *
	 * @param id
	 *            试题对象
	 * @return
	 */
	public Question getQuestionDetail(String id) {
		List<String> questions = new ArrayList<>(1);
		questions.add(id);
		List<Question> questionList = getQuestionDetail(questions);

		if(Optional.ofNullable(questionList).isPresent()&&questionList.size()>0)
			return questionList.get(0);
		else
			return null;
	}

}
