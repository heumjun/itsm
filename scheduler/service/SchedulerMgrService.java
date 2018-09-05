package com.web.scheduler.service;

import java.util.List;
import java.util.Map;

import com.web.common.command.CommandMap;

/**
 * 
 * @파일명    : SchedulerMgrService.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 8. 16.
 * @작성자	: Son JungHwi
 * @설명
 * 
 * <pre>
 * 		배치 서비스 인터페이스
 * </pre>
 */
public interface SchedulerMgrService 
{

	/**
	 * 
	 * @메소드명	: warehousingErrorCnt
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
	public int warehousingErrorCnt(Map<String, Object> map);
	
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
	public int materialsMoveErrorCnt(Map<String, Object> map);
	
	/**
	 * 
	 * @메소드명	: weeklyPerformanceList
	 * @날짜		: 2018. 8. 16.
	 * @작성자	: Son JungHwi
	 * @설명 
	 * 	<pre>
	 *			주간실적 리스트
	 * 	</pre>
	 * @param commandMap
	 * @return weeklyPerformanceList
	 * @throws 
	 */
	public List<Map<String, Object>> weeklyPerformanceList(CommandMap commandMap);
		
	/**
	 * 
	 * @메소드명	: vacationNoticeList
	 * @날짜		: 2018. 8. 16.
	 * @작성자	: Son JungHwi
	 * @설명 
	 * 	<pre>
	 *			차주휴가일정 리스트
	 * 	</pre>
	 * @param commandMap
	 * @return vacationNoticeList
	 * @throws 
	 */
	public List<Map<String, Object>> vacationNoticeList(CommandMap commandMap);
	
	/**
	 * 
	 * @메소드명	: smsNoticeScheduleRun
	 * @날짜		: 2018. 3. 5.
	 * @작성자	: Cho HeumJun
	 * @설명
	 * <pre>
	 *		주간실적 미작성 인원 SMS 통보 스케쥴러
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	void smsNoticeScheduleRun();

	/**
	 * 
	 * @메소드명	: saveManualGridList
	 * @날짜		: 2018. 3. 5.
	 * @작성자	: Cho HeumJun
	 * @설명
	 * <pre>
	 *		근태관리1년미만자휴가수기준정보(일반휴가 +1) 업데이트 스케쥴러
	 * </pre>
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	void dlmGtmOyuScheduleRun();

}
