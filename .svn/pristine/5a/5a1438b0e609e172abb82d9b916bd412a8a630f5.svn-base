package com.web.common.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @파일명 : BaseDAO.java
 * @프로젝트 : ITMS
 * @날짜 : 2018. 3. 28.
 * @작성자 : Cho HeumJun
 * @설명
 * 
 *     <pre>
 * 		공통 : DAO
 *     </pre>
 */
@Repository("baseDAO")
public class BaseDAO extends CommonDAO
{

	/**
	 * 
	 * @메소드명 : getSelectBoxDataList
	 * @날짜 : 2018. 3. 28.
	 * @작성자 : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *		공통 셀렉트 박스 리스트 쿼리
	 *     </pre>
	 * 
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getSelectBoxDataList(Map<String, Object> map)
	{
		return selectList("CommonSelectBox." + map.get("p_query"), map);
	}

}
