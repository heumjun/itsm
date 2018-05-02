package com.web.common.exceptionHandler;

import com.web.common.util.MessageUtil;

/**
 * 
 * @파일명		: CommonException.java 
 * @프로젝트	: ITMS
 * @날짜		: 2018. 3. 28. 
 * @작성자		: Cho HeumJun
 * @설명
 * <pre>
 * 		예외처리 클레스
 * </pre>
 */
public class CommonException extends Exception
{

	/**
	 * 키값으로 에러메시지를 찾거나 없으면 입력된 문자열이 에러 메시지
	 * 
	 * @param message
	 */
	public CommonException(String message)
	{
		super(MessageUtil.getMessage(message));
	}

	/**
	 * 키값으로 에러메시지를 찾거나 없으면 입력된 문자열이 에러 메시지
	 * 
	 * @param message
	 * @param subMsg
	 */
	public CommonException(String message, String subMsg)
	{
		super(MessageUtil.getMessage(message, subMsg));
	}

	/**
	 * 키값으로 에러메시지를 찾거나 없으면 입력된 문자열이 에러 메시지
	 * 
	 * @param message
	 * @param subMsg
	 */
	public CommonException(String message, Object subMsg[])
	{
		super(MessageUtil.getMessage(message, subMsg));
	}

	/**
	 * 지정된 메시지가 없을시에는 디폴트 실패 메시지를 지정
	 */
	public CommonException()
	{
		super(MessageUtil.getMessage("common.default.fail", ""));
	}

	/** serialVersionUID */
	private static final long serialVersionUID = -6880204166571853388L;

}