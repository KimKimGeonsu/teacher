package com.naver.myhome6.common;

//Advice : 횡단 관심에 해당하는 공통기능을 의미하며 독립된 클래스의 메서드로 작성됩니다.
//AfterThrowing(예외 발생했을때 실행)
//타겟 메소드가 수행중에 예외를 던지게 되면 어드바이스 기능을 수행

public class Afterthrowing {
	
	public void afterThrowingLog(Throwable exp) {
		
		System.out.println("==============================");
		System.out.println("[AfterThrowing] : 비즈니스 로직수행중 오류가 발생하면 동작입니다.");
		System.out.println("ex: "+exp.toString());
		System.out.println("==============================");
		
		
	}

}
