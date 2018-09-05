package com.web.infoSharing.referRoomMgr.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.controller.CommonController;
import com.web.infoSharing.referRoomMgr.service.ReferRoomMgrService;

/**
 * 
 * @파일명 : ReferRoomMgrController.java
 * @프로젝트 : ITMS
 * @날짜 : 2018. 5. 14.
 * @작성자 : Cho HeumJun
 * @설명
 * 
 *     <pre>
 * 		자료실 Controller
 *     </pre>
 */
@Controller
public class ReferRoomMgrController extends CommonController
{
	/**
	 * ReferRoomMgrService 등록
	 */
	@Resource(name = "infoSharing.referRoomMgr.referRoomMgrService")
	private ReferRoomMgrService referRoomMgrService;

	/**
	 * 
	 * @메소드명 : referRoomMgr
	 * @날짜 : 2018. 5. 14.
	 * @작성자 : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *		자료실 화면
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "infoSharing.referRoomMgr.referRoomMgr.do")
	public ModelAndView referRoomMgr(CommandMap commandMap)
	{
		return getUserRoleAndLink(commandMap);
	}

	/**
	 * 
	 * @메소드명 : referRoomList
	 * @날짜 : 2018. 5. 14.
	 * @작성자 : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *		자료실 리스트
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "infoSharing.referRoomMgr.referRoomList.do")
	public @ResponseBody Map<String, Object> referRoomList(CommandMap commandMap)
	{
		return referRoomMgrService.referRoomList(commandMap);

	}

	/**
	 * 
	 * @메소드명 : popUpNoticeRegiste
	 * @날짜 : 2018. 5. 14.
	 * @작성자 : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *		자료실 등록
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "infoSharing.referRoomMgr.popUpReferRoomRegiste.do")
	public ModelAndView popUpNoticeRegiste(CommandMap commandMap)
	{
		ModelAndView mav = new ModelAndView(
				Constants.INFO + Constants.REFER_ROOM + Constants.POPUP + commandMap.get(Constants.JSP_NAME));
		mav.addAllObjects(commandMap.getMap());

		return mav;
	}

	/**
	 * 
	 * @메소드명 : popUpNoticeRegiste
	 * @날짜 : 2018. 5. 14.
	 * @작성자 : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *		자료실 수정
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "infoSharing.referRoomMgr.popUpReferRoomUpdate.do")
	public ModelAndView popUpReferRoomUpdate(CommandMap commandMap)
	{
		ModelAndView mav = new ModelAndView(
				Constants.INFO + Constants.REFER_ROOM + Constants.POPUP + commandMap.get(Constants.JSP_NAME));
		mav.addAllObjects(commandMap.getMap());

		// SEQ 유무에 따른 수정
		mav.addObject("referRoom", referRoomMgrService.getReferRoom(commandMap));

		return mav;
	}

	/**
	 * 
	 * @메소드명 : referRoomList
	 * @날짜 : 2018. 5. 17.
	 * @작성자 : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *		자료실 그리드 클릭 시 첨부파일 목록
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "infoSharing.referRoomMgr.popUpReferRoomAttachList.do")
	public @ResponseBody Map<String, Object> popUpReferRoomAttachList(CommandMap commandMap)
	{
		return referRoomMgrService.popUpReferRoomAttachList(commandMap);

	}

	/**
	 * 
	 * @메소드명 : saveReferRoom
	 * @날짜 : 2018. 5. 25.
	 * @작성자 : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *		자료실 등록/수정
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "infoSharing.referRoomMgr.saveReferRoom.do")
	public @ResponseBody Map<String, String> saveReferRoom(CommandMap commandMap) throws Exception
	{
		return referRoomMgrService.saveReferRoom(commandMap);
	}

	/**
	 * 
	 * @메소드명 : saveReferRoomAttachFile
	 * @날짜 : 2018. 5. 25.
	 * @작성자 : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *		자료실 첨부파일 업로드
	 *     </pre>
	 * 
	 * @param commandMap
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "infoSharing.referRoomMgr.saveReferRoomAttachFile.do")
	public void saveReferRoomAttachFile(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		referRoomMgrService.saveReferRoomAttachFile(commandMap, request, response);
	}

	/**
	 * 
	 * @메소드명 : refAttachFileDownload
	 * @날짜 : 2018. 5. 25.
	 * @작성자 : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *		자료실 첨부파일 다운로드
	 *     </pre>
	 * 
	 * @param commandMap
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "infoSharing.referRoomMgr.refAttachFileDownload.do", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public void refAttachFileDownload(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{

		List<Map<String, Object>> resultList = referRoomMgrService.selectFileInfo(commandMap);

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
	 * 
	 * @메소드명 : refAttachFileDelete
	 * @날짜 : 2018. 5. 25.
	 * @작성자 : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *		자료실 첨부파일 삭제
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "infoSharing.referRoomMgr.refAttachFileDelete.do")
	public @ResponseBody Map<String, String> refAttachFileDelete(CommandMap commandMap) throws Exception
	{
		return referRoomMgrService.refAttachFileDelete(commandMap);
	}
	
	@RequestMapping(value = "infoSharing.referRoomMgr.referRoomDelete.do")
	public @ResponseBody Map<String, String> referRoomDelete(CommandMap commandMap) throws Exception
	{
		return referRoomMgrService.referRoomDelete(commandMap);
	}
	
	

}
