package com.web.admin.menuMgr.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.web.admin.menuMgr.dao.ManageMenuDAO;
import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.exceptionHandler.CommonException;
import com.web.common.service.CommonServiceImpl;
import com.web.common.util.MessageUtil;
import com.web.common.util.SessionUtil;

/**
 * 
 * @파일명 : ManageMenuServiceImpl.java
 * @프로젝트 : ITMS
 * @날짜 : 2018. 3. 14.
 * @작성자 : Cho HeumJun
 * @설명
 * 
 *     <pre>
 * 
 *     </pre>
 */
@Service("admin.menuMgr.manageMenuService")
public class ManageMenuServiceImpl extends CommonServiceImpl implements ManageMenuService
{

	@Resource(name = "admin.menuMgr.manageMenuDAO")
	private ManageMenuDAO manageMenuDAO;

	@Override
	public List<Map<String, Object>> getTreeMenuList(CommandMap commandMap)
	{
		// 권한그룹이 선택되어 좌측메뉴를 취득할때는 선택된 권한그룹을 세션에 담는다.
		if (commandMap.get("roleCode") != null && !"".equals(commandMap.get("roleCode")))
		{
			SessionUtil.setObject("roleCode", commandMap.get("roleCode"));
		}
		else
		{
			SessionUtil.setObject("roleCode", "");
		}
		// 메뉴의 트리리스트를 구한다.
		return manageMenuDAO.getTreeMenuList(commandMap.getMap());
	}

	@Override
	public List<Map<String, Object>> getAdminTreeMenuList(CommandMap commandMap)
	{
		// 메뉴의 트리리스트를 구한다.
				return manageMenuDAO.getAdminTreeMenuList(commandMap.getMap());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, String> saveManageMenu(CommandMap commandMap) throws Exception
	{
		// 제이슨 데이터를 List Map 형식으로 형변환하기 위한 타입참조
		TypeReference<List<HashMap<String, Object>>> typeRef = new TypeReference<List<HashMap<String, Object>>>() {
		};
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
					rowData.put(Constants.SEQ_ID, getSeqType(Constants.MENU_SEQ));
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

}
