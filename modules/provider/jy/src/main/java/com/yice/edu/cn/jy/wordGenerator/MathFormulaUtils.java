package com.yice.edu.cn.jy.wordGenerator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.io.SAXReader;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * 
 * @ClassName: MathFormulaUtils
 * @Description: OMML、mathml、latex互相转换 
 * 				1.需要用到office的OMML2MML.XSL，提供了将OMML转为MathML的xml对象定义 
 * 				(OMML:Office MathML在office2007之后版本所编辑的公式对象便是OMML,数学标记语言;
 *               Mathml:数学标记语言（Mathematical MarkupLanguage），
 *               是一种基于XML的标准，互联网上书写数学符号和公式的置标语言
 *               Office文档分为word(2007之前)与wordx(2007之后)这两种类型,2007之后使用的是Office OpenXml规范定义格式文件
 *               ) 
 *               由于POI支持二进制与Office Open Xml文档
 * @author xuchang
 * @date 2018年11月10日
 */
public class MathFormulaUtils {
	
	private static final Logger log = LoggerFactory.getLogger(AnalysisMEditorHTML.class);
	

	
	// mathml->latex标准文件路径
	private static String MMLTEX_PATH = "/mmltex/mmltex.xsl";
	// mathml->omml标准文件路径
	private static String MML2OMML_PATH = "/mml2omml/MML2OMML.XSL";
	// omml->mathml标准文件路径
	private static String OMM2MML_PATH = "/omm2mml/OMML2MML.XSL";
	// 解析xsl:include里文件的父路径
	private static String MMLTEX_INCLUDE_PATH = "/mmltex/";

	private static String MATHML3DTD_PATH = "/mml2omml/mathml3/";

	private static Pattern compile = Pattern.compile("&.*?;");
	
//	private static String MATHML3_DOCTYPE="<!DOCTYPE math SYSTEM \"mathml3.dtd\">";
	private static String MATHML3_DOCTYPE="<!DOCTYPE math PUBLIC \"-//W3C//DTD MathML 3.0//EN\" \"http://www.w3.org/Math/DTD/mathml3/mathml3.dtd\">";
	
	private static TransformerFactory tFacactory = TransformerFactory.newInstance();
	
	private static SAXReader reader=null;
	
