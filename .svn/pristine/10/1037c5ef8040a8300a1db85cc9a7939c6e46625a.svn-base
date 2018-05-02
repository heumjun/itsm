package com.web.admin.codeMgr.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.web.admin.codeMgr.dao.ManageCodeDAO;
import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.exceptionHandler.CommonException;
import com.web.common.service.CommonServiceImpl;
import com.web.common.util.MessageUtil;

/**
 * 
 * @파일명 : ManageCodeServiceImpl.java
 * @프로젝트 : ITMS
 * @날짜 : 2018. 3. 28.
 * @작성자 : Cho HeumJun
 * @설명
 * 
 *     <pre>
 *     공통코드관리
 *     </pre>
 */
@Service("admin.codeMgr.manageCodeService")
public class ManageCodeServiceImpl extends CommonServiceImpl implements ManageCodeService
{

	@Resource(name = "admin.codeMgr.manageCodeDAO")
	private ManageCodeDAO manageCodeDAO;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Map<String, String> saveManageCode(CommandMap commandMap) throws Exception
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
				result = getDuplicationCnt(rowData);
				if (result.equals(Constants.RESULT_SUCCESS))
				{
					rowData.put(Constants.SEQ_ID, getSeqType(Constants.CODE_M_SEQ));
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
		
		for (Map<String, Object> rowData : saveDetailList)
		{
			// CommandMap에 저장되어있는 DB용 로그인 아이디, 맵퍼네임 등을 설정한다.
			rowData.put(Constants.SET_DB_LOGIN_ID, commandMap.get(Constants.SET_DB_LOGIN_ID));
			rowData.put("p_seq_id", commandMap.get("p_seq_id"));
			rowData.put("p_major_code", commandMap.get("p_major_code"));
			
			// INSERT인경우 중복체크
			if (Constants.INSERT.equals(rowData.get(Constants.FROM_GRID_OPER)))
			{
				System.out.println(rowData.toString());
				// !! 중복체크 쿼리는 CNT로 받아올것 !! 데이터 NULL체크는 하지 않는다.
				int duplicationResult = manageCodeDAO.selectOne("SaveManageCode.detailDuplicate", rowData);
				if (duplicationResult > 0)
				{
					result =  Constants.RESULT_FAIL;
				}
				else
				{
					result =  Constants.RESULT_SUCCESS;
				}
				
				if (result.equals(Constants.RESULT_SUCCESS))
				{
					rowData.put(Constants.SEQ_ID, getSeqType(Constants.CODE_D_SEQ));
					int insertResult = manageCodeDAO.insert("SaveManageCode.detailInsert", rowData);
					if (insertResult == 0)
					{
						result = Constants.RESULT_FAIL;
					}
					else
					{
						result = Constants.RESULT_SUCCESS;
					}
					
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
				int updateResult = manageCodeDAO.update("SaveManageCode.detailUpdate", rowData);
				if (updateResult == 0)
				{
					result = Constants.RESULT_FAIL;
				}
				else
				{
					result = Constants.RESULT_SUCCESS;
				}
			}
			// DELETE 인경우
			else if (Constants.DELETE.equals(rowData.get(Constants.FROM_GRID_OPER)))
			{
				int deleteResult = manageCodeDAO.delete("SaveManageCode.detailDelete", rowData);
				if (deleteResult == 0)
				{
					result =  Constants.RESULT_FAIL;
				}
				else
				{
					result =  Constants.RESULT_SUCCESS;
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

}
