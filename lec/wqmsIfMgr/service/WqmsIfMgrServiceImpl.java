package com.web.lec.wqmsIfMgr.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.service.CommonServiceImpl;
import com.web.common.util.GenericExcelView;
import com.web.common.util.PageUtil;
import com.web.common.util.StringUtil;
import com.web.lec.wqmsIfMgr.dao.WqmsIfMgrDAO;

/**
 * 
 * @파일명		: WqmsIfMgrServiceImpl.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 8. 20. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		WQMS I/F ServiceImpl
 * </pre>
 */
@Service("lec.wqmsIfMgr.wqmsIfMgrService")
public class WqmsIfMgrServiceImpl extends CommonServiceImpl implements WqmsIfMgrService
{

	@Resource(name = "lec.wqmsIfMgr.wqmsIfMgrDAO")
	private WqmsIfMgrDAO wqmsIfMgrDAO;

	/**
	 * 
	 * @메소드명	: wqmsIfMgrList
	 * @날짜		: 2018. 8. 20.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		WQMS I/F 리스트	
	 * 	</pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> wqmsIfMgrList(CommandMap commandMap) throws Exception
	{
		// 리스트를 취득한다.
		Object pageSize = commandMap.get(Constants.FROM_GRID_PAGE_SIZE);
		Object curPageNo = commandMap.get(Constants.FROM_GRID_CUR_PAGE_NO);
		commandMap.put(Constants.SET_DB_PAGE_SIZE, pageSize);
		commandMap.put(Constants.SET_DB_CUR_PAGE_NO, curPageNo);
		List<Map<String, Object>> listData = wqmsIfMgrDAO.wqmsIfMgrList(commandMap.getMap());

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
	 * @메소드명	: wqmsIfMgrExcelExport
	 * @날짜		: 2018. 8. 20.
	 * @작성자		: 조흠준
	 * @설명 
	 * 	<pre>
	 *		WQMS I/F 엑셀 출력	
	 * 	</pre>
	 * @param commandMap
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public View wqmsIfMgrExcelExport(CommandMap commandMap, Map<String, Object> modelMap) throws Exception
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

			List<Map<String, Object>> listData = wqmsIfMgrDAO.wqmsIfMgrExcelExport(commandMap.getMap());

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
