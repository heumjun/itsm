package com.web.common.interceptor;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.web.common.command.CommandMap;
import com.web.common.constants.Constants;
import com.web.common.util.SessionUtil;
import com.web.main.logIn.dao.LoginDAO;

/**
 * 
 * @파일명 : Interceptor.java
 * @프로젝트 : ITMS
 * @날짜 : 2018. 3. 28.
 * @작성자 : Cho HeumJun
 * @설명
 * 
 *     <pre>
 * 		처리 로그를 남기기 위한 인터셉터
 *     </pre>
 */
public class Interceptor extends HandlerInterceptorAdapter
{
	protected Log		log	= LogFactory.getLog(Interceptor.class);

	@Resource(name = "loginDAO")
	private LoginDAO	loginDAO;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{

		String client_ip = request.getHeader("X-FORWARDED-FOR");
		if (client_ip == null)
		{
			client_ip = request.getRemoteAddr();
		}

		// MailUtil.mailSender(request);

		if (log.isDebugEnabled())
		{
			log.debug(
					"======================================          START         ======================================");
			log.debug(" Request URI \t:  " + request.getRequestURI());
		}

		/*
		 * 세션 체크 로직 1. SESSION_LOGIN_USER_OBJ가 없으면 로그인 화면으로 보냄.
		 */
		if (!request.getRequestURI().equals("/") && !request.getRequestURI().equals("/login.do")
				&& !request.getRequestURI().equals("/main.memberJoin.memberJoin.do")
				&& !request.getRequestURI().equals("/main.memberJoin.userIdCheck.do")
				&& !request.getRequestURI().equals("/main.passwdInit.passwdInit.do")
				&& !request.getRequestURI().equals("/main.memberJoin.memberJoinRegiste.do")
				&& !request.getRequestURI().equals("/main.memberJoin.memberQuestionSelectBoxDataList.do")
				&& !request.getRequestURI().equals("/main.memberJoin.memberPositionSelectBoxDataList.do")
			&& !request.getRequestURI().equals("/main.passwdInit.passwdInitAction.do"))
		{
			HttpSession session = request.getSession();

			if ((session.getAttribute(Constants.SESSION_LOGIN_USER_OBJ) == null)
					|| (session.getAttribute(Constants.SESSION_LOGIN_USER_OBJ).toString().equals("")))
			{

				if (request.getParameter("login_id") == null && request.getParameter("loginID") == null)
				{
					// response.sendRedirect(request.getContextPath());
					PrintWriter out = response.getWriter();
					out.println("<html>");
					out.println("<script>");
					out.println("window.open ('" + request.getContextPath() + "/','_top')");
					out.println("</script>");
					out.println("</html>");
					return false;
				}
				else
				{
					// 로그인페이지가 아니면 실행
					// 파라미터로 자동 로그인 세션 생성
					// loginID로 넘겨준다.
					if (!request.getRequestURI().equals("/loginCheck.do"))
					{
						CommandMap commandMap = new CommandMap();
						Object loginUser = loginDAO.selectLogin(commandMap.getMap());
						if (loginUser != null)
						{
							// 유저정보가 있는경우 유저정보를 세션에 설정
							SessionUtil.setLoginUserObject(loginUser);
						}
						else
						{
							// response.sendRedirect(request.getContextPath());
							PrintWriter out = response.getWriter();
							out.println("<html>");
							out.println("<script>");
							out.println("window.open ('" + request.getContextPath() + "/','_top')");
							out.println("</script>");
							out.println("</html>");
							return true;
						}
					}

				}
			}
		}
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception
	{
		if (log.isDebugEnabled())
		{
			log.debug(
					"======================================           END          ======================================\n");
		}
	}
}
