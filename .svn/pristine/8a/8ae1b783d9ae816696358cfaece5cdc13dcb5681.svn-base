package com.web.admin.stanAuthMgr.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import com.web.admin.stanAuthMgr.dao.StanAuthMgrDAO;
import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.exceptionHandler.CommonException;
import com.web.common.service.CommonServiceImpl;
import com.web.common.util.MessageUtil;
import com.web.common.util.PageUtil;

/**
 * 
 * @파일명		: StanAuthMgrServiceImpl.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 4. 17. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		표준권한 정보 서비스 구현
 * </pre>
 */
@Service("admin.stanAuthMgr.stanAuthMgrService")
public class StanAuthMgrServiceImpl extends CommonServiceImpl implements StanAuthMgrService
{

	@Resource(name = "admin.stanAuthMgr.stanAuthMgrDAO")
	private StanAuthMgrDAO stanAuthMgrDAO;

	/**
	 * 
	 * @메소드명	: stanAuthMgrList
	 * @날짜		: 2018. 4. 16.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		표준권한 리스트	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> stanAuthMgrList(CommandMap commandMap) throws Exception
	{
		// 리스트를 취득한다.
		Object pageSize = commandMap.get(Constants.FROM_GRID_PAGE_SIZE);
		Object curPageNo = commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO);
		commandMap.put(Constants.SET_DB_PAGE_SIZE, pageSize);
		commandMap.put(Constants.SET_DB_CUR_PAGE_NO, curPageNo);
		List<Map<String, Object>> listData = stanAuthMgrDAO.stanAuthMgrList(commandMap.getMap());

		// 리스트 총 사이즈를 구한다.
		Object listRowCnt = listData.size();
		listRowCnt = listData.get(0).get("CNT").toString();
		if (!Constants.NO.equals(commandMap.get(Constants.IS_PAGING)))
		{
			// listRowCnt = getGridListSize(commandMap.getMap());
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
	 * @메소드명	: stanAuthMgrMenuList
	 * @날짜		: 2018. 4. 16.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		표준 권한에 따른 상세정보 리스트	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 */
	@Override
	public Map<String, Object> stanAuthMgrMenuList(CommandMap commandMap)
	{
		// 리스트를 취득한다.
		Object pageSize = commandMap.get(Constants.FROM_GRID_PAGE_SIZE);
		Object curPageNo = commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO);
		commandMap.put(Constants.SET_DB_PAGE_SIZE, pageSize);
		commandMap.put(Constants.SET_DB_CUR_PAGE_NO, curPageNo);
		List<Map<String, Object>> listData = stanAuthMgrDAO.stanAuthMgrMenuList(commandMap.getMap());

