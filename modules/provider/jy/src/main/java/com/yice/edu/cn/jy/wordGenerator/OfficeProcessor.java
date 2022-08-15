package com.yice.edu.cn.jy.wordGenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.jsoup.nodes.Element;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathPara;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyle;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHdrFtr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STStyleType;

import com.yice.edu.cn.common.pojo.jy.prepareLessons.TextStyle;

/**
 * 
* @ClassName: OfficeProcessor  
* @Description: word处理  
* @author xuchang  
* @date 2018年11月10日
 */
public class OfficeProcessor {

	/** 1厘米 */
	public static final int ONE_UNIT = 567;
	/** 页脚样式 */
	private static final String STYLE_FOOTER = "footer";
	
	/** 语言，简体中文 */
	private static final String LANG_ZH_CN = "zh-CN";
	
	public static final List<String> titleTags=Arrays.asList("title","h1","h2","h3") ;
	
	
	/**
	 * 
	* @Title: getWordTextFromOMML  
	* @Description: 得到CTOMath对象 渲染Word  
	* @param @param omml
	* @param @param style
	* @param @return
	* @param @throws XmlException    设定文件  
	* @return CTOMath    返回类型  
	* @throws
	 */
	public static CTOMath getWordTextFromOMML(String omml,TextStyle style) throws XmlException {
		// 判断公式值是否输入，为空会转换错误
		if (StringUtils.isBlank(omml)||omml.indexOf("{ }") > 0)
			return null;
		CTOMathPara ctOMathPara = CTOMathPara.Factory.parse(omml);
		CTOMath ctOMath = ctOMathPara.getOMathArray(0);

		// for making this to work with Office 2007 Word also, special font settings are
		// necessary
		XmlCursor xmlcursor = ctOMath.newCursor();
		while (xmlcursor.hasNextToken()) {
			XmlCursor.TokenType tokentype = xmlcursor.toNextToken();
			if (tokentype.isStart()) {
				if (xmlcursor.getObject() instanceof org.openxmlformats.schemas.officeDocument.x2006.math.CTR) {
					org.openxmlformats.schemas.officeDocument.x2006.math.CTR cTR = (org.openxmlformats.schemas.officeDocument.x2006.math.CTR) xmlcursor
							.getObject();
					CTRPr rpr2 = cTR.addNewRPr2();
					// 设置字体,对应docx 中的rpr标签生成
					rpr2.addNewRFonts().setAscii("Cambria Math");
					cTR.getRPr2().getRFonts().setHAnsi("Cambria Math");
					rpr2.addNewSz().setVal(new BigInteger(style.getFontSize()*2+""));
					rpr2.addNewSzCs().setVal(new BigInteger(style.getFontSize()*2+""));
				}
			}
		}
		return ctOMath;

	}

