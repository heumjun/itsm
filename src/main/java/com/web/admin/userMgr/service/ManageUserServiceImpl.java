package com.web.admin.userMgr.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.View;

import com.web.admin.userMgr.dao.ManageUserDAO;
import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.exceptionHandler.CommonException;
import com.web.common.service.CommonServiceImpl;
import com.web.common.util.GenericExcelView;
import com.web.common.util.MessageUtil;
import com.web.common.util.StringUtil;

/**
 * 
 * @파일명		: ManageUserServiceImpl.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 3. 28. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 	사용자 관리	
 * </pre>
 */
@Service("admin.userMgr.manageUserService")
public class ManageUserServiceImpl extends CommonServiceImpl implements ManageUserService
{

	@Resource(name = "admin.userMgr.manageUserDAO")
	private ManageUserDAO manageUserDAO;
	
	@Override
	public List<Map<String, Object>> getUserRankList(CommandMap commandMap)
	{
		List<Map<String, Object>> resultList = manageUserDAO.getUserRankList(commandMap.getMap());
		return resultList;
	}
	
	/**
	 * 
	 * @메소드명	: saveManageUser
	 * @날짜		: 2018. 4. 12.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *			
	 * 	</pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, String> saveManageUser(CommandMap commandMap) throws Exception
	{
		// 제이슨 데이터를 List Map 형식으로 형변환하기 위한 타입참조
		TypeReference<List<HashMap<String, Object>>> typeRef = new TypeReference<List<HashMap<String, Object>>>() {};
		
		// 그리드로부터 데이타리스트를 제이슨 형식으로 받아온다.
		String gridDataList = commandMap.get(Constants.FROM_GRID_DATA_LIST).toString();
		commandMap.remove(Constants.FROM_GRID_DATA_LIST);

		// List Map 형식으로 형변환
		List<Map<String, Object>> saveList = new ObjectMapper().readValue(gridDataList, typeRef);

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
				result = getDuplicationCnt(rowData);
				if (result.equals(Constants.RESULT_SUCCESS))
				{
					rowData.put(Constants.SEQ_ID, getSeqType(Constants.USER_SEQ));
					result = gridDataInsert(rowData);
				}
				else if (result.equals(Constants.RESULT_FAIL))
				{
					throw new CommonException(MessageUtil.getMessage("common.default.duplication"), "");
				}
				else
				{
					throw new CommonException(result);
				}
				
				
				rowData.put("loginSeqId", rowData.get(Constants.SEQ_ID));
				// !! 중복체크 쿼리는 CNT로 받아올것 !! 데이터 NULL체크는 하지 않는다.
				result = manageUserDAO.userAuthDuplicationCnt(rowData);
				if (result.equals(Constants.RESULT_SUCCESS))
				{
					rowData.put(Constants.SEQ_ID, getSeqType(Constants.INDI_AUTH_SEQ));
					result = manageUserDAO.userAuthInsert(rowData);
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
				result = gridDataUpdate(rowData);
			}
			// DELETE 인경우
			else if (Constants.DELETE.equals(rowData.get(Constants.FROM_GRID_OPER)))
			{
				result = gridDataDelete(rowData);
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

	/**
	 * 
	 * @메소드명	: manageUserListExcelExport
	 * @날짜		: 2018. 3. 28.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *	사용자관리 엑셀 출력		
	 * 	</pre>
	 * @param commandMap
	 * @param modelMap
	 * @return
	 */
	@Override
	public View manageUserListExcelExport(CommandMap commandMap, Map<String, Object> modelMap)
	{

		try
		{
			// COLNAME 설정
			List<String> colName = new ArrayList<String>();
			// 그리드에서 받아온 엑셀 헤더를 설정한다.
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

			List<Map<String, Object>> listData = manageUserDAO.manageUserListExcelExport(commandMap.getMap());

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

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return new GenericExcelView();
	}

}