		// 리스트 총 사이즈를 구한다.
		Object listRowCnt = listData.size();
		listRowCnt = listData.get(0).get("CNT").toString();
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
	 * @메소드명	: saveStanAuthMgrMenu
	 * @날짜		: 2018. 4. 16.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		표준권한관리 저장	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, String> saveStanAuthMgrMenu(CommandMap commandMap) throws Exception
	{
		// 제이슨 데이터를 List Map 형식으로 형변환하기 위한 타입참조
		TypeReference<List<HashMap<String, Object>>> typeRef = new TypeReference<List<HashMap<String, Object>>>() {
		};
		// 그리드로부터 데이타리스트를 제이슨 형식으로 받아온다.
		String gridDataList = commandMap.get(Constants.FROM_GRID_DATA_LIST).toString();
		String gridDetailDataList = commandMap.get("chmDetailResultList").toString();
		commandMap.remove(Constants.FROM_GRID_DATA_LIST);
		commandMap.remove("chmDetailResultList");

		// List Map 형식으로 형변환
		List<Map<String, Object>> saveList = new ObjectMapper().readValue(gridDataList, typeRef);
		List<Map<String, Object>> saveDetailList = new ObjectMapper().readValue(gridDetailDataList, typeRef);

		// 결과값 최초
		String result = Constants.RESULT_FAIL;

		for (Map<String, Object> rowData : saveList)
		{
			// CommandMap에 저장되어있는 DB용 로그인 아이디, 맵퍼네임 등을 설정한다.
			rowData.put(Constants.SET_DB_LOGIN_ID, commandMap.get(Constants.SET_DB_LOGIN_ID));
			rowData.put(Constants.MAPPER_NAME, commandMap.get(Constants.MAPPER_NAME));
			// INSERT인경우 중복체크
			if (Constants.INSERT.equals(rowData.get(Constants.FROM_GRID_OPER)))
			{
				// !! 중복체크 쿼리는 CNT로 받아올것 !! 데이터 NULL체크는 하지 않는다.
				result = getStanAuthDuplicationCnt(rowData);
				if (result.equals(Constants.RESULT_SUCCESS))
				{
					rowData.put(Constants.SEQ_ID, getSeqType(Constants.STAND_AUTH_M_SEQ));
					result = stanAuthDataInsert(rowData);
				}
				else if (result.equals(Constants.RESULT_FAIL))
				{
					throw new CommonException(MessageUtil.getMessage("common.default.duplication"), "");
				}
				else
				{
					throw new CommonException(result);
				}
			}
			// UPDATE 인경우
			else if (Constants.UPDATE.equals(rowData.get(Constants.FROM_GRID_OPER)))
			{
				result = stanAuthDataUpdate(rowData);
			}
			// DELETE 인경우
			else if (Constants.DELETE.equals(rowData.get(Constants.FROM_GRID_OPER)))
			{
				result = stanAuthDataDelete(rowData);
				result = stanAuthMasterToDetailDataDelete(rowData);
			}
		}

		for (Map<String, Object> rowData : saveDetailList)
		{
			// CommandMap에 저장되어있는 DB용 로그인 아이디, 맵퍼네임 등을 설정한다.
			rowData.put(Constants.SET_DB_LOGIN_ID, commandMap.get(Constants.SET_DB_LOGIN_ID));
			rowData.put(Constants.MAPPER_NAME, commandMap.get(Constants.MAPPER_NAME));
			// 선택한 표준권한의 SEQ_ID 저장
			rowData.put("icsaim_seq_id", commandMap.get("p_stanAuthCode"));

			// 상세권한의 체크값
			if (rowData.get("auth").equals("Y"))
			{
				// Insert
				int duplCnt = stanAuthMgrDAO.stanAuthDetailUpDataDuplication(rowData);
				if (duplCnt > 0)
				{
					// 이미 상위가 있을경우
				}
				else
				{
					// 생성한 상세권한의 상위메뉴가 없을 시 상위메뉴권한 자동생성 
					rowData.put(Constants.SEQ_ID, getSeqType(Constants.STAND_AUTH_D_SEQ));
					result = stanAuthDetailUpDataInsert(rowData);
				}

				// 상세권한 저장(DB에 Row 생성)
				rowData.put(Constants.SEQ_ID, getSeqType(Constants.STAND_AUTH_D_SEQ));
				result = stanAuthDetailDataInsert(rowData);

			}
			else
			{
				// Delete
				// 상세권한 저장(DB에 Row 삭제)
				result = stanAuthDetailDataDelete(rowData);

				int duplCnt = stanAuthMgrDAO.stanAuthDetailUpDeleteDataDuplication(rowData);
				if (duplCnt > 0)
				{
					// 부모 메뉴에 달린 하위 메뉴가 있을 시
				}
				else
				{
					result = stanAuthDetailUpDataDelete(rowData);
				}

			}

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

	// 표준권한 중복 체크
	public String getStanAuthDuplicationCnt(Map<String, Object> rowData)
	{

		int result = stanAuthMgrDAO.getStanAuthDuplicationCnt(rowData);
		if (result > 0)
		{
			return Constants.RESULT_FAIL;
		}
		else
		{
			return Constants.RESULT_SUCCESS;
		}
	}

	// 표준권한 생성
	public String stanAuthDataInsert(Map<String, Object> rowData)
	{
		int insertResult = stanAuthMgrDAO.stanAuthDataInsert(rowData);
		if (insertResult == 0)
		{
			return Constants.RESULT_FAIL;
		}
		else
		{
			return Constants.RESULT_SUCCESS;
		}
	}

	// 표준권한 업데이트
	public String stanAuthDataUpdate(Map<String, Object> rowData)
	{
		int updateResult = stanAuthMgrDAO.stanAuthDataUpdate(rowData);
		if (updateResult == 0)
		{
			return Constants.RESULT_FAIL;
		}
		else
		{
			return Constants.RESULT_SUCCESS;
		}
	}

	// 표준권한 삭제
	public String stanAuthDataDelete(Map<String, Object> rowData)
	{
		int deleteResult = stanAuthMgrDAO.stanAuthDataDelete(rowData);
		if (deleteResult == 0)
		{
			return Constants.RESULT_FAIL;
		}
		else
		{
			return Constants.RESULT_SUCCESS;
		}
	}
	
	// 표준권한 삭제
	public String stanAuthMasterToDetailDataDelete(Map<String, Object> rowData)
	{
		int deleteResult = stanAuthMgrDAO.stanAuthMasterToDetailDataDelete(rowData);
		if (deleteResult == 0)
		{
			return Constants.RESULT_FAIL;
		}
		else
		{
			return Constants.RESULT_SUCCESS;
		}
	}

	// 상세권한 생성
	public String stanAuthDetailDataInsert(Map<String, Object> rowData)
	{
		int insertResult = stanAuthMgrDAO.stanAuthDetailDataInsert(rowData);
		if (insertResult == 0)
		{
			return Constants.RESULT_FAIL;
		}
		else
		{
			return Constants.RESULT_SUCCESS;
		}
	}

	// 상세권한 삭제
	public String stanAuthDetailDataDelete(Map<String, Object> rowData)
	{
		int deleteResult = stanAuthMgrDAO.stanAuthDetailDataDelete(rowData);
		if (deleteResult == 0)
		{
			return Constants.RESULT_FAIL;
		}
		else
		{
			return Constants.RESULT_SUCCESS;
		}
	}

	// 상세권한의 부모권한 생성
	public String stanAuthDetailUpDataInsert(Map<String, Object> rowData)
	{
		int insertResult = stanAuthMgrDAO.stanAuthDetailUpDataInsert(rowData);
		if (insertResult == 0)
		{
			return Constants.RESULT_FAIL;
		}
		else
		{
			return Constants.RESULT_SUCCESS;
		}
	}

	// 상세권한의 부모권한 삭제
	public String stanAuthDetailUpDataDelete(Map<String, Object> rowData)
	{
		int deleteResult = stanAuthMgrDAO.stanAuthDetailUpDataDelete(rowData);
		if (deleteResult == 0)
		{
			return Constants.RESULT_FAIL;
		}
		else
		{
			return Constants.RESULT_SUCCESS;
		}
	}
	
}
