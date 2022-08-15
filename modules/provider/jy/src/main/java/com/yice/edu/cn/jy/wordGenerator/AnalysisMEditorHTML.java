package com.yice.edu.cn.jy.wordGenerator;

import com.yice.edu.cn.common.pojo.jy.prepareLessons.TextStyle;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.util.List;
import java.util.Objects;



/**
 * 
* @ClassName: AnalysisMEditorHTML  
* @Description: 解析HTML  
* @author xuchang  
* @date 2018年11月15日
 */
public class AnalysisMEditorHTML {
	
	private static final Logger log = LoggerFactory.getLogger(AnalysisMEditorHTML.class);
	
	private static String BOLD_LABEL = "strong";
	private static String ITALIC_LABEL = "em";
	private static String FONT_STYLE = "font-size: ";
	private static String UNDERLINE_CLASS="text-decoration:underline;";
	private static String MATH_CLASS="mathquill-embedded-latex";
	private static String ATTR_STYLE="style";
	private static String ATTR_CLASS="class";
	private static String FONT_FAMILY="font-family:";

	public static void writeContextToStream(String content, OutputStream out) {
		XWPFDocument document = new XWPFDocument();
		try {
			Document doc = Jsoup.parse(content.replaceAll("\\s*\r\n\\s*", ""));
			Element body = doc.body();
			//添加标题
			if(doc.title()!=null){
				OfficeProcessor.createXWPFParagraph(document, doc.getElementsByTag("title").first());
			}
			long a=Instant.now().toEpochMilli();
			body.childNodes().forEach(bc -> {
				if( !Objects.isNull(bc) && bc instanceof  Element){
					Element e=(Element) bc;
					// 添加一个段落
					XWPFParagraph p1 = OfficeProcessor.createXWPFParagraph(document, e);
					if(OfficeProcessor.titleTags.contains(e.tagName())){
						return;
					}
					try {
						// 内容
						TextStyle style = new TextStyle();
						formatHtmlTag(p1, e, style,document);
					} catch (CloneNotSupportedException | XmlException | InvalidFormatException e1) {
						// TODO Auto-generated catch block
						log.error("转换错误{}", e1.getMessage());
						System.out.println(e1.getMessage());
					}
				}
			});
			System.out.println(Thread.currentThread().getName()+" ---------用时:"+(Instant.now().toEpochMilli()-a));
			document.write(out);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			log.error("读取文件错误{}", e1.getMessage());
			System.out.println(e1.getMessage());
		} finally {
			try {
				document.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("文件关闭异常{}", e.getMessage());
				System.out.println(e.getMessage());
			}
		}
	}

	public static void formatHtmlTag(XWPFParagraph paragraph, Element e, TextStyle style,XWPFDocument document)
			throws CloneNotSupportedException, XmlException, InvalidFormatException {
			
			//写入图片
			if(Objects.equals("img", e.tagName())) {
				String picSrc = e.attr("src");
				String styleStr = e.attr(ATTR_STYLE);
				int widthIndx=styleStr.indexOf("width:");
				int heightIndx=styleStr.indexOf("height:");
				OfficeProcessor.writeImage(
						paragraph, 
						document, 
						picSrc,
						widthIndx!=-1?Integer.parseInt(styleStr.substring(widthIndx+6,styleStr.indexOf("px", widthIndx)).trim()):400,
						heightIndx!=-1?Integer.parseInt(styleStr.substring(heightIndx+7,styleStr.indexOf("px", heightIndx)).trim()):400
				);
				
			}else {
				TextStyle styleCopy = style.clone();
				writeStyle(styleCopy, e);
				List<Node> childNodes = e.childNodes();
				for (Node c : childNodes) {
					
					try {
						if (c instanceof TextNode) {
							writeText(paragraph, styleCopy, c);
							continue;
						}
						Element ele = (Element) c;
						
						formatHtmlTag(paragraph, ele, styleCopy,document);
					} catch (CloneNotSupportedException | XmlException | TransformerException | IOException | InvalidFormatException e1) {
						// TODO Auto-generated catch block
						log.error("转换错误{}", e1.getMessage());
						System.out.println(e1.getMessage());
					}
					
				}
				
			}
			

	}

	private static void writeText(XWPFParagraph paragraph, TextStyle style, Node e) throws XmlException, TransformerException, IOException {

		
		String text = (e instanceof TextNode)?((TextNode)e).text():((Element)e).text();
		if(StringUtils.isBlank(text)){
			return;
		}
		CTP ctp = paragraph.getCTP();
		if (style.isMathFormula()) {
			if(!text.startsWith("$")){
				text="$ "+text+"$";
			}
			String omml = MathFormulaUtils.convertLatex2OMML(text);
			CTOMath ctoMath1 = OfficeProcessor.getWordTextFromOMML(omml,style);
			CTOMath ctoMath = ctp.addNewOMath();
			if(ctoMath1!=null){
				ctoMath.set(ctoMath1);
			}
		}else {
			XWPFRun run = paragraph.createRun();
			if (style.isBold())
				run.setBold(true);
			if (style.isItalic())
				run.setItalic(true);
			if (style.isUnderline())
				run.setUnderline(UnderlinePatterns.SINGLE);
			if (style.getColor() != null)
				run.setColor(style.getColor());
			run.setFontSize(OfficeProcessor.transformPx2FontSize(style.getFontSize()));
			run.setText(text);
			run.setFontFamily("宋体");
//			run.setTextPosition(25);// 设置行间距
			paragraph.addRun(run);
		}

	}

	private static void writeStyle(TextStyle style, Element e) {
		String styleStr = e.attr(ATTR_STYLE);
		String classStr = e.attr(ATTR_CLASS);
		if (StringUtils.isNotBlank(styleStr)) {
			int fontIndex = styleStr.indexOf(FONT_STYLE);
			int underlineIndex = styleStr.indexOf(UNDERLINE_CLASS);
			
			int fontFamilyIndex=styleStr.indexOf(FONT_FAMILY);
			
			int colorIndex = styleStr.indexOf("rgb");
			int colorHexIndex=styleStr.indexOf("color:#");
			if (fontIndex !=-1) {
				int fontPxIndex = styleStr.indexOf("px", fontIndex);
				style.setFontSize(Integer.parseInt(styleStr.substring(fontIndex + 11, fontPxIndex)));
			}
			if (underlineIndex !=-1) {
				style.setUnderline(true);
			}
			if (colorIndex !=-1) {
				String[] ragArr = styleStr.substring(styleStr.indexOf("(")+1, styleStr.indexOf(")")).split(",");
				style.setColor(HexColorUtils.convertRGBToHex(Integer.parseInt(ragArr[0].trim()), Integer.parseInt(ragArr[1].trim()),
						Integer.parseInt(ragArr[2].trim())));
			}
			if(fontFamilyIndex!=-1) style.setFontFamily(styleStr.substring( fontFamilyIndex+1, styleStr.indexOf(";", fontFamilyIndex)));
			if(colorHexIndex!=-1) style.setColor(styleStr.substring(colorHexIndex+7, colorHexIndex+13));
			
		}
		if (Objects.equals(BOLD_LABEL, e.tagName())) {
			style.setBold(true);
		}
		if (Objects.equals(ITALIC_LABEL, e.tagName())) {
			style.setItalic(true);
		}
		if (classStr.indexOf(MATH_CLASS) !=-1 ) {
			style.setMathFormula(true);
		}

	}
	
	
	
}
