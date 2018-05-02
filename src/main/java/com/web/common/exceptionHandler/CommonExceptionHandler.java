package com.web.common.exceptionHandler;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.common.constants.Constants;
import com.web.common.service.CommonService;

/**
 * 
 * @파일명		: CommonExceptionHandler.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 3. 28. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		예외처리 핸들러
 * </pre>
 */
@ControllerAdvice
public class CommonExceptionHandler
{

	@Resource(name = "commonService")
	private CommonService commonService;

	/**
	 * 
	 * @메소드명	: handleGeException
	 * @날짜		: 2018. 3. 28.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		Exception인 경우 에러메시지 반환
	 * </pre>
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler(CommonException.class)
	public @ResponseBody Map<String, String> handleGeException(Exception e, HttpServletRequest request)
	{
		Map<String, String> resut = new HashMap<String, String>();
		resut.put(Constants.RESULT_KEY, Constants.RESULT_FAIL);
		resut.put(Constants.RESULT_MASAGE_KEY, e.getMessage());
		return resut;
	}

	/**
	 * 
	 * @메소드명	: handleException
	 * @날짜		: 2018. 3. 28.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		Exception인 경우 에러서비스를 실행후 에러메시지 반환
	 * </pre>
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public @ResponseBody Map<String, String> handleException(Exception e, HttpServletRequest request)
	{
		commonService.errorService(e, request);
		return handleGeException(e, request);
	}

	/**
	 * 
	 * @메소드명	: handleRuntimeException
	 * @날짜		: 2018. 3. 28.
	 * @작성자		: Cho HeumJun
	 * @설명
	 * <pre>
	 *		RuntimeException인경우 에러서비스를 실행후 에러메시지 반환
	 * </pre>
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler(RuntimeException.class)
	public @ResponseBody Map<String, String> handleRuntimeException(RuntimeException e, HttpServletRequest request)
	{
		commonService.errorService(e, request);
		return handleGeException(e, request);
	}
}