	/**
	 * 将图片写入文档
	 * 
	 * @throws InvalidFormatException
	 */
	public static void writeImage(XWPFParagraph paragraph, XWPFDocument document,String url,int width,int height) throws InvalidFormatException {
		String blipId = paragraph.getDocument()
				.addPictureData(getImageStream(url), Document.PICTURE_TYPE_PNG);
		createPicture(blipId, 
				document.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), 
				width,
				height, 
				paragraph
		);
	}

	/**
	 * 
	* @Title: createPicture  
	* @Description: 创建图片xml 
	* @param @param blipId  document中添加图片的位置
	* @param @param id		document中图片的标识Id
	* @param @param width 宽
	* @param @param height 高
	* @param @param paragraph    段落 
	* @return void    返回类型  
	* @throws
	 */
	private static void createPicture(String blipId, int id, int width, int height, XWPFParagraph paragraph) {
		final int EMU = 9525;
		width *= EMU;
		height *= EMU;
		
		CTInline inline = paragraph.createRun().getCTR().addNewDrawing().addNewInline();
		String picXml = "" + "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"
				+ "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
				+ "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
				+ "         <pic:nvPicPr>" + "            <pic:cNvPr id=\"" + id + "\" name=\"img_" + id + "\"/>"
				+ "            <pic:cNvPicPr/>" + "         </pic:nvPicPr>" + "         <pic:blipFill>"
				+ "            <a:blip r:embed=\"" + blipId
				+ "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
				+ "            <a:stretch>" + "               <a:fillRect/>" + "            </a:stretch>"
				+ "         </pic:blipFill>" + "         <pic:spPr>" + "            <a:xfrm>"
				+ "               <a:off x=\"0\" y=\"0\"/>" + "               <a:ext cx=\"" + width + "\" cy=\""
				+ height + "\"/>" + "            </a:xfrm>" + "            <a:prstGeom prst=\"rect\">"
				+ "               <a:avLst/>" + "            </a:prstGeom>" + "         </pic:spPr>"
				+ "      </pic:pic>" + "   </a:graphicData>" + "</a:graphic>";
		// CTGraphicalObjectData graphicData =
		// inline.addNewGraphic().addNewGraphicData();
		XmlToken xmlToken = null;
		try {
			xmlToken = XmlToken.Factory.parse(picXml);
		} catch (XmlException xe) {
			xe.printStackTrace();
		}
		inline.set(xmlToken);
		// graphicData.set(xmlToken);
		inline.setDistT(0);
		inline.setDistB(0);
		inline.setDistL(0);
		inline.setDistR(0);
		CTPositiveSize2D extent = inline.addNewExtent();
		extent.setCx(width);
		extent.setCy(height);
		CTNonVisualDrawingProps docPr = inline.addNewDocPr();
		docPr.setId(id);
		docPr.setName("docx_img_ " + id);
		docPr.setDescr("docx Picture");
	}

	/**
	 * 将网络图片保存到本地
	 */
	public String download(String url, String savePathAndName) throws Exception {
		url = url.replaceAll("http", "https");
		BufferedImage read = ImageIO.read(new URL(url));
		ImageIO.write(read, "png", new File(savePathAndName));
		return savePathAndName;
	}

	/**
	 * @Description 设置字体信息
	 */
	public void setParagraphRunFontInfo(XWPFParagraph paragraph, XWPFRun pRun, String content, String fontFamily,
			String fontSize) {
		CTRPr pRpr = getRunCTRPr(paragraph, pRun);
		if (StringUtils.isNotBlank(content)) {
			pRun.setText(content);
		}
		// 设置字体
		CTFonts fonts = pRpr.isSetRFonts() ? pRpr.getRFonts() : pRpr.addNewRFonts();
		fonts.setAscii(fontFamily);
		fonts.setEastAsia(fontFamily);
		fonts.setHAnsi(fontFamily);
		// 设置字体大小
		CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
		sz.setVal(new BigInteger(fontSize));
		CTHpsMeasure szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr.addNewSzCs();
		szCs.setVal(new BigInteger(fontSize));
	}

	/**
	 * @Description: 得到XWPFRun的CTRPr
	 */
	public CTRPr getRunCTRPr(XWPFParagraph p, XWPFRun pRun) {
		CTRPr pRpr = null;
		if (pRun.getCTR() != null) {
			pRpr = pRun.getCTR().getRPr();
			if (pRpr == null) {
				pRpr = pRun.getCTR().addNewRPr();
			}
		} else {
			pRpr = p.getCTP().addNewR().addNewRPr();
		}
		return pRpr;
	}
	
	

	/**
	 * @Description: 设置段落间距信息,一行=100 一磅=20
	 */
	public static void setParagraphSpacingInfo(XWPFParagraph paragraph, boolean isSpace, String before, String after,
			String beforeLines, String afterLines, boolean isLine, String line, STLineSpacingRule.Enum lineValue) {
		CTPPr pPPr = getParagraphCTPPr(paragraph);
		CTSpacing pSpacing = pPPr.getSpacing() != null ? pPPr.getSpacing() : pPPr.addNewSpacing();
		if (isSpace) {
			// 段前磅数
			if (before != null) {
				pSpacing.setBefore(new BigInteger(before));
			}
			// 段后磅数
			if (after != null) {
				pSpacing.setAfter(new BigInteger(after));
			}
			// 段前行数
			if (beforeLines != null) {
				pSpacing.setBeforeLines(new BigInteger(beforeLines));
			}
			// 段后行数
			if (afterLines != null) {
				pSpacing.setAfterLines(new BigInteger(afterLines));
			}
		}
		// 间距
		if (isLine) {
			if (line != null) {
				pSpacing.setLine(new BigInteger(line));
			}
			if (lineValue != null) {
				pSpacing.setLineRule(lineValue);
			}
		}
	}

	/**
	 * @Description: 得到段落CTPPr
	 */
	public static CTPPr getParagraphCTPPr(XWPFParagraph p) {
		CTPPr pPPr = null;
		if (p.getCTP() != null) {
			if (p.getCTP().getPPr() != null) {
				pPPr = p.getCTP().getPPr();
			} else {
				pPPr = p.getCTP().addNewPPr();
			}
		}
		return pPPr;
	}

	/**
	 * @Description: 设置段落对齐
	 */
	public void setParagraphAlignInfo(XWPFParagraph paragraph, ParagraphAlignment pAlign, TextAlignment vAlign) {
		if (pAlign != null) {
			paragraph.setAlignment(pAlign);
		}
		if (vAlign != null) {
			paragraph.setVerticalAlignment(vAlign);
		}
	}
	
	
	
	
	
	/**
	 * 
	* @Title: createXWPFParagraph  
	* @Description: 构建段落
	* @param @param docxDocument
	* @param @param e
	* @return XWPFParagraph    返回类型  
	* @throws
	 */
	public static XWPFParagraph createXWPFParagraph(XWPFDocument docxDocument,Element e) {
		XWPFParagraph paragraph=docxDocument.createParagraph();
		
		if (titleTags.contains(e.tagName())) {
			XWPFRun run = paragraph.createRun();
			run.setText(e.text());
			// 设置行间距
			run.setTextPosition(35);
			if (e.tagName().equals("title")) {
				// 对齐方式
				paragraph.setAlignment(ParagraphAlignment.CENTER);
				// 加粗
				run.setBold(true);
				// 设置颜色--十六进制
				run.setColor("000000");
				// 字体
				run.setFontFamily("宋体");
				// 字体大小
				run.setFontSize(24);
			} else if (e.tagName().equals("h1")) {
				addCustomHeadingStyle(docxDocument, "标题 1", 1);
				paragraph.setStyle("标题 1");

				run.setBold(true);
				run.setColor("000000");
				run.setFontFamily("宋体");
				run.setFontSize(20);
			} else if (e.tagName().equals("h2")) {
				addCustomHeadingStyle(docxDocument, "标题 2", 2);
				paragraph.setStyle("标题 2");

				run.setBold(true);
				run.setColor("000000");
				run.setFontFamily("宋体");
				run.setFontSize(18);
			} else if (e.tagName().equals("h3")) {
				addCustomHeadingStyle(docxDocument, "标题 3", 3);
				paragraph.setStyle("标题 3");
				run.setBold(true);
				run.setColor("000000");
				run.setFontFamily("宋体");
				run.setFontSize(16);
			} 
		}else {
			// 对齐方式
			paragraph.setAlignment(ParagraphAlignment.BOTH);
			// 首行缩进：567==1厘米
			paragraph.setIndentationFirstLine(OfficeProcessor.ONE_UNIT);
			//400/20=20磅
			setParagraphSpacingInfo(paragraph, true, null, null, null, null, true, "400",STLineSpacingRule.AT_LEAST);
		}
		return paragraph; 
		
		
//		paragraph.setPageBreak(true);// 段前分页(ctrl+enter)
	}

	/**
	 * 设置页边距 (word中1厘米约等于567)
	 * 
	 * @param document
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public static void setDocumentMargin(XWPFDocument document, String left, String top, String right, String bottom) {
		CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
		CTPageMar ctpagemar = sectPr.addNewPgMar();
		if (StringUtils.isNotBlank(left)) {
			ctpagemar.setLeft(new BigInteger(left));
		}
		if (StringUtils.isNotBlank(top)) {
			ctpagemar.setTop(new BigInteger(top));
		}
		if (StringUtils.isNotBlank(right)) {
			ctpagemar.setRight(new BigInteger(right));
		}
		if (StringUtils.isNotBlank(bottom)) {
			ctpagemar.setBottom(new BigInteger(bottom));
		}
	}

	/**
	 * 创建默认页眉
	 *
	 * @param docx
	 *            XWPFDocument文档对象
	 * @param text
	 *            页眉文本
	 * @return 返回文档帮助类对象，可用于方法链调用
	 * @throws XmlException
	 *             XML异常
	 * @throws IOException
	 *             IO异常
	 * @throws InvalidFormatException
	 *             非法格式异常
	 * @throws FileNotFoundException
	 *             找不到文件异常
	 */
	public static void createDefaultHeader(final XWPFDocument docx, final String text) throws IOException {
		CTP ctp = CTP.Factory.newInstance();
		XWPFParagraph paragraph = new XWPFParagraph(ctp, docx);
		ctp.addNewR().addNewT().setStringValue(text);
		ctp.addNewR().addNewT().setSpace(SpaceAttribute.Space.PRESERVE);
		CTSectPr sectPr = docx.getDocument().getBody().isSetSectPr() ? docx.getDocument().getBody().getSectPr()
				: docx.getDocument().getBody().addNewSectPr();
		XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(docx, sectPr);
		XWPFHeader header = policy.createHeader(STHdrFtr.DEFAULT, new XWPFParagraph[] { paragraph });
		header.setXWPFDocument(docx);
	}

	/**
	 * @throws IOException 
	 * 
	* @Title: createDefaultFooter  
	* @Description: 创建默认的页脚(该页脚主要只居中显示页码)  
	* @param @param docx    XWPFDocument文档对象  
	* @return void   
	* @throws 
	 */
	public static void createDefaultFooter(final XWPFDocument docx) throws IOException {
		// TODO 设置页码起始值
		CTP pageNo = CTP.Factory.newInstance();
		XWPFParagraph footer = new XWPFParagraph(pageNo, docx);
		CTPPr begin = pageNo.addNewPPr();
		begin.addNewPStyle().setVal(STYLE_FOOTER);
		begin.addNewJc().setVal(STJc.CENTER);
		pageNo.addNewR().addNewFldChar().setFldCharType(STFldCharType.BEGIN);
		pageNo.addNewR().addNewInstrText().setStringValue("PAGE   \\* MERGEFORMAT");
		pageNo.addNewR().addNewFldChar().setFldCharType(STFldCharType.SEPARATE);
		CTR end = pageNo.addNewR();
		CTRPr endRPr = end.addNewRPr();
		endRPr.addNewNoProof();
		endRPr.addNewLang().setVal(LANG_ZH_CN);
		end.addNewFldChar().setFldCharType(STFldCharType.END);
		CTSectPr sectPr = docx.getDocument().getBody().isSetSectPr() ? docx.getDocument().getBody().getSectPr()
				: docx.getDocument().getBody().addNewSectPr();
		XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(docx, sectPr);
		policy.createFooter(STHdrFtr.DEFAULT, new XWPFParagraph[] { footer });
	}

	/**
	 * 增加自定义标题样式。这里用的是stackoverflow的源码
	 * 
	 * @param docxDocument
	 *            目标文档
	 * @param strStyleId
	 *            样式名称
	 * @param headingLevel
	 *            样式级别
	 */
	private static void addCustomHeadingStyle(XWPFDocument docxDocument, String strStyleId, int headingLevel) {

		CTStyle ctStyle = CTStyle.Factory.newInstance();
		ctStyle.setStyleId(strStyleId);

		CTString styleName = CTString.Factory.newInstance();
		styleName.setVal(strStyleId);
		ctStyle.setName(styleName);

		CTDecimalNumber indentNumber = CTDecimalNumber.Factory.newInstance();
		indentNumber.setVal(BigInteger.valueOf(headingLevel));

		// lower number > style is more prominent in the formats bar
		ctStyle.setUiPriority(indentNumber);

		CTOnOff onoffnull = CTOnOff.Factory.newInstance();
		ctStyle.setUnhideWhenUsed(onoffnull);

		// style shows up in the formats bar
		ctStyle.setQFormat(onoffnull);

		// style defines a heading of the given level
		CTPPr ppr = CTPPr.Factory.newInstance();
		ppr.setOutlineLvl(indentNumber);
		ctStyle.setPPr(ppr);

		XWPFStyle style = new XWPFStyle(ctStyle);

		// is a null op if already defined
		XWPFStyles styles = docxDocument.createStyles();

		style.setType(STStyleType.PARAGRAPH);
		styles.addStyle(style);

	}

	/**
	 * 设置页面大小及纸张方向 landscape横向
	 * 
	 * @param document
	 * @param width
	 * @param height
	 * @param stValue
	 */
	public void setDocumentSize(XWPFDocument document, String width, String height, STPageOrientation.Enum stValue) {
		CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
		CTPageSz pgsz = sectPr.isSetPgSz() ? sectPr.getPgSz() : sectPr.addNewPgSz();
		pgsz.setH(new BigInteger(height));
		pgsz.setW(new BigInteger(width));
		pgsz.setOrient(stValue);
	}
	
	/**
	 * 
	* @Title: transformPx2FontSize  
	* @Description: 像素转换为word大小  
	* @param @param px
	* @return String    返回类型  
	* @throws
	 */
	public static int transformPx2FontSize(int px) {
		return (px*72)/96;
	}
	
	/**
	 * 
	* @Title: getImageStream  
	* @Description: 获取图片流  
	* @param @param url
	* @return InputStream    返回类型  
	* @throws
	 */
	private static InputStream getImageStream(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                return inputStream;
            }
        } catch (IOException e) {
            System.out.println("获取网络图片出现异常，图片路径为：" + url);
            e.printStackTrace();
        }
        return null;
    }
	
	
	
}
