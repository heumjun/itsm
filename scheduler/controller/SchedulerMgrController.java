package com.web.scheduler.controller;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.web.common.command.CommandMap;
import com.web.common.controller.CommonController;
import com.web.common.service.CommonService;
import com.web.common.util.DateUtils;
import com.web.common.util.MessageUtil;
import com.web.common.util.StringUtil;
import com.web.scheduler.service.SchedulerMgrService;

/**
 * 
 * @파일명	: SchedulerMgrController.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 8. 16.
 * @작성자	: Son JungHwi
 * @설명
 * <pre>
 * 		배치스케쥴컨트롤러	
 * </pre>
 */
@Component
public class SchedulerMgrController extends CommonController
{
	
	protected Log log = LogFactory.getLog(SchedulerMgrController.class);
	
	@Resource(name = "velocityEngine")
	private VelocityEngine velocityEngine;
	
	@Resource(name = "commonService")
	public CommonService commonService;

	@Resource(name = "schedulerMgrService")
	public SchedulerMgrService schedulerMgrService;

	/**
	 * 
	 * @메소드명	: warehousingErrorNotificationScheduler
	 * @날짜		: 2018. 8. 16.
	 * @작성자	: Son JungHwi
	 * @설명 
	 * 	<pre>
	 *			입고오류 통보 스케쥴러
	 * 	</pre>
	 * @param 
	 * @return
	 * @throws 
	 */
	@Scheduled(cron = "0 0/5 * * * ?") // 테스트용 설정 5분에 한번씩 실행
	//@Scheduled(cron = "0 00 8,11,16 ? * MON-FRI") // 월~금, 매월, 아무 날이나 08:00:00, 16:00:00
	public void warehousingErrorNotificationScheduler()
	{
		
		log.debug("======================================          START         ======================================");
		if(StringUtil.nullString(MessageUtil.getMessage("warehousingError.switch")).equals("Y")) 
		{
			
			log.info("입고오류 통보 스케쥴러 실행 : " + DateUtils.getTodayhhmm());

			// 데이터 취득
			int cnt = schedulerMgrService.warehousingErrorCnt(null);
			log.info("입고오류 건수 : " + cnt);
			
			// 확인
			if (cnt == 0) 
			{
				log.debug("======================================           END          ======================================\n");
				return;
			}
			
			// 수신자 Mail 확인
			CommandMap commandMap = new CommandMap();
			commandMap.put("loginId", MessageUtil.getMessage("warehousingError.receiverId"));
			commandMap.put("approvalAdmin", MessageUtil.getMessage("approval.admin"));
			
			Map<String, String> resultMap = commonService.getUserMail(commandMap);
			
			Template template = velocityEngine.getTemplate("./mailTemplate/errorNotice.template", "UTF-8");
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("contents", "ERP상에서 입고오류 건수가  " + cnt + "건 발생되었습니다.");
			
			StringWriter stringWriter = new StringWriter();
			template.merge(velocityContext, stringWriter);
			
			// 송신
			commandMap.put("i_sender", MessageUtil.getMessage("common.sendMailAddr"));
			// 수신
			commandMap.put("i_receiver", resultMap.get("create_mail"));
			// 제목
			commandMap.put("i_subject", "오류알림메일 : 입고오류");
			// 메세지
			commandMap.put("i_message", stringWriter.toString());
			
			// 메일전송
			try
			{
				commonService.sendMail(commandMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} 
		else 
		{
			log.info("입고오류 통보 메일 스위치가 Off 상태 입니다. system.properties에서 On으로 변경후 재기동 해주세요.");
		}
		
		log.debug("======================================           END          ======================================\n");
	}
	
	/**
	 * 
	 * @메소드명	: materialsMoveErrorNotificationScheduler
	 * @날짜		: 2018. 8. 16.
	 * @작성자	: Son JungHwi
	 * @설명 
	 * 	<pre>
	 *			자재이동 오류 통보 스케쥴러
	 * 	</pre>
	 * @param 
	 * @return
	 * @throws 
	 */
	@Scheduled(cron = "0 0/5 * * * ?") // 테스트용 설정 5분에 한번씩 실행
	//@Scheduled(cron = "0 00 16 ? * MON-FRI") // 월~금, 매월, 아무 날이나 16:00:00
	public void materialsMoveErrorNotificationScheduler()
	{
		log.debug("======================================          START         ======================================");
		if(StringUtil.nullString(MessageUtil.getMessage("materialsMoveError.switch")).equals("Y")) 
		{
			log.info("자재이동 오류 통보 스케쥴러 실행 : " + DateUtils.getTodayhhmm());
			
			// 데이터 취득
			int cnt = schedulerMgrService.materialsMoveErrorCnt(null);
			log.info("자재이동 오류 건수 : " + cnt);
			
			// 확인
			if (cnt == 0) 
			{
				log.debug("======================================           END          ======================================\n");
				return;
			}
			
			// 수신자 Mail 확인
			CommandMap commandMap = new CommandMap();
			commandMap.put("loginId", MessageUtil.getMessage("materialsMoveError.receiverId"));
			commandMap.put("approvalAdmin", MessageUtil.getMessage("approval.admin"));
			Map<String, String> resultMap = commonService.getUserMail(commandMap);
			
			Template template = velocityEngine.getTemplate("./mailTemplate/errorNotice.template", "UTF-8");
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("contents", "ERP상에서 자재이동오류 건수가 " + cnt + "건 발생되었습니다.");
			
			StringWriter stringWriter = new StringWriter();
			template.merge(velocityContext, stringWriter);
			
			// 송신
			commandMap.put("i_sender", MessageUtil.getMessage("common.sendMailAddr"));
			// 수신
			commandMap.put("i_receiver", resultMap.get("create_mail"));
			// 제목
			commandMap.put("i_subject", "오류알림메일 : 자재이동 오류");
			// 메세지
			commandMap.put("i_message", stringWriter.toString());
			
			// 메일전송
			try 
			{
				commonService.sendMail(commandMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		else 
		{
			log.info("자재이동 오류 통보 메일 스위치가 Off 상태 입니다. system.properties에서 On으로 변경후 재기동 해주세요.");
		}
		
		log.debug("======================================           END          ======================================\n");
	}
	
	/**
	 * 
	 * @메소드명	: weeklyPerformanceNotificationScheduler
	 * @날짜		: 2018. 8. 16.
	 * @작성자	: Son JungHwi
	 * @설명 
	 * 	<pre>
	 *			주간실적 집계 통보 스케쥴러
	 * 	</pre>
	 * @param 
	 * @return
	 * @throws 
	 */
	@Scheduled(cron = "0 0/5 * * * ?") // 테스트용 설정 5분에 한번씩 실행
	//@Scheduled(cron = "0 30 16 ? * THU") // 매주 목요일 오후4시30분
	public void weeklyPerformanceNotificationScheduler()
	{
		log.debug("======================================          START         ======================================");
		if(StringUtil.nullString(MessageUtil.getMessage("weeklyPerformance.switch")).equals("Y")) 
		{
			log.info("주간실적 통보 스케쥴러 실행 : " + DateUtils.getTodayhhmm());
			
			// 데이터 취득
			CommandMap commandMap = new CommandMap();
			List<Map<String, Object>> list = schedulerMgrService.weeklyPerformanceList(commandMap);
			
			StringBuilder sb = new StringBuilder();
			StringBuilder titleSb = new StringBuilder();
			
			//년월주차 출력
			for (Map<String, Object> map : list) 
			{
				titleSb.append(map.get("YEAR") + "년 " + map.get("MONTH") + "월 " + map.get("CHASU") + "주차 주간실적 집계");
				sb.append("<font size=5><b>" + map.get("YEAR") + "년 " + map.get("MONTH") + "월 " + map.get("CHASU") + "주차 주간실적 집계  </b></font>");
				break;
			}
			
			//"ERP사업영역"
			boolean eFlag = false;
			boolean lFlag = false;
			
			//개인별 주간실적 출력
			for (Map<String, Object> map : list) 
			{
				String gubun = map.get("ERP_LEGACY_GUBUN").toString();
				if ("E".equals(gubun)) {
					if (!eFlag) {
						sb.append("<font size=2><p>&nbsp;--------------------------------------------------------------------------------------------------</p></font><br/>");
						sb.append("<font size=5><b>1.ERP파트</b></font><br/>");
						eFlag = true;
					}
				} else if ("L".equals(gubun)) {
					if (!lFlag) {
						sb.append("<font size=2><p>&nbsp;--------------------------------------------------------------------------------------------------</p></font><br/>");
						sb.append("<font size=5><b>2.LEGACY파트</b></font><br/>");
						lFlag = true;
					}
				}
				
				sb.append("<font size=3><b>*작성자: " + map.get("NAME") + "(" + map.get("BUSINESS_ROLE") + ")</b></font>");
				sb.append("<br/>");
				sb.append(map.get("CONTENTS"));
				sb.append("<br/>");
			}
			//log.info("주간실적 : " + sb.toString());
			
			// 수신자 Mail 확인
			commandMap.put("loginId", MessageUtil.getMessage("weeklyPerformance.receiverId"));
			commandMap.put("approvalAdmin", MessageUtil.getMessage("approval.admin"));
			Map<String, String> resultMap = commonService.getUserMail(commandMap);
			
			Template template = velocityEngine.getTemplate("./mailTemplate/weeklyReport.template", "UTF-8");
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("contents", sb.toString());
			
			StringWriter stringWriter = new StringWriter();
			template.merge(velocityContext, stringWriter);
			
			System.out.println(stringWriter.toString());
			
			// 송신
			commandMap.put("i_sender", MessageUtil.getMessage("common.sendMailAddr"));
			// 수신
			commandMap.put("i_receiver", resultMap.get("create_mail"));
			// 제목
			commandMap.put("i_subject", titleSb.toString());
			// 메세지
			commandMap.put("i_message", stringWriter.toString());
			
			// 메일전송
			try 
			{
				commonService.sendMail(commandMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		else 
		{
			log.info("주간실적 집계 통보 메일 스위치가 Off 상태 입니다. system.properties에서 On으로 변경후 재기동 해주세요.");
		}
		
		log.debug("======================================           END          ======================================\n");
	}
	
	/**
	 * 
	 * @메소드명	: nextWeekVacationNotificationScheduler
	 * @날짜		: 2018. 8. 16.
	 * @작성자	: Son JungHwi
	 * @설명 
	 * 	<pre>
	 *			차주휴가일정통보 스케쥴러
	 * 	</pre>
	 * @param 
	 * @return
	 * @throws 
	 */
	@Scheduled(cron = "0 0/5 * * * ?") // 테스트용 설정 5분에 한번씩 실행
	//@Scheduled(cron = "0 30 16 ? * THU") // 매주 목요일 오후4시30분
	public void nextWeekVacationNotificationScheduler()
	{
		log.debug("======================================          START         ======================================");
		if(StringUtil.nullString(MessageUtil.getMessage("nextWeekVacation.switch")).equals("Y")) 
		{
			log.info("차주휴가일정 통보 스케쥴러 실행 : " + DateUtils.getTodayhhmm());
			
			// 데이터 취득
			CommandMap commandMap = new CommandMap();
			List<Map<String, Object>> list = schedulerMgrService.vacationNoticeList(commandMap);
			
			//sb.append("<font size=5><b>" + map.get("YEAR") + "년 " + map.get("MONTH") + "월 " + map.get("CHASU") + "주차 주간실적 집계  </b></font>");
			// 날짜랑 주차 가져와야됨.
			
			Date today = new Date();
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(today);
			
			//일주일후 
			cal.add(Calendar.DAY_OF_MONTH,+7);  
			
			String week = String.valueOf(cal.get(Calendar.WEEK_OF_MONTH));
			
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("MM-dd");
			cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
			String monday = formatter.format(cal.getTime());
	
	 		cal.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
	 		String friday = formatter.format(cal.getTime());
	 		
	 		StringBuilder sb2 = new StringBuilder();
	 		sb2.append(String.valueOf(cal.get(Calendar.YEAR)));
	 		sb2.append("년 ");
	 		sb2.append(String.valueOf(cal.get(Calendar.MONTH)+1));
	 		sb2.append("월 ");
	 		sb2.append(week);
	 		sb2.append("주차(");
	 		sb2.append(monday);
	 		sb2.append("~");
	 		sb2.append(friday);
	 		sb2.append(") 휴가예정 집계 ");
	
	 		StringBuilder sb = new StringBuilder();
	 		
			for (Map<String, Object> map : list) 
			{
				sb.append("<tr>");
				sb.append("<td align='center' style='width:100;'><b>"+map.get("USER_NAME")+"</b></td>");
				sb.append("<td align='center' style='width:100;'>"+map.get("VAC_TYPE_NAME")+"</td>");
				sb.append("<td align='center' style='width:100;'>"+map.get("VAC_START_DATE")+"</td>");
				sb.append("<td align='center' style='width:100;'>"+map.get("VAC_END_DATE")+"</td>");
				sb.append("<td align='center' style='width:100;'>"+map.get("VAC_REQ_DAY")+"</td>");
				sb.append("</tr>");
			}
			//System.out.println("차주휴가일정 : " + sb.toString());
			
			// 수신자 Mail 확인
			commandMap.put("loginId", MessageUtil.getMessage("nextWeekVacation.receiverId"));
			commandMap.put("approvalAdmin", MessageUtil.getMessage("approval.admin"));
			Map<String, String> resultMap = commonService.getUserMail(commandMap);
			
			Template template = velocityEngine.getTemplate("./mailTemplate/weeklyReport.template", "UTF-8");
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("contents", sb.toString());
			
			StringWriter stringWriter = new StringWriter();
			template.merge(velocityContext, stringWriter);
			
			// 송신
			commandMap.put("i_sender", MessageUtil.getMessage("common.sendMailAddr"));
			// 수신
			commandMap.put("i_receiver", resultMap.get("create_mail"));
			// 제목
			commandMap.put("i_subject", sb2.toString());
			// 메세지
			commandMap.put("i_message", stringWriter.toString());
			
			// 메일전송
			try 
			{
				commonService.sendMail(commandMap);
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
		else 
		{
			log.info("차주휴가일정 통보 메일 스위치가 Off 상태 입니다. system.properties에서 On으로 변경후 재기동 해주세요.");
		}
		
		log.debug("======================================           END          ======================================\n");
	}
	
	/**
	 *
	 * @메소드명	: weeklyPerformanceMissNotificationScheduler
	 * @날짜		: 2018. 5. 9.
	 * @작성자	: Cho HeumJun
	 * @설명
	 * <pre>
	 *		주간실적 미작성 인원 SMS 통보 스케쥴러
	 * </pre>
	 */
	//@Scheduled(cron = "0 0/5 * * * ?") // 테스트용 설정 5분에 한번씩 실행
	//@Scheduled(cron = "0 30 16 * * WED")
	public void weeklyPerformanceMissNotificationScheduler()
	{
		if (log.isDebugEnabled())
		{
			log.debug("======================================          START         ======================================");
		}
		
		// system.properties 에서 SMS설정이 Y가 되어있어야 발송
		if(StringUtil.nullString(MessageUtil.getMessage("common.smsSendSwitch")).equals("Y")) 
		{
			log.info("주간실적 미작성 인원 SMS 통보 스케쥴러 실행 : " + DateUtils.getTodayhhmm());
			schedulerMgrService.smsNoticeScheduleRun();
		}
		
		if (log.isDebugEnabled())
		{
			log.debug("======================================           END          ======================================\n");
		}
	}

	/**
	 * 
	 * @메소드명	: dlmGtmOyuNotificationScheduler
	 * @날짜		: 2018. 5. 9.
	 * @작성자	: Cho HeumJun
	 * @설명
	 * <pre>
	 *		근태관리1년미만자휴가수기준정보(일반휴가 +1) 업데이트 스케쥴러
	 * </pre>
	 */
	//@Scheduled(cron = "0 0/5 * * * ?") // 테스트용 설정 5분에 한번씩 실행	
	//@Scheduled(cron = "0 1 0 * * ?" )
	public void dlmGtmOyuNotificationScheduler()
	{
		if (log.isDebugEnabled())
		{
			log.debug("======================================          START         ======================================");
		}

		// system.properties 에서 SMS설정이 Y가 되어있어야 발송
		if(StringUtil.nullString(MessageUtil.getMessage("common.smsSendSwitch")).equals("Y")) 
		{
			log.info("근태관리1년미만자휴가수기준정보(일반휴가 +1) 업데이트 스케쥴러 실행 : " + DateUtils.getTodayhhmm());
			schedulerMgrService.dlmGtmOyuScheduleRun();
		}
		
		if (log.isDebugEnabled())
		{
			log.debug("======================================           END          ======================================\n");
		}
	}

}
