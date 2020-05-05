package com.naver.myhome6.common;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;

public class AroundAdvice {
	/* PreceedingJoinPoint 인터페이스는 JoinPoint를 상속했기떄문에
	 * JoinPoint가 가진 모든 메서드를 지원합니다.
	 * Around Advice에서만 ProceedingJoinPoint를 매개변수로 사용하는데
	 * 이 곳에서 proceed()메서드가 필요하기 때문입니다.
	 * Around Advice인 경우 비즈니스 메서드 실행 전과 후로 실행되는데
	 * 비즈니스 메서드를 호출하기 위해서는
	 * ProceedingJoinPoint의 Proceed()메서드가 필요합니다.
	 * 즉, 클라이언트의 요청을 가로챈 어드바이스는
	 * 클라이언트가 호출한 비즈니스 메서드(ServiceImpl의 get으로 시작하는 메서드)를
	 * 호출하기위해
	 * ProceedingJoinPoint 객체를 매개 변수로 받아 Proceed()메서드를 통해서
	 * 비즈니스 메서드를 호출할 수 있습니다.
	 * proceed()메서드 실행 후 메소드의 반환값을 리턴해야합니다
	 * 
	 */
	
	//PreceedingJoinPoint 인터페이스는 JoinPoint를 상속했기때문에 JoinPoint가
	public Object aroundLog(ProceedingJoinPoint proceeding) throws Throwable{
		System.out.println("========================");
		System.out.println("====[Around result Before]: 비즈니스 메서드 수행전");
		
		StopWatch sw = new StopWatch();
		sw.start();
		//이 코드의 이전과 이후에 공통 기능을 위한 코드를 위치시키면됩니다.
		//대상 객체의 메서드 BoardServiceImpl.getListcount([])를 호출합니다.		
		Object result = proceeding.proceed();
		//후
		System.out.println("====[Around Advice의 after]: 비즈니스 메서드 수행 후 입니다.");
		
		org.aspectj.lang.Signature sig = proceeding.getSignature();
		
		//Object[] GetArgs() : 클라이언트가 메서드를 호출할 때 넘겨준 인자목록을 Object 배열형태로받음
		System.out.printf("[Around Advice의 after] %s.%s(%s) \n", proceeding.getTarget().getClass().getSimpleName(),sig.getName(),Arrays.toString(proceeding.getArgs()));
		
		System.out.println("[Around Advice의 after]"+proceeding.getSignature().getName()+"()메소드 수행시간"+sw.getTotalTimeMillis()+"(ms)초");
		
		System.out.println("[Around Advice의 after] : "+ proceeding.getTarget().getClass().getName());
		
		
		System.out.println("proceeding.proceed() 실행 후 반환값:"+result);
		System.out.println("====================================================================");
		return result;
		//return null이거나 없으면 오류가 발생된다
		
	}
	

}
