package com.yice.edu.cn.jy.source21.service;

import com.yice.edu.cn.jy.source21.constant.APIConstant;
import com.yice.edu.cn.common.pojo.jy.source21.model.document.Document;
import com.yice.edu.cn.common.pojo.jy.source21.model.document.Preview;
import com.yice.edu.cn.common.pojo.jy.source21.model.result.APIResult;
import com.yice.edu.cn.jy.source21.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API Document 服务
 * 
 * @author zhoushubing
 *
 */
public class APIDocumentService extends APICommonService {

	/**
	 * 加载下载地址，下载地址半小时有效，半小时后需重新获取
	 * 
	 * @param documents
	 *            需要获取下载地址的文档
	 */
	public void loadDocumentsDownurl(List<Document> documents) {
		if (documents == null || documents.size() == 0) {
			Log.info("documents is null.");
			return;
		}
		StringBuffer itemIds = new StringBuffer();
		Map<Long, Document> docMap = new HashMap<Long, Document>(documents.size());
		for (Document doc : documents) {
			itemIds.append(doc.getItemId()).append(",");
			docMap.put(doc.getItemId(), doc);
		}
		itemIds.deleteCharAt(itemIds.length() - 1);
		List<Document> urlDocList = apiRequest.requestList(APIConstant.URL_DOCUMENT_DETAIL, Document.class, "itemIds",
				itemIds.toString());
		for (Document urlDoc : urlDocList) {
			docMap.get(urlDoc.getItemId()).setDownloadUrl(urlDoc.getDownloadUrl());
		}
	}

	/**
	 * 获取文档下载地址，下载地址半小时有效，半小时后需重新获取
	 * 
	 * @param itemid
	 * @return
	 */
	public String getDocumentDownurl(long itemid) {
		List<Document> urlDocList = apiRequest.requestList(APIConstant.URL_DOCUMENT_DETAIL, Document.class, "itemIds",
				String.valueOf(itemid));
		return urlDocList.get(0).getDownloadUrl();
	}

	/**
	 * 获取试题列表
	 * 
	 * @param params
	 *            请求参数 {@value chapterId | knowledgeId = 必填项} {@value stage &
	 *            subjectId = 必填项}
	 * @return
	 */
	public APIResult<Document> getDocuments(APIRequestParams params) {
		if (params.chapterId == null && params.knowledgeId == null) {
			throw new RuntimeException("chapterId or knowledgeId is null.");
		}
		if (params.stage == null || params.subjectId == null) {
			throw new RuntimeException("stage and subjectId cannot be null.");
		}
		return apiRequest.request(APIConstant.URL_DOCUMENTS, Document.class, params);
	}

	public List<Preview> getPreview(Long _itemId) {
		return apiRequest.requestList(APIConstant.URL_DOCUMENT_PREVIEW, Preview.class, new APIRequestParams() {
			{
				this.itemId = _itemId;
			}
		});
	}
}
