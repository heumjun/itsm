package com.web.temp.tempMgr.service;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.View;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.exceptionHandler.CommonException;
import com.web.common.service.CommonServiceImpl;
import com.web.common.util.FileScanner;
import com.web.common.util.GenericExcelView;
import com.web.common.util.MessageUtil;
import com.web.common.util.PageUtil;
import com.web.common.util.StringUtil;
import com.web.temp.tempMgr.dao.TempMgrDAO;

import net.sf.json.JSONObject;

/**
 * 
 * @파일명		: ReferRoomMgrServiceImpl.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 5. 25. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		자료실 관리 ServiceImpl
 * </pre>
 */
@Service("temp.tempMgr.tempMgrService")
public class TempMgrServiceImpl extends CommonServiceImpl implements TempMgrService
{
	/**
	 * tempDAO 등록
	 */
	@Resource(name = "temp.tempMgr.tempMgrDAO")
	private TempMgrDAO tempMgrDAO;

	@Resource(name = "velocityEngine")
	private VelocityEngine velocityEngine;
	
	/** 
	 * @메소드명	: tempList
	 * @날짜		: 2018. 8. 9.
	 * @작성자		:  bhlee
	 * @설명 
	 * 	<pre>
	 *			
	 * 	</pre>
	 * @param commandMap
	 * @return
	 */
	@Override
	public Map<String, Object> tempList(CommandMap commandMap)
	{
		// 리스트를 취득한다.
		Object pageSize = commandMap.get(Constants.FROM_GRID_PAGE_SIZE);
		Object curPageNo = commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO);
		commandMap.put(Constants.SET_DB_PAGE_SIZE, pageSize);
		commandMap.put(Constants.SET_DB_CUR_PAGE_NO, curPageNo);
		//List<Map<String, Object>> listData = tempMgrDAO.referRoomList(commandMap.getMap());
		
		List<Map<String, Object>> listData = tempMgrDAO.tempList(commandMap.getMap());
		

		// 리스트 총 사이즈를 구한다.
		Object listRowCnt = listData.size();
		if (listData.size() > 0)
		{
			listRowCnt = listData.get(0).get("CNT").toString();
		}
		else
		{
			listRowCnt = 0;
		}

		// 라스트 페이지를 구한다.
		Object lastPageCnt = "page>total";
		if (!Constants.NO.equals(commandMap.get(Constants.IS_PAGING)))
		{
			lastPageCnt = PageUtil.getPageCount(pageSize, listRowCnt);
		}

