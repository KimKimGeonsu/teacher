package com.naver.myhome6.common;

import org.aspectj.lang.JoinPoint;

public class BeforeAdvice {

	public void beforeLog(JoinPoint proceeding) {
		/*
		 * 출력내용 [BeforeAdvice] : 비즈니스 로직 수행 전 동작입니다. [BeforeAdvice] :
		 * com.json.jsonroot.service.ServiceImpl의 get_whereid를 호출합니다
		 * 
		 * 
		 */

		System.out.println("[BeforeAdvice] : 비즈니스 로직 수행 전 동작입니다");
		System.out.println("[BeforeAdvice] :" + proceeding.getTarget().getClass().getName() + "의"
				+ proceeding.getSignature().getName() + "호출합니다.");

	}

}
