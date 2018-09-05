package com.web.scheduler.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.web.common.dao.CommonDAO;

/**
 * 
 * @파일명    : SchedulerMgrDAO.java
 * @프로젝트	: ITMS
 * @날짜		: 2018. 8. 16.
 * @작성자	: Son JungHwi
 * @설명
 * 
 * <pre>
 * 		배치 DAO
 * </pre>
 */
@Repository("schedulerMgrDAO")
public class SchedulerMgrDAO extends CommonDAO
{
	protected Log log = LogFactory.getLog(SchedulerMgrDAO.class);
	
	/**
	 * 
	 * @메소드명	: getErrorCnt1
	 * @날짜		: 2018. 8. 16.
	 * @작성자	: Son JungHwi
	 * @설명 
	 * 	<pre>
	 *			입고오류 취득
	 * 	</pre>
	 * @param map
	 * @return error count
	 * @throws 
	 */
	public int warehousingErrorCnt(Map<String, Object> map)
	{
		return selectOneErp("SchedulerMgr.warehousingErrorCnt", map);
	}
	
	/**
	 * 
	 * @메소드명	: materialsMoveErrorCnt
	 * @날짜		: 2018. 8. 16.
	 * @작성자	: Son JungHwi
	 * @설명 
	 * 	<pre>
	 *			자재이동오류 취득
	 * 	</pre>
	 * @param map
	 * @return error count
	 * @throws 
	 */
	public int materialsMoveErrorCnt(Map<String, Object> map)
	{
		return selectOneErp("SchedulerMgr.materialsMoveErrorCnt", map);
	}
	
	/**
	 * 
	 * @메소드명	: weeklyPerformanceList
	 * @날짜		: 2018. 8. 16.
	 * @작성자	: Son JungHwi
	 * @설명 
	 * 	<pre>
	 *			주간실적 리스트
	 * 	</pre>
	 * @param map
	 * @return weeklyPerformanceList
	 * @throws 
	 */
	public List<Map<String, Object>> weeklyPerformanceList(Map<String, Object> map)
	{
		return selectList("SchedulerMgr.weeklyPerformanceList", map);
	}
	
	/**
	 * 
	 * @메소드명	: vacationNoticeList
	 * @날짜		: 2018. 8. 16.
	 * @작성자	: Son JungHwi
	 * @설명 
	 * 	<pre>
	 *			차주휴가일정 리스트
	 * 	</pre>
	 * @param map
	 * @return vacationNoticeList
	 * @throws 
	 */
	public List<Map<String, Object>> vacationNoticeList(Map<String, Object> map)
	{
		return selectList("SchedulerMgr.vacationNoticeList", map);
	}
	
	/**
	 * 
	 * @메소드명 : smsNoticeScheduleRun
	 * @날짜    : 2018. 3. 28.
	 * @작성자   : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *		주간실적 미작성 인원 SMS 통보 스케쥴러
	 *     </pre>
	 * 
	 * @param 
	 * @return
	 * @throws
	 */
	public void smsNoticeScheduleRun(Map<String, Object> map)
	{
		selectOne("BatchJob.smsNoticeScheduleRun", map);
	}

	/**
	 * 
	 * @메소드명 : dlmGtmOyuScheduleRun
	 * @날짜    : 2018. 3. 28.
	 * @작성자   : Cho HeumJun
	 * @설명
	 * 
	 *     <pre>
	 *		근태관리1년미만자휴가수기준정보(일반휴가 +1) 업데이트 스케쥴러
	 *     </pre>
	 * 
	 * @param 
	 * @return
	 * @throws
	 */
	public void dlmGtmOyuScheduleRun(Map<String, Object> map)
	{
		selectOne("BatchJob.dlmGtmOyuScheduleRun", map);
	}

}