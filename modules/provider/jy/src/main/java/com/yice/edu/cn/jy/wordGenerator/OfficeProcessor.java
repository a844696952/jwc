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
* @Description: word??????  
* @author xuchang  
* @date 2018???11???10???
 */
public class OfficeProcessor {

	/** 1?????? */
	public static final int ONE_UNIT = 567;
	/** ???????????? */
	private static final String STYLE_FOOTER = "footer";
	
	/** ????????????????????? */
	private static final String LANG_ZH_CN = "zh-CN";
	
	public static final List<String> titleTags=Arrays.asList("title","h1","h2","h3") ;
	
	
	/**
	 * 
	* @Title: getWordTextFromOMML  
	* @Description: ??????CTOMath?????? ??????Word  
	* @param @param omml
	* @param @param style
	* @param @return
	* @param @throws XmlException    ????????????  
	* @return CTOMath    ????????????  
	* @throws
	 */
	public static CTOMath getWordTextFromOMML(String omml,TextStyle style) throws XmlException {
		// ???????????????????????????????????????????????????
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
					// ????????????,??????docx ??????rpr????????????
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
	 * ?????????????????????
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
	* @Description: ????????????xml 
	* @param @param blipId  document????????????????????????
	* @param @param id		document??????????????????Id
	* @param @param width ???
	* @param @param height ???
	* @param @param paragraph    ?????? 
	* @return void    ????????????  
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
	 * ??????????????????????????????
	 */
	public String download(String url, String savePathAndName) throws Exception {
		url = url.replaceAll("http", "https");
		BufferedImage read = ImageIO.read(new URL(url));
		ImageIO.write(read, "png", new File(savePathAndName));
		return savePathAndName;
	}

	/**
	 * @Description ??????????????????
	 */
	public void setParagraphRunFontInfo(XWPFParagraph paragraph, XWPFRun pRun, String content, String fontFamily,
			String fontSize) {
		CTRPr pRpr = getRunCTRPr(paragraph, pRun);
		if (StringUtils.isNotBlank(content)) {
			pRun.setText(content);
		}
		// ????????????
		CTFonts fonts = pRpr.isSetRFonts() ? pRpr.getRFonts() : pRpr.addNewRFonts();
		fonts.setAscii(fontFamily);
		fonts.setEastAsia(fontFamily);
		fonts.setHAnsi(fontFamily);
		// ??????????????????
		CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
		sz.setVal(new BigInteger(fontSize));
		CTHpsMeasure szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr.addNewSzCs();
		szCs.setVal(new BigInteger(fontSize));
	}

	/**
	 * @Description: ??????XWPFRun???CTRPr
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
	 * @Description: ????????????????????????,??????=100 ??????=20
	 */
	public static void setParagraphSpacingInfo(XWPFParagraph paragraph, boolean isSpace, String before, String after,
			String beforeLines, String afterLines, boolean isLine, String line, STLineSpacingRule.Enum lineValue) {
		CTPPr pPPr = getParagraphCTPPr(paragraph);
		CTSpacing pSpacing = pPPr.getSpacing() != null ? pPPr.getSpacing() : pPPr.addNewSpacing();
		if (isSpace) {
			// ????????????
			if (before != null) {
				pSpacing.setBefore(new BigInteger(before));
			}
			// ????????????
			if (after != null) {
				pSpacing.setAfter(new BigInteger(after));
			}
			// ????????????
			if (beforeLines != null) {
				pSpacing.setBeforeLines(new BigInteger(beforeLines));
			}
			// ????????????
			if (afterLines != null) {
				pSpacing.setAfterLines(new BigInteger(afterLines));
			}
		}
		// ??????
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
	 * @Description: ????????????CTPPr
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
	 * @Description: ??????????????????
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
	* @Description: ????????????
	* @param @param docxDocument
	* @param @param e
	* @return XWPFParagraph    ????????????  
	* @throws
	 */
	public static XWPFParagraph createXWPFParagraph(XWPFDocument docxDocument,Element e) {
		XWPFParagraph paragraph=docxDocument.createParagraph();
		
		if (titleTags.contains(e.tagName())) {
			XWPFRun run = paragraph.createRun();
			run.setText(e.text());
			// ???????????????
			run.setTextPosition(35);
			if (e.tagName().equals("title")) {
				// ????????????
				paragraph.setAlignment(ParagraphAlignment.CENTER);
				// ??????
				run.setBold(true);
				// ????????????--????????????
				run.setColor("000000");
				// ??????
				run.setFontFamily("??????");
				// ????????????
				run.setFontSize(24);
			} else if (e.tagName().equals("h1")) {
				addCustomHeadingStyle(docxDocument, "?????? 1", 1);
				paragraph.setStyle("?????? 1");

				run.setBold(true);
				run.setColor("000000");
				run.setFontFamily("??????");
				run.setFontSize(20);
			} else if (e.tagName().equals("h2")) {
				addCustomHeadingStyle(docxDocument, "?????? 2", 2);
				paragraph.setStyle("?????? 2");

				run.setBold(true);
				run.setColor("000000");
				run.setFontFamily("??????");
				run.setFontSize(18);
			} else if (e.tagName().equals("h3")) {
				addCustomHeadingStyle(docxDocument, "?????? 3", 3);
				paragraph.setStyle("?????? 3");
				run.setBold(true);
				run.setColor("000000");
				run.setFontFamily("??????");
				run.setFontSize(16);
			} 
		}else {
			// ????????????
			paragraph.setAlignment(ParagraphAlignment.BOTH);
			// ???????????????567==1??????
			paragraph.setIndentationFirstLine(OfficeProcessor.ONE_UNIT);
			//400/20=20???
			setParagraphSpacingInfo(paragraph, true, null, null, null, null, true, "400",STLineSpacingRule.AT_LEAST);
		}
		return paragraph; 
		
		
//		paragraph.setPageBreak(true);// ????????????(ctrl+enter)
	}

	/**
	 * ??????????????? (word???1???????????????567)
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
	 * ??????????????????
	 *
	 * @param docx
	 *            XWPFDocument????????????
	 * @param text
	 *            ????????????
	 * @return ??????????????????????????????????????????????????????
	 * @throws XmlException
	 *             XML??????
	 * @throws IOException
	 *             IO??????
	 * @throws InvalidFormatException
	 *             ??????????????????
	 * @throws FileNotFoundException
	 *             ?????????????????????
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
	* @Description: ?????????????????????(????????????????????????????????????)  
	* @param @param docx    XWPFDocument????????????  
	* @return void   
	* @throws 
	 */
	public static void createDefaultFooter(final XWPFDocument docx) throws IOException {
		// TODO ?????????????????????
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
	 * ?????????????????????????????????????????????stackoverflow?????????
	 * 
	 * @param docxDocument
	 *            ????????????
	 * @param strStyleId
	 *            ????????????
	 * @param headingLevel
	 *            ????????????
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
	 * ????????????????????????????????? landscape??????
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
	* @Description: ???????????????word??????  
	* @param @param px
	* @return String    ????????????  
	* @throws
	 */
	public static int transformPx2FontSize(int px) {
		return (px*72)/96;
	}
	
	/**
	 * 
	* @Title: getImageStream  
	* @Description: ???????????????  
	* @param @param url
	* @return InputStream    ????????????  
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
            System.out.println("???????????????????????????????????????????????????" + url);
            e.printStackTrace();
        }
        return null;
    }
	
	
	
}
