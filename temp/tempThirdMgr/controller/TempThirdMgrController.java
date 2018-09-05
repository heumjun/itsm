package com.web.temp.tempThirdMgr.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.web.common.command.CommandMap;
import com.web.common.controller.CommonController;
import com.web.temp.tempSecondMgr.service.TempSecondMgrService;
import com.web.temp.tempThirdMgr.service.TempThirdMgrService;

@Controller
public class TempThirdMgrController extends CommonController
{
	protected Log		log	= LogFactory.getLog(TempThirdMgrController.class);
	
	/**
	 * TempService 등록
	 */
	@Resource(name = "temp.tempThirdMgr.tempThirdMgrService")
	private TempThirdMgrService tempThirdMgrService;
	
	@Resource(name = "temp.tempSecondMgr.tempSecondMgrService")
	private TempSecondMgrService tempSecondMgrService;

	@RequestMapping(value = "temp.tempThirdMgr.rdTest.do")
	public ModelAndView rdTest(CommandMap commandMap)
	{
		return getUserRoleAndLink(commandMap);
	}
	
	@RequestMapping(value = "temp.tempThirdMgr.popUpReportDesign.do")
	public String popUpNoticeRegiste(CommandMap commandMap)
	{
		/*ModelAndView mav = new ModelAndView(
				Constants.TEMP + "/tempThirdMgr" + Constants.POPUP + commandMap.get(Constants.JSP_NAME));
		mav.addAllObjects(commandMap.getMap());

		return mav;*/
		
		return "temp/tempThirdMgr/popUp/popUpReportDesign";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/reportDegisnTest.do", method = RequestMethod.GET)
	public void sendData(HttpServletResponse response, CommandMap commandMap) throws Exception 
	{
		
		PrintWriter out = response.getWriter();
		
		log.debug(
				"======================================          START         ======================================");
		
		List<Map<String, Object>> resultList = tempSecondMgrService.getChartData(commandMap);
		
		Map<String, Object> md = new HashMap<String, Object>();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<Map<String, Object>> dsList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>();
		
		paramMap.put("a1", "11");
		paramMap.put("a2", "22");
		paramMap.put("a3", "33");
		paramMap.put("a4", "44");
		paramMap.put("a5", "55");
		
		for(int idx=0; idx<10; idx++) {
			
			Map<String, Object> dataMap = new HashMap<String, Object>();
			
			dataMap.put("a16", idx);
			dataMap.put("a17", idx);
			dataMap.put("a18", idx);
			dataMap.put("a19", idx);
			dataMap.put("a20", idx);
			dataMap.put("a21", idx);
			dataMap.put("a22", idx);
			
			dsList.add(idx, dataMap);
			
			for(int j=0; j<5; j++) {
				
				Map<String, Object> recodeMap = new HashMap<String, Object>();
				
				recodeMap.put("a30", j);
				recodeMap.put("a31", j);
				recodeMap.put("a32", j);
				recodeMap.put("a33", j);
				recodeMap.put("a34", j);
				
				rsList.add(j, recodeMap);
			}
			
		}
		
		md.put("paramMap", paramMap);
		md.put("datasetList", dsList);
		md.put("recordsetList", rsList);
		
		log.debug(
				"======================================           END          ======================================\n");

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setIgnoringElementContentWhitespace(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();
			doc.setXmlVersion("1.0");
			
			Element rootNode = doc.createElement("root");
			doc.appendChild(rootNode);
			
			for (int i = 0; i < dsList.size(); i++) {
				
				Element eItem = doc.createElement("Detail");
				rootNode.appendChild(eItem);
				
				Map<String, Object> map = (Map<String, Object>)resultList.get(i);
                Set set = map.keySet();
                Iterator setit = set.iterator();
                
                while( setit.hasNext() ){
                	
                    String key   = (String)setit.next();
                    Object value = map.get(key);
 
                    Element eKey = doc.createElement(key);
                    eItem.appendChild(eKey);
                     
                    Text cdata = doc.createTextNode(value==null?"":value.toString());      
                    eKey.appendChild(cdata);
                    
                }
                
			}
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(out);
			//StreamResult result = new StreamResult(System.out);
			
			TransformerFactory tff = TransformerFactory.newInstance();
			Transformer tf = tff.newTransformer();
			
			tf.setOutputProperty("method", "xml");
			tf.setOutputProperty("encoding", response.getCharacterEncoding());
			//tf.setOutputProperty("encoding", "UTF-8");
			tf.setOutputProperty("indent", "yes");
			tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			tf.transform(source, result);
			
			out.flush();
			out.close();
		} catch (Exception e) {
			throw e;
		}
	}

}