	static {
		//生成SAXReader ，设置dtd读取方式，避免远程读取延时太高
		DocumentFactory dtf = new DocumentFactory();
		reader = new SAXReader(dtf);
		reader.setValidation(false);
		reader.setEntityResolver(new EntityResolver() {
	
			@Override
			public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
				// TODO Auto-generated method stub
				if (systemId != null) {
	
					int lastPathSeparator = systemId.lastIndexOf('/');
					String sourceFile = "";
					if (lastPathSeparator != -1) {
						sourceFile = systemId.substring(lastPathSeparator + 1);
					} else {
						sourceFile = systemId;
					}
					InputSource source = new InputSource(
							MathFormulaUtils.class.getResourceAsStream(MATHML3DTD_PATH + sourceFile));
					source.setPublicId(publicId);
					source.setSystemId(systemId);
					return source;
				}
				return null;
			}
		});	
		
	}

	/**
	 * 
	 * @Title: xslConvert 
	 * @Description: xsl转换器
	 * @param ommlStr 需要转化的str 
	 * @param xslpath 标准xsl文件路径,比如需要omml2mml omml转mml标准xsl文件路径
	 * @param uriResolver 如果需要解析document()、xsl:import 或xsl:include 则需要设置该解析器,实现URIResolver,对应的xsl:import等文件的所属路径用于创建inputStream
	 * @return String 使用xsl生成对应的xml字符串 
	 * @throws
	 */
	public static String xslConvert(String ommlStr, String xslpath, URIResolver uriResolver) {
		if (ommlStr==null) return "";
		// 设置在转换过程中默认用于解析 document()、xsl:import 或 xsl:include 中所使用的 URI 的对象。
		if (uriResolver != null)
			tFacactory.setURIResolver(uriResolver);
		StreamSource xslSource = new StreamSource(MathFormulaUtils.class.getResourceAsStream(xslpath));
		StringWriter writer = new StringWriter();
		try {
			Source source=null;
			Transformer t = tFacactory.newTransformer(xslSource);
			// 判断是否存在需要引用实体的字段
			Matcher matcher = compile.matcher(ommlStr);
			// DOCTYPE实体引用，用于解析&xxxxx;
			 if(matcher.find()) {
				 ommlStr=loadDocument(MATHML3_DOCTYPE+ommlStr).replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<!DOCTYPE math PUBLIC \"-//W3C//DTD MathML 3.0//EN\" \"http://www.w3.org/Math/DTD/mathml3/mathml3.dtd\"><math xmlns=\"http://www.w3.org/1998/Math/MathML\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" mathvariant=\"italic\" display=\"inline\">", "<math xmlns=\"http://www.w3.org/1998/Math/MathML\"  mathvariant='italic' display='inline'>");
				 source=new StreamSource(new StringReader(ommlStr));
			 }else {
				 source=new StreamSource(new StringReader(ommlStr));
			 }
			
			Result result = new StreamResult(writer);
			t.transform(source, result);
		} catch (Exception e) {
			System.out.println(e);
			log.error(e.getMessage(), e);
		}
		return writer.getBuffer().toString();
	}

	/**
	 * 
	 * @Title: convertMathml2Latex 
	 * @Description: 将mathml转为latex 
	 * @param context 
	 * @return String latex str 
	 * @throws
	 */
	public static String convertMML2Latex(String context) {
		// 截取掉xml头节点
		context = context.substring(context.indexOf("?>") + 2, context.length());
		// 设置URIResolver解析器
		// 实现URIResolver接口的对象可由处理器进行调用，以将 document()、xsl:import 或 xsl:include 中使用的 URI
		// 转换为 Source 对象
		URIResolver resolver = new URIResolver() {
			// href 项目resource绝对路径或者相对路径
			// base绝对路径，可有可无
			@Override
			public Source resolve(String href, String base) throws TransformerException {
				InputStream inputStream = MathFormulaUtils.class.getResourceAsStream(MMLTEX_INCLUDE_PATH + href);
				return new StreamSource(inputStream);
			}
		};
		// 用于解析latex的xsl标准文件
		String latex = xslConvert(context, MMLTEX_PATH, resolver);
		// if(latex != null && latex.length() > 1){
		// latex = latex.substring(1, latex.length() - 1);
		// }
		return latex;
	}

	/**
	 * 
	 * @Title: convertOMML2MML 
	 * @Description: OMML转为mathml 
	 * @param  xml 
	 * @return String mathml str 
	 * @throws
	 */
	public static String convertOMML2MML(String context) {
		String result = xslConvert(context, OMM2MML_PATH, null);
		return result;
	}

	/**
	 * 
	 * @Title: convertLatex2OMML 
	 * @Description: 转换latex公式并写入文档 $wxTempConfig$
	 * @param latex 
	 * @return String 返回类型 
	 * @throws
	 */
	public static String convertLatex2OMML(String latex) {
		//fmath latex -> mathml有不兼容的情况，只支持其中某一种写法
		String mathML=transferMathMLProcessor(latex);
		StringBuilder sb = new StringBuilder();
		sb.append(mathML.replaceFirst("<math", "<math xmlns=\"http://www.w3.org/1998/Math/MathML\" "));
		String result = xslConvert(sb.toString(), MML2OMML_PATH, null);
		return result;
	}

	

	/**
	 * 
	 * @Title: latexImage 
	 * @Description: latex公式转图片 
	 * @param formulaStr latex格式字段
	 * @param path 
	 * @throws IOException 
	 * @return String 返回类型 
	 */
	public static String latexImage(String formulaStr, String path) throws IOException {
		TeXFormula tf = new TeXFormula(formulaStr);
		TeXIcon ti = tf.createTeXIcon(TeXConstants.STYLE_DISPLAY, 15);
		BufferedImage bimg = new BufferedImage(ti.getIconWidth(), ti.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = bimg.createGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, ti.getIconWidth(), ti.getIconHeight());
		JLabel jl = new JLabel();
		jl.setForeground(new Color(0, 0, 0));
		ti.paintIcon(jl, g2d, 0, 0);
		File out = new File(path);
		ImageIO.write(bimg, "png", out);
		return path;
	}


	/**
	 * 
	* @Title: loadDocument  
	* @Description: 将xml中&xxx; 实体引用转换成对应的字符  
	* @param @param documentContent
	* @param @return 
	* @param @throws DocumentException    
	* @return String    mathml字符串  
	* @throws
	 */
	private static String loadDocument(String documentContent) throws DocumentException {
	
		InputSource source = new InputSource(new StringReader(documentContent));
		source.setEncoding("utf-8");
		Document doc = reader.read(source);
		return doc.asXML();
	}
	
	/**
	 * 
	* @Title: transferMathMLProcessor  
	* @Description: latex转mathml  
	* @param @param latex
	* @param @return    
	* @return String    mathml字符串    
	* @throws
	 */
	private static String transferMathMLProcessor(String latex) {
		
		String mathML="";
		//测试发现\overline上划线不兼容
		int overlineIndex=latex.indexOf("\\overline");
		if(overlineIndex!=-1) {
			mathML="<math><mover accent=\"false\"><mrow><mn>"+latex.substring(overlineIndex+10, latex.indexOf("}", overlineIndex))+"</mn></mrow><mo>¯</mo></mover></math>";
		}else {
			if(latex.indexOf("^1/_2")!=-1) {
				latex=latex.replace("^1/_2", "1/2");
			}
			try {
				mathML = fmath.conversion.ConvertFromLatexToMathML.convertToMathML(latex);
			}catch(Exception e){
				log.error("latex转化mathML异常,latex:{}\n异常:{}",latex,e.getMessage());
			}
			
		}
		return mathML;
		
	}
	
	/**
	 * 测试
	 */
//	public static void main(String[] args) throws IOException, DocumentException, XmlException {
//		FileInputStream in = new FileInputStream(new File("C:\\Users\\DELL\\Desktop\\测试.docx"));
//		XWPFDocument xwpfDocument = new XWPFDocument(in);
//		CTDocument1 document = xwpfDocument.getDocument();
//		String xmlText = document.xmlText();
//		// dom4j解析器的初始化
//		SAXReader reader = new SAXReader(new DocumentFactory());
//		Map<String, String> map = new HashMap<String, String>();
//		// map.put("wp","http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing");
//		map.put("m", "http://schemas.openxmlformats.org/officeDocument/2006/math");
//		// xml文档的namespace设置
//		reader.getDocumentFactory().setXPathNamespaceURIs(map);
//		InputSource source = new InputSource(new StringReader(xmlText));
//		source.setEncoding("utf-8");
//		Document doc = reader.read(source);
//		Element root = doc.getRootElement();
//		// 用xpath得到OMML节点
//		List<Element> es = (List<Element>) root.selectNodes("//m:oMath");
//		// 转为xml
//		es.forEach(e -> {
//			String omml = e.asXML();
//			String mml = convertOMML2MML(omml);
//			String latex = convertMML2Latex(mml);
//			System.out.println(mml);
//		});
//
//	}
	
}
