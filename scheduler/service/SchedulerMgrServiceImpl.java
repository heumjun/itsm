package com.web.scheduler.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.web.common.command.CommandMap;
import com.web.common.exceptionHandler.CommonException;
import com.web.common.util.StringUtil;
import com.web.scheduler.dao.SchedulerMgrDAO;

/**
 * 
 * @파일명    : SchedulerMgrServiceImpl.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 8. 16.
 * @작성자	: Son JungHwi
 * @설명
 * 
 * <pre>
 * 		배치 서비스 
 * </pre>
 */
@Service("schedulerMgrService")
public class SchedulerMgrServiceImpl implements SchedulerMgrService
{

	/**
	 * 배치 DAO정의
	 */
	@Resource(name = "schedulerMgrDAO")
	private SchedulerMgrDAO schedulerMgrDAO;
	
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
	@Override
	public int warehousingErrorCnt(Map<String, Object> map)
	{
		int cnt = 0;
		
		try 
		{
					
			cnt = schedulerMgrDAO.warehousingErrorCnt(null);
			
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
		return cnt;
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
	@Override
	public int materialsMoveErrorCnt(Map<String, Object> map)
	{
		int cnt = 0;
		
		try 
		{
					
			cnt = schedulerMgrDAO.materialsMoveErrorCnt(null);
			
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
		return cnt;
	}
	
	/**
	 * 
	 * @메소드명	: weeklyPerformanceList
	 * @날짜		: 2018. 8. 16.
	 * @작성자	: Son JungHwi
	 * @설명 
	 * 	<pre>
	 *			차주휴가일정 리스트
	 * 	</pre>
	 * @param commandMap
	 * @return weeklyPerformanceList
	 * @throws 
	 */
	@Override
	public List<Map<String, Object>> weeklyPerformanceList(CommandMap commandMap)
	{
		
		List<Map<String, Object>> listData = schedulerMgrDAO.weeklyPerformanceList(commandMap.getMap());
		return listData;
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
	 * @param commandMap
	 * @return vacationNoticeList
	 * @throws 
	 */
	@Override
	public List<Map<String, Object>> vacationNoticeList(CommandMap commandMap)
	{
		
		List<Map<String, Object>> listData = schedulerMgrDAO.vacationNoticeList(commandMap.getMap());
		return listData;
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
	@Override
	public void smsNoticeScheduleRun()
	{
		String sErrorMsg = "";
		String sErrCode = "";

		try 
		{
			Calendar cal = Calendar.getInstance();
			String strYear = String.valueOf(cal.get(Calendar.YEAR)); 
			String strMonth = String.valueOf(cal.get(Calendar.MONTH)+1); 
			String strChasu = String.valueOf(cal.get(Calendar.WEEK_OF_MONTH));
			
			Map<String, Object> pkgParam = new HashMap<String, Object>();
			pkgParam.put("p_gubun", "A");
			pkgParam.put("p_year" , strYear);
			pkgParam.put("p_month", strMonth);
			pkgParam.put("p_chasu", strChasu);
			
			// 프로시저는 넘겨준 MAP에 결과가 리턴된다.
			schedulerMgrDAO.smsNoticeScheduleRun(pkgParam);
			
			// 프로시저 결과 받음
			sErrorMsg = StringUtil.nullString(pkgParam.get("p_error_message"));
			sErrCode = StringUtil.nullString(pkgParam.get("p_error_code"));

			// 오류가 있으면 스탑
			if (!"S".equals(sErrCode)) 
			{
				throw new CommonException(sErrorMsg);
			}
		} 
		catch (Exception e) 
		{
			System.out.println(sErrCode);
			System.out.println(sErrorMsg);
		}
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
	@Override
	public void dlmGtmOyuScheduleRun()
	{
		String sErrorMsg = "";
		String sErrCode = "";

		try 
		{
			Map<String, Object> pkgParam = new HashMap<String, Object>();
			pkgParam.put("p_gubun", "A");
			
			// 프로시저는 넘겨준 MAP에 결과가 리턴된다.
			schedulerMgrDAO.dlmGtmOyuScheduleRun(pkgParam);
			
			// 프로시저 결과 받음
			sErrorMsg = StringUtil.nullString(pkgParam.get("p_error_message"));
			sErrCode = StringUtil.nullString(pkgParam.get("p_error_code"));

			// 오류가 있으면 스탑
			if (!"S".equals(sErrCode)) 
			{
				throw new CommonException(sErrorMsg);
			}

		} 
		catch (Exception e) 
		{
			System.out.println(sErrCode);
			System.out.println(sErrorMsg);
		}
	}

}
