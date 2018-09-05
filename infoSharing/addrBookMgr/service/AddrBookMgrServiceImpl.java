package com.web.infoSharing.addrBookMgr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import com.web.infoSharing.addrBookMgr.dao.AddrBookMgrDAO;
import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.service.CommonServiceImpl;
import com.web.common.util.GenericExcelView;
import com.web.common.util.StringUtil;

/**
 * 
 * @파일명	: AddrBookMgrServiceImpl.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 5. 04.
 * @작성자	: LEE BYOUNGJIN
 * @설명
 * <pre>
 * 	주소록관리(운영팀)
 * </pre>
 */
@Service("infoSharing.addrBookMgr.addrBookMgrService")
public class AddrBookMgrServiceImpl extends CommonServiceImpl implements AddrBookMgrService
{

	@Resource(name = "infoSharing.addrBookMgr.addrBookMgrDAO")
	private AddrBookMgrDAO addrBookMgrDAO;
	
	/**
	 * 
	 * @메소드명	: addrBookMgrListExcelExport
	 * @날짜		: 2018. 5. 04.
	 * @작성자	: LEE BYOUNGJIN
	 * @설명 
	 * 	<pre>
	 *	주소록관리(운영팀) 엑셀 출력		
	 * 	</pre>
	 * @param commandMap
	 * @param modelMap
	 * @return
	 */
	@Override
	public View addrBookMgrListExcelExport(CommandMap commandMap, Map<String, Object> modelMap)
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

			List<Map<String, Object>> listData = addrBookMgrDAO.addrBookMgrListExcelExport(commandMap.getMap());

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
}
