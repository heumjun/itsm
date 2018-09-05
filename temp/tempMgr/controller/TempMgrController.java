package com.web.temp.tempMgr.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.controller.CommonController;
import com.web.temp.tempMgr.service.TempMgrService;

 /** 
 * @파일명		: TempMgrController.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 8. 9. 
 * @작성자		: bhLee
 * @설명
 * <pre>
 * 		템플릿(First)
 * </pre>
 */
@Controller
public class TempMgrController extends CommonController
{
	/**
	 * TempService 등록
	 */
	@Resource(name = "temp.tempMgr.tempMgrService")
	private TempMgrService tempMgrService;

	 /** 
	 * @메소드명	: tempMgr
	 * @날짜		: 2018. 8. 9.
	 * @작성자		: bhLee
	 * @설명
	 * <pre>
	 *		
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "temp.tempMgr.tempFirst.do")
	public ModelAndView tempMgr(CommandMap commandMap)
	{
		return getUserRoleAndLink(commandMap);
	}


	 /** 
	 * @메소드명	: referRoomList
	 * @날짜		: 2018. 8. 9.
	 * @작성자		: bhLee
	 * @설명
	 * <pre>
	 *		템플릿(First) list
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "temp.tempMgr.tempMgrList.do")
	public @ResponseBody Map<String, Object> tempList(CommandMap commandMap)
	{
		return tempMgrService.tempList(commandMap);
	}

	 /** 
	 * @메소드명	: popUpNoticeRegiste
	 * @날짜		: 2018. 8. 10.
	 * @작성자		: bhLee
	 * @설명
	 * <pre>
	 *		템플릿 등록팝업 호출
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "temp.tempMgr.popUpTempMgrRegiste.do")
	public ModelAndView popUpNoticeRegiste(CommandMap commandMap)
	{
		ModelAndView mav = new ModelAndView(
				Constants.TEMP + Constants.TEMP_REG + Constants.POPUP + commandMap.get(Constants.JSP_NAME));
		mav.addAllObjects(commandMap.getMap());

		return mav;
	}

	 /** 
	 * @메소드명	: popUpTempMgrUpdate
	 * @날짜		: 2018. 8. 10.
	 * @작성자		: bhLee
	 * @설명
	 * <pre>
	 *		템플릿 수정팝업호출
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "temp.tempMgr.popUpTempMgrUpdate.do")
	public ModelAndView popUpTempMgrUpdate(CommandMap commandMap)
	{
		ModelAndView mav = new ModelAndView(
				Constants.TEMP + Constants.TEMP_REG + Constants.POPUP + commandMap.get(Constants.JSP_NAME));
		mav.addAllObjects(commandMap.getMap());

		// SEQ 유무에 따른 수정
		mav.addObject("tempMgr", tempMgrService.getTempMgr(commandMap));

		return mav;
	}

	 /** 
	 * @메소드명	: popUpTempMgrAttachList
	 * @날짜		: 2018. 8. 10.
	 * @작성자		: bhLee
	 * @설명
	 * <pre>
	 *		템플릿 그리드 클릭시 첨부파일 목록
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "temp.tempMgr.popUpTempMgrAttachList.do")
	public @ResponseBody Map<String, Object> popUpTempMgrAttachList(CommandMap commandMap)
	{
		return tempMgrService.popUpTempMgrAttachList(commandMap);
	}

	 /** 
	 * @메소드명	: saveTempMgr
	 * @날짜		: 2018. 8. 10.
	 * @작성자		: bhLee
	 * @설명
	 * <pre>
	 *		템플릿 등록/수정
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "temp.tempMgr.saveTempMgr.do")
	public @ResponseBody Map<String, String> saveTempMgr(CommandMap commandMap) throws Exception
	{
		return tempMgrService.saveTempMgr(commandMap);
		
	}

	 /** 
	 * @메소드명	: saveTempMgrAttachFile
	 * @날짜		: 2018. 8. 10.
	 * @작성자		: bhLee
	 * @설명
	 * <pre>
	 *		템플릿 첨부파일 업로드
	 * </pre>
	 * @param commandMap
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "temp.tempMgr.saveTempMgrAttachFile.do")
	public void saveTempMgrAttachFile(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		tempMgrService.saveTempMgrAttachFile(commandMap, request, response);
	}

	 /** 
	 * @메소드명	: refAttachFileDownload
	 * @날짜		: 2018. 8. 10.
	 * @작성자		: bhLee
	 * @설명
	 * <pre>
	 *		템플릿 첨부파일 다운로드
	 * </pre>
	 * @param commandMap
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "temp.tempMgr.refAttachFileDownload.do", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public void refAttachFileDownload(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{

		List<Map<String, Object>> resultList = tempMgrService.selectFileInfo(commandMap);

		// 첩부파일 다운로드 목록이 1개 이상일때 ZIP으로 일괄 다운로드
		if (resultList.size() > 1)
		{
			int bufferSize = 1024 * 2;
			String ouputName = URLEncoder.encode((String) commandMap.get("p_title"), "UTF-8");
			
			ZipOutputStream zos = null;
			
			try {
                
			    if (request.getHeader("User-Agent").indexOf("MSIE 5.5") > -1) {
			        response.setHeader("Content-Disposition", "filename=\"" + ouputName + ".zip" + "\";");
			    } else {
			        response.setHeader("Content-Disposition", "attachment; filename=\"" + ouputName + ".zip" + "\";");
			    }
			    response.setHeader("Content-Transfer-Encoding", "binary");
			                
			    OutputStream os = response.getOutputStream();
			    zos = new ZipOutputStream(os); // ZipOutputStream
			    zos.setLevel(8); // 압축 레벨 - 최대 압축률은 9, 디폴트 8
			    BufferedInputStream bis = null;
			    
			    for (Map<String, Object> resultMap : resultList) 
			    {
			        File sourceFile = new File((String) resultMap.get("FILE_PATH"));
			                        
			        bis = new BufferedInputStream(new FileInputStream(sourceFile));
			        ZipEntry zentry = new ZipEntry((String) resultMap.get("ORG_FILE_NAME"));
			        zentry.setTime(sourceFile.lastModified());
			        zos.putNextEntry(zentry);
			        
			        byte[] buffer = new byte[bufferSize];
			        int cnt = 0;
			        while ((cnt = bis.read(buffer, 0, bufferSize)) != -1) {
			            zos.write(buffer, 0, cnt);
			        }
			        zos.closeEntry();
			    }
			               
			    zos.close();
			    bis.close();
			                
			                
			} catch(Exception e){
			    e.printStackTrace();
			}
		}
		// 첨부파일 다운로드 목록이 1개일때
		else
		{
			for (Map<String, Object> resultMap : resultList)
			{

				String filePath = (String) resultMap.get("FILE_PATH");
				String originalFileName = (String) resultMap.get("ORG_FILE_NAME");
				
				File file = new File(filePath);
				
				boolean isExists = file.exists(); 
				if(isExists) 
				{ 
					byte fileByte[] = FileUtils.readFileToByteArray(new File(filePath));

					response.setContentType("application/octet-stream");
					response.setContentLength(fileByte.length);
					response.setHeader("Content-Disposition",
							"attachment; fileName=\"" + URLEncoder.encode(originalFileName, "UTF-8") + "\";");
					response.setHeader("Content-Transfer-Encoding", "binary");
					response.getOutputStream().write(fileByte);

					response.getOutputStream().flush();
					response.getOutputStream().close();
				} 
				else 
				{ 
					response.setCharacterEncoding("utf-8");
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().println("<script type='text/javascript'>"
							+ "alert('첨부파일이 존재하지 않습니다.');"
							//+ "location.href='#';"
							+ "history.back();"
							+ "</script>");
					response.getWriter().flush();
					response.getWriter().close();
					
				}
			}
		}

	}

	 /** 
	 * @메소드명	: refAttachFileDelete
	 * @날짜		: 2018. 8. 10.
	 * @작성자		: bhLee
	 * @설명
	 * <pre>
	 *		템플릿 첨부 삭제
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "temp.tempMgr.refAttachFileDelete.do")
	public @ResponseBody Map<String, String> refAttachFileDelete(CommandMap commandMap) throws Exception
	{
		return tempMgrService.refAttachFileDelete(commandMap);
	}
	
	
	 /** 
	 * @메소드명	: tempMgrDelete
	 * @날짜		: 2018. 8. 10.
	 * @작성자		: bhLee
	 * @설명
	 * <pre>
	 *		템플릿 삭제
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "temp.tempMgr.tempMgrDelete.do")
	public @ResponseBody Map<String, String> tempMgrDelete(CommandMap commandMap) throws Exception
	{
		return tempMgrService.tempMgrDelete(commandMap);
	}
	
	 /** 
	 * @메소드명	: popUpTempInfoExcelImport
	 * @날짜		: 2018. 8. 10.
	 * @작성자		: bhLee
	 * @설명
	 * <pre>
	 *		템플릿 엑셀일괄등록 첨부등록 팝업
	 * </pre>
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "temp.tempMgr.popUpTempInfoExcelImport.do")
	public ModelAndView popUpTempInfoExcelImport(CommandMap commandMap) {
		ModelAndView mav = new ModelAndView(
				Constants.TEMP + Constants.TEMP_REG + Constants.POPUP + commandMap.get(Constants.JSP_NAME));
		mav.addAllObjects(commandMap.getMap());
		return mav;
	}
	
	 /** 
	 * @메소드명	: popUpTempInfoExcelImportAction
	 * @날짜		: 2018. 8. 10.
	 * @작성자		: bhLee
	 * @설명
	 * <pre>
	 *		템플릿 엑셀일괄등록
	 * </pre>
	 * @param file
	 * @param commandMap
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "temp.tempMgr.popUpTempInfoExcelImportAction.do")
	public void popUpTempInfoExcelImportAction(@RequestParam(value = "fileName") MultipartFile file, CommandMap commandMap, HttpServletResponse response) throws Exception 
	{
		commandMap.put("file", file);
		tempMgrService.popUpTempInfoExcelImportAction(commandMap, response);
	}
	
	 /** 
	 * @메소드명	: tempMgrListExcelExport
	 * @날짜		: 2018. 8. 13.
	 * @작성자		: bhLee
	 * @설명
	 * <pre>
	 *		템플릿 엑셀저장
	 * </pre>
	 * @param commandMap
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "temp.tempMgr.tempMgrListExcelExport.do")
	public View tempMgrListExcelExport(CommandMap commandMap, Map<String, Object> modelMap) throws Exception
	{
		return tempMgrService.tempMgrListExcelExport(commandMap, modelMap);
	}
	
	 /** 
	 * @메소드명	: tempMgrSendEmail
	 * @날짜		: 2018. 8. 13.
	 * @작성자		: bhLee
	 * @설명
	 * <pre>
	 *		템플릿에서 메일전송
	 * </pre>
	 * @param commandMap
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "temp.tempMgr.tempMgrSendEmail.do")
	public @ResponseBody Map<String, String> tempMgrSendEmail(CommandMap commandMap, Map<String, Object> modelMap) throws Exception
	{
		return tempMgrService.tempMgrSendEmail(commandMap);
	}
	
	

}