		// 결과값 생성
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constants.GRID_RESULT_CUR_PAGE, curPageNo);
		result.put(Constants.GRID_RESULT_LAST_PAGE, lastPageCnt);
		result.put(Constants.GRID_RESULT_RECORDS_CNT, listRowCnt);
		result.put(Constants.GRID_RESULT_DATA, listData);

		return result;
	}

	/** 
	 * @메소드명	: saveTempMgr
	 * @날짜		: 2018. 8. 10.
	 * @작성자		: bhlee
	 * @설명 
	 * 	<pre>
	 *		템플릿 저장
	 * 	</pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, String> saveTempMgr(CommandMap commandMap) throws Exception
	{
		// 결과값 최초
		String result = Constants.RESULT_FAIL;

		if (commandMap.get("p_seq_id").equals(""))
		{

			// 등록
			// 공지사항 테이블 SEQ 증가
			commandMap.put(Constants.SEQ_ID, getSeqType(Constants.TEMP_SEQ));
			int insertResult = tempMgrDAO.saveTempMgrInsert(commandMap.getMap());
			
			if (insertResult == 0)
			{
				result = Constants.RESULT_FAIL;
			}
			else
			{
				result = Constants.RESULT_SUCCESS;
			}
		}
		else
		{
			// 수정
			int updateResult = tempMgrDAO.saveTempMgrUpdate(commandMap.getMap());

			if (updateResult == 0)
			{
				result = Constants.RESULT_FAIL;
			}
			else
			{
				result = Constants.RESULT_SUCCESS;
			}
		}

		if (result.equals(Constants.RESULT_SUCCESS))
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put(Constants.RESULT_KEY, Constants.RESULT_SUCCESS);
			map.put(Constants.RESULT_MASAGE_KEY, "처리가 완료 되었습니다.");
			map.put("ref_table_seq_id", (String) commandMap.get(Constants.SEQ_ID));

			return map;
		}
		else if (result.equals(Constants.RESULT_FAIL))
		{
			// 실패한경우(실패 메시지가 없는 경우)
			throw new CommonException();
		}
		else
		{
			// 실패한경우(실패 메시지가 있는 경우)
			throw new CommonException(result);
		}
	}
		
	/** 
	 * @메소드명	: saveTempMgrAttachFile
	 * @날짜		: 2018. 8. 10.
	 * @작성자		: bhlee
	 * @설명 
	 * 	<pre>
	 *		템플릿 첨부파일 저장
	 * 	</pre>
	 * @param commandMap
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void saveTempMgrAttachFile(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		MultipartFile mf = null;

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet())
		{
			mf = entity.getValue();
		}

		String uploadDir = MessageUtil.getMessage("referfile.path");
		File desti = new File(uploadDir);
		if (!desti.exists())
		{
			desti.mkdirs();
		}

		String fileExt = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf('.'));
		String uuidFileName = UUID.randomUUID().toString() + fileExt;
		File uploadFile = new File(uploadDir + uuidFileName);

		commandMap.put("file_path", uploadDir + uuidFileName);
		commandMap.put("file_name", uuidFileName);
		commandMap.put("file_size", mf.getSize());
		commandMap.put("file_type", fileExt);
		commandMap.put("org_file_name", mf.getOriginalFilename());

		// 결과값 최초
		String result = Constants.RESULT_FAIL;

		// 등록
		// 자료실 첨부파일 테이블 SEQ 증가
		commandMap.put(Constants.SEQ_ID, getSeqType(Constants.ATTACH_SEQ));
		//int insertResult = tempMgrDAO.saveReferRoomAttachInsert(commandMap.getMap());
		int insertResult = tempMgrDAO.saveTempMgrAttachInsert(commandMap.getMap());

		if (insertResult == 0)
		{
			result = Constants.RESULT_FAIL;
		}
		else
		{
			result = Constants.RESULT_SUCCESS;
			try
			{
				mf.transferTo(uploadFile);
			}
			catch (IllegalStateException | IOException e)
			{
				e.printStackTrace();
			}
		}

		if (result.equals(Constants.RESULT_SUCCESS))
		{
			// 결과값에 따른 메시지를 담아 전송
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Constants.RESULT_KEY, Constants.RESULT_SUCCESS);
			map.put(Constants.RESULT_MASAGE_KEY, "처리가 완료 되었습니다.");
			response.setContentType("text/html;charset=utf-8");
			JSONObject jsonObject = JSONObject.fromObject(map);
			response.getWriter().write(jsonObject.toString());
		}
		else if (result.equals(Constants.RESULT_FAIL))
		{
			// 실패한경우(실패 메시지가 없는 경우)
			throw new CommonException();
		}
		else
		{
			// 실패한경우(실패 메시지가 있는 경우)
			throw new CommonException(result);
		}

	}
	

	/** 
	 * @메소드명	: getTempMgr
	 * @날짜		: 2018. 8. 10.
	 * @작성자		: bhlee
	 * @설명 
	 * 	<pre>
	 *		템플릿 상세
	 * 	</pre>
	 * @param commandMap
	 * @return
	 */
	@Override
	public Map<String, String> getTempMgr(CommandMap commandMap)
	{
		return tempMgrDAO.getTempMgr(commandMap.getMap());
	}
	
	/** 
	 * @메소드명	: popUpTempMgrAttachList
	 * @날짜		: 2018. 8. 10.
	 * @작성자		: bhlee
	 * @설명 
	 * 	<pre>
	 *		템플릿첨부파일 상세 리스트
	 * 	</pre>
	 * @param commandMap
	 * @return
	 */
	@Override
	public Map<String, Object> popUpTempMgrAttachList(CommandMap commandMap)
	{
		// 리스트를 취득한다.
		Object pageSize = commandMap.get(Constants.FROM_GRID_PAGE_SIZE);
		Object curPageNo = commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO);
		commandMap.put(Constants.SET_DB_PAGE_SIZE, pageSize);
		commandMap.put(Constants.SET_DB_CUR_PAGE_NO, curPageNo);
		List<Map<String, Object>> listData = tempMgrDAO.popUpTempMgrAttachList(commandMap.getMap());

		// 리스트 총 사이즈를 구한다.
		Object listRowCnt = listData.size();
		if (listData.size() > 0)
		{
			listRowCnt = listData.get(0).get("CNT").toString();
		}
		else
		{
			listRowCnt = 0;
		}

		// 라스트 페이지를 구한다.
		Object lastPageCnt = "page>total";
		if (!Constants.NO.equals(commandMap.get(Constants.IS_PAGING)))
		{
			lastPageCnt = PageUtil.getPageCount(pageSize, listRowCnt);
		}

		// 결과값 생성
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constants.GRID_RESULT_CUR_PAGE, curPageNo);
		result.put(Constants.GRID_RESULT_LAST_PAGE, lastPageCnt);
		result.put(Constants.GRID_RESULT_RECORDS_CNT, listRowCnt);
		result.put(Constants.GRID_RESULT_DATA, listData);

		return result;
	}
	
	/** 
	 * @메소드명	: selectFileInfo
	 * @날짜		: 2018. 8. 10.
	 * @작성자		: bhlee
	 * @설명 
	 * 	<pre>
	 *		템플릿 건별에 대한 첨부파일 정보	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectFileInfo(CommandMap commandMap)
	{
		commandMap.put("p_attach_seq_id", StringUtil.nullString(commandMap.get("p_attach_seq_id")).split(","));
		return tempMgrDAO.selectFileInfo(commandMap.getMap());
	}

	/** 
	 * @메소드명	: refAttachFileDelete
	 * @날짜		: 2018. 8. 10.
	 * @작성자		: bhlee
	 * @설명 
	 * 	<pre>
	 *		템플릿 첨부파일 삭제
	 * 	</pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, String> refAttachFileDelete(CommandMap commandMap) throws Exception
	{
		// 결과값 최초
		String result = Constants.RESULT_FAIL;

		// 그리드에서 선택한 첨부파일 배열
		commandMap.put("p_attach_seq_id", StringUtil.nullString(commandMap.get("p_attach_seq_id")).split(","));
		List<Map<String, Object>> resultList = tempMgrDAO.selectFileInfo(commandMap.getMap());
		for (Map<String, Object> resultMap : resultList)
		{

			File file = new File((String) resultMap.get("FILE_PATH"));

			if (file.exists())
			{
				if (file.delete())
				{
					System.out.println("파일삭제 성공");
				}
				else
				{
					System.out.println("파일삭제 실패");
				}
			}
			else
			{
				System.out.println("파일이 존재하지 않습니다.");
			}
		}

		int deleteResult = tempMgrDAO.refAttachFileDelete(commandMap.getMap());
		if (deleteResult == 0)
		{
			result = Constants.RESULT_FAIL;
		}
		else
		{
			result = Constants.RESULT_SUCCESS;
		}

		if (result.equals(Constants.RESULT_SUCCESS))
		{
			// 결과값에 따른 메시지를 담아 전송
			return MessageUtil.getResultMessage(result);
		}
		else if (result.equals(Constants.RESULT_FAIL))
		{
			// 실패한경우(실패 메시지가 없는 경우)
			throw new CommonException();
		}
		else
		{
			// 실패한경우(실패 메시지가 있는 경우)
			throw new CommonException(result);
		}
	}
	
	/** 
	 * @메소드명	: tempMgrDelete
	 * @날짜		: 2018. 8. 10.
	 * @작성자		: bhlee
	 * @설명 
	 * 	<pre>
	 *		템플릿 삭제	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, String> tempMgrDelete(CommandMap commandMap) throws Exception
	{
		// 결과값 최초
		String result = Constants.RESULT_FAIL;
		
		// 그리드에서 선택한 첨부파일 배열
		List<Map<String, Object>> resultList = tempMgrDAO.getAttachFileInfo(commandMap.getMap());
		if(resultList.size() > 0) {
			for (Map<String, Object> resultMap : resultList)
			{

				File file = new File((String) resultMap.get("FILE_PATH"));

				if (file.exists())
				{
					if (file.delete())
					{
						System.out.println("파일삭제 성공");
					}
					else
					{
						System.out.println("파일삭제 실패");
					}
				}
				else
				{
					System.out.println("파일이 존재하지 않습니다.");
				}
			}

			// 첨부파일 TABLE에서 삭제 
			int deleteResult = tempMgrDAO.tempMgrAttachDelete(commandMap.getMap());
			
			if (deleteResult == 0)
			{
				result = Constants.RESULT_FAIL;
			}
			else
			{
				result = Constants.RESULT_SUCCESS;
			}
		}

		// 자료실 리스트에서 삭제
		int referRoomDeleteResult = tempMgrDAO.tempMgrDelete(commandMap.getMap());
		if (referRoomDeleteResult == 0)
		{
			result = Constants.RESULT_FAIL;
		}
		else
		{
			result = Constants.RESULT_SUCCESS;
		}

		if (result.equals(Constants.RESULT_SUCCESS))
		{
			// 결과값에 따른 메시지를 담아 전송
			return MessageUtil.getResultMessage(result);
		}
		else if (result.equals(Constants.RESULT_FAIL))
		{
			// 실패한경우(실패 메시지가 없는 경우)
			throw new CommonException();
		}
		else
		{
			// 실패한경우(실패 메시지가 있는 경우)
			throw new CommonException(result);
		}
	}
	
	/** 
	 * @메소드명	: popUpTempInfoExcelImportAction
	 * @날짜		: 2018. 8. 10.
	 * @작성자		: bhlee
	 * @설명 
	 * 	<pre>
	 *		엑셀일괄등록	
	 * 	</pre>
	 * @param commandMap
	 * @param response
	 * @throws Exception
	 */
	@Override
	public void popUpTempInfoExcelImportAction(CommandMap commandMap, HttpServletResponse response) throws Exception
	{
		response.setContentType("text/html; charset=UTF-8");
		
		String result = Constants.RESULT_FAIL;
		
		try {

			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			list = FileScanner.excelToList((MultipartFile) commandMap.get("file"), 1, true, 0);
			
			for(Map<String, Object> str : list ) {
				
				Map<String, Object> pkgParam = new HashMap<String, Object>();

				pkgParam.put(Constants.SEQ_ID, getSeqType(Constants.TEMP_SEQ));
				pkgParam.put("p_icui_seq_id", str.get("column0"));
				pkgParam.put("p_title", str.get("column1"));
				pkgParam.put("p_sel_temp", str.get("column2"));
				pkgParam.put("p_contents", str.get("column3"));
				pkgParam.put(Constants.SET_DB_LOGIN_ID, commandMap.get(Constants.SET_DB_LOGIN_ID));
				
				int smsInsertInfoResult = tempMgrDAO.popUpTempInfoExcelImportAction(pkgParam);
				if (smsInsertInfoResult == 0)
				{
					result = Constants.RESULT_FAIL;
				}
				else
				{
					result = Constants.RESULT_SUCCESS;
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			StringBuffer sb = new StringBuffer();
			sb.append("<script type=\"text/javascript\" >");
			sb.append("alert('시스템 오류입니다.\n전산담당자에게 문의해주세요.');");
			sb.append("self.close();");
			sb.append("</script>");
			response.getWriter().println(sb);
			response.getWriter().flush();
		}
		
		if (result.equals(Constants.RESULT_SUCCESS))
		{
			StringBuffer sb = new StringBuffer();
			sb.append("<script type=\"text/javascript\" >");
			sb.append("alert('처리가 완료되었습니다.');");
			sb.append("self.close();");
			sb.append("</script>");
			response.getWriter().println(sb);
			response.getWriter().flush();
		}
		else
		{
			StringBuffer sb = new StringBuffer();
			sb.append("<script type=\"text/javascript\" >");
			sb.append("alert('업로드 실패');");
			sb.append("self.close();");
			sb.append("</script>");
			response.getWriter().println(sb);
			response.getWriter().flush();
		}
		
	}

	/** 
	 * @메소드명	: tempMgrListExcelExport
	 * @날짜		: 2018. 8. 13.
	 * @작성자		: bhlee
	 * @설명 
	 * 	<pre>
	 *		템플릿 엑셀저장
	 * 	</pre>
	 * @param commandMap
	 * @param modelMap
	 * @return
	 */
	@Override
	public View tempMgrListExcelExport(CommandMap commandMap, Map<String, Object> modelMap)
	{
		try
		{
			// COLNAME 설정
			List<String> colName = new ArrayList<String>();
			// 그리드에서 받아온 엑셀 헤더를 설정한다.
			System.out.println("===="+commandMap.get("p_col_name").toString());
			System.out.println("===="+commandMap.get("p_col_name").toString());
			
			String[] p_col_names = commandMap.get("p_col_name").toString().split(",");
			// COLVALUE 설정
			List<List<String>> colValue = new ArrayList<List<String>>();
			// 그리드에서 받아온 데이터 네임을 배열로 설정
			String[] p_data_names = commandMap.get("p_data_name").toString().split(",");

			// 그리드의 헤더를 콜네임으로 설정
			for (String p_col_name : p_col_names)
			{
				// colName.add(new
				// String(p_col_name.getBytes("ISO_8859_1"),"UTF-8"));
				colName.add(p_col_name);
			}

			// 리스트를 취득한다.
			Object pageSize = commandMap.get(Constants.FROM_GRID_PAGE_SIZE);
			Object curPageNo = commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO);

			// Excel출력 페이지는 단일 페이지=1, 출력은 9999999건까지 제약조건
			curPageNo = "1";
			pageSize = "999999";

			commandMap.put(Constants.SET_DB_PAGE_SIZE, pageSize);
			commandMap.put(Constants.SET_DB_CUR_PAGE_NO, curPageNo);

			List<Map<String, Object>> listData = tempMgrDAO.tempList(commandMap.getMap());

			for (Map<String, Object> rowData : listData)
			{
				List<String> row = new ArrayList<String>();

				// 데이터 네임을 이용해서 리스트에서 뽑아냄.
				for (String p_data_name : p_data_names)
				{
					row.add(StringUtil.nullString(rowData.get(p_data_name)));
				}
				colValue.add(row);
			}

			modelMap.put("excelName", commandMap.get(Constants.MAPPER_NAME));
			modelMap.put("colName", colName);
			modelMap.put("colValue", colValue);

		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return new GenericExcelView();
	}
	
	/** 
	 * @메소드명	: tempMgrSendEmail
	 * @날짜		: 2018. 8. 13.
	 * @작성자		: bhlee
	 * @설명 
	 * 	<pre>
	 *		템플릿 메일	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, String> tempMgrSendEmail(CommandMap commandMap) throws Exception
	{
		//TODO 메일 보내기(loginId) SET_DB_LOGIN_ID, APPROVAL_ADMIN
		Map<String, String> resultMap = tempMgrDAO.getUserMail(commandMap.getMap());
		
		Template template = velocityEngine.getTemplate("./mailTemplate/tempTest.template", "UTF-8");
		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("conStatus", "R");
		velocityContext.put("title", commandMap.get("p_title"));
		velocityContext.put("contents", commandMap.get("p_contents"));
		velocityContext.put("user_name", resultMap.get("user_name"));
		velocityContext.put("create_date", resultMap.get("create_date"));
		
		StringWriter stringWriter = new StringWriter();
		template.merge(velocityContext, stringWriter);
		
		commandMap.put("i_sender", commandMap.get("create_mail").toString());
		commandMap.put("i_receiver", commandMap.get("receiver_mail").toString());
		commandMap.put("i_message", stringWriter.toString());
		commandMap.put("i_subject", "[템플릿] 메일전송테스트");
		
		// 메일 발송
		return sendMail(commandMap);
	}
}
