package com.web.infoSharing.referRoomMgr.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.exceptionHandler.CommonException;
import com.web.common.service.CommonServiceImpl;
import com.web.common.util.MessageUtil;
import com.web.common.util.PageUtil;
import com.web.common.util.StringUtil;
import com.web.infoSharing.referRoomMgr.dao.ReferRoomMgrDAO;

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
@Service("infoSharing.referRoomMgr.referRoomMgrService")
public class ReferRoomMgrServiceImpl extends CommonServiceImpl implements ReferRoomMgrService
{
	/**
	 * ReferRoomMgrDAO 등록
	 */
	@Resource(name = "infoSharing.referRoomMgr.referRoomMgrDAO")
	private ReferRoomMgrDAO referRoomMgrDAO;

	/**
	 * 
	 * @메소드명 : referRoomList
	 * @날짜 : 2018. 5. 25.
	 * @작성자 : 조흠준
	 * @설명
	 * 
	 *     <pre>
	 *		자료실 리스트
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 */
	@Override
	public Map<String, Object> referRoomList(CommandMap commandMap)
	{
		// 리스트를 취득한다.
		Object pageSize = commandMap.get(Constants.FROM_GRID_PAGE_SIZE);
		Object curPageNo = commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO);
		commandMap.put(Constants.SET_DB_PAGE_SIZE, pageSize);
		commandMap.put(Constants.SET_DB_CUR_PAGE_NO, curPageNo);
		List<Map<String, Object>> listData = referRoomMgrDAO.referRoomList(commandMap.getMap());

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
	 * 
	 * @메소드명 : saveReferRoom
	 * @날짜 : 2018. 5. 25.
	 * @작성자 : 조흠준
	 * @설명
	 * 
	 *     <pre>
	 * 
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, String> saveReferRoom(CommandMap commandMap) throws Exception
	{
		// 결과값 최초
		String result = Constants.RESULT_FAIL;

		if (commandMap.get("p_seq_id").equals(""))
		{

			// 등록
			// 공지사항 테이블 SEQ 증가
			commandMap.put(Constants.SEQ_ID, getSeqType(Constants.RESOURCE_SEQ));
			int insertResult = referRoomMgrDAO.saveReferRoomInsert(commandMap.getMap());

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
			int updateResult = referRoomMgrDAO.saveReferRoomUpdate(commandMap.getMap());

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
	 * 
	 * @메소드명 : saveReferRoomAttachFile
	 * @날짜 : 2018. 5. 25.
	 * @작성자 : 조흠준
	 * @설명
	 * 
	 *     <pre>
	 *		자료실 첨부파일 저장
	 *     </pre>
	 * 
	 * @param commandMap
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void saveReferRoomAttachFile(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response)
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
		int insertResult = referRoomMgrDAO.saveReferRoomAttachInsert(commandMap.getMap());

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
	 * 
	 * @메소드명 : getReferRoom
	 * @날짜 : 2018. 5. 25.
	 * @작성자 : 조흠준
	 * @설명
	 * 
	 *     <pre>
	 *		자료실 건별 정보
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 */
	@Override
	public Map<String, String> getReferRoom(CommandMap commandMap)
	{
		return referRoomMgrDAO.getReferRoom(commandMap.getMap());
	}

	/**
	 * 
	 * @메소드명 : popUpReferRoomAttachList
	 * @날짜 : 2018. 5. 25.
	 * @작성자 : 조흠준
	 * @설명
	 * 
	 *     <pre>
	 *		자료실 첨부파일 리스트
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 */
	@Override
	public Map<String, Object> popUpReferRoomAttachList(CommandMap commandMap)
	{
		// 리스트를 취득한다.
		Object pageSize = commandMap.get(Constants.FROM_GRID_PAGE_SIZE);
		Object curPageNo = commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO);
		commandMap.put(Constants.SET_DB_PAGE_SIZE, pageSize);
		commandMap.put(Constants.SET_DB_CUR_PAGE_NO, curPageNo);
		List<Map<String, Object>> listData = referRoomMgrDAO.popUpReferRoomAttachList(commandMap.getMap());

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
	 * 
	 * @메소드명 : selectFileInfo
	 * @날짜 : 2018. 5. 25.
	 * @작성자 : 조흠준
	 * @설명
	 * 
	 *     <pre>
	 *		자료실 건별에 대한 첨부파일 정보
	 *     </pre>
	 * 
	 * @param commandMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectFileInfo(CommandMap commandMap)
	{
		commandMap.put("p_attach_seq_id", StringUtil.nullString(commandMap.get("p_attach_seq_id")).split(","));
		return referRoomMgrDAO.selectFileInfo(commandMap.getMap());
	}

	/**
	 * 
	 * @메소드명 : refAttachFileDelete
	 * @날짜 : 2018. 5. 25.
	 * @작성자 : 조흠준
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
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, String> refAttachFileDelete(CommandMap commandMap) throws Exception
	{
		// 결과값 최초
		String result = Constants.RESULT_FAIL;

		// 그리드에서 선택한 첨부파일 배열
		commandMap.put("p_attach_seq_id", StringUtil.nullString(commandMap.get("p_attach_seq_id")).split(","));
		List<Map<String, Object>> resultList = referRoomMgrDAO.selectFileInfo(commandMap.getMap());
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

		int deleteResult = referRoomMgrDAO.refAttachFileDelete(commandMap.getMap());
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
	 * 
	 * @메소드명	: referRoomDelete
	 * @날짜		: 2018. 5. 25.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		자료실 리스트 건별 삭제	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, String> referRoomDelete(CommandMap commandMap) throws Exception
	{
		// 결과값 최초
		String result = Constants.RESULT_FAIL;
		
		// 그리드에서 선택한 첨부파일 배열
		List<Map<String, Object>> resultList = referRoomMgrDAO.getAttachFileInfo(commandMap.getMap());
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
			int deleteResult = referRoomMgrDAO.refRoomAttachDelete(commandMap.getMap());
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
		int referRoomDeleteResult = referRoomMgrDAO.refRoomDelete(commandMap.getMap());
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

	@Override
	public List<Map<String, Object>> getMainRefRoomList(CommandMap commandMap)
	{
		return referRoomMgrDAO.getMainRefRoomList(commandMap.getMap());
	}

}
