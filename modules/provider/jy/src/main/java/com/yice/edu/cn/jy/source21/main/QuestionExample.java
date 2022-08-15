package com.yice.edu.cn.jy.source21.main;

import com.google.gson.Gson;
import com.yice.edu.cn.jy.source21.constant.APIConstant;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Book;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Chapter;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Subject;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Version;
import com.yice.edu.cn.common.pojo.jy.source21.model.question.Question;
import com.yice.edu.cn.jy.source21.service.APIQuestionService;
import com.yice.edu.cn.jy.source21.service.APIRequestParams;
import com.yice.edu.cn.common.pojo.jy.source21.model.result.APIResult;

import java.util.List;

/**
 * 试题例子
 * 
 * @author zhoushubing
 *
 */
public class QuestionExample {
	public static void main(String[] args) {
		APIQuestionService service = new APIQuestionService();
        // 获取小学教材科目
		System.out.println("获取小学教材科目");
		List<Subject> smallSubjects = service.getSubjects(APIConstant.STAGE_XIAOXUE);
		//System.out.println(new Gson().toJson(smallSubjects));
		System.out.println("获取初中教材科目");
		List<Subject> midSubjects = service.getSubjects(APIConstant.STAGE_CHUZHONG);
		//System.out.println(new Gson().toJson(midSubjects));
		System.out.println("获取高中教材科目");
		List<Subject> hignSubjects = service.getSubjects(APIConstant.STAGE_GAOZHONG);
		//System.out.println(new Gson().toJson(hignSubjects));
		//System.out.println(subjects.size());
		// 获取小学第一个科目下的教材版本列表
		System.out.println("获取小学第一个科目下的教材版本列表");
		List<Version> versions = service.getVersions(APIConstant.STAGE_XIAOXUE, 3);
		System.out.println("versions size:" + versions.size());
		System.out.println(new Gson().toJson(versions));
		// 获取版本教材下的册别信息列表
		System.out.println("获取版本教材下的册别信息列表");
		List<Book> books = service.getBooks(11740);
		System.out.println("books size:" + books.size());
		//System.out.println(new Gson().toJson(books));

		// 此处可获取Book下所有章节目录树
		System.out.println("此处可获取Book下所有章节目录树");
		List<Chapter> chapters = service.getChapters(10167);
		System.out.println("chapters size:" + chapters.size());
		//System.out.println(new Gson().toJson(chapters));
		//根据章节获取试题
		APIResult<Question> questions = service.getQuestions(new APIRequestParams() {
			{
				this.chapterId = "77683";//77684
				this.stage = APIConstant.STAGE_CHUZHONG;
				this.subjectId = 3;
				this.perPage = 50;
			}
		});
		System.out.println("根据章节获取试题");
		System.out.println(new Gson().toJson(questions));
//		System.out.println(new Gson().toJson(questions.getPage()));
//
//		List<KnowledgePoint> knowledges = service.getKnowledgePoints(APIConstant.STAGE_XIAOXUE, 3);
//		System.out.println("根据科目获取知识点");
//		System.out.println(new Gson().toJson(knowledges));
//		//根据知识点获取试题
//		questions = service.getQuestions(new APIRequestParams() {
//			{
//				this.knowledgeId = knowledges.get(0).getId();
//				this.stage = APIConstant.STAGE_XIAOXUE;
//				this.subjectId = subjects.get(1).getSubjectId();
//				this.perPage = 50;
//			}
//		});
//		System.out.println("根据知识点获取试题");
//		System.out.println(new Gson().toJson(questions));
//		System.out.println(new Gson().toJson(questions.getPage()));
//		List<Question> q1 = questions.getData();
//		List<Question> questionList = service.getQuestionsAnswer(q1);
//		System.out.println("根据知识点获取试题详情");
//		System.out.println(new Gson().toJson(questionList));
//		//获取试题类型
//		List<QuestionType> list = service.getQuestionTypes(APIConstant.STAGE_XIAOXUE, subjects.get(1).getSubjectId());
//		System.out.println("获取试题类型");
//		System.out.println(new Gson().toJson(list));
	}
}
