package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

/**
 * serviceImpl 내부에서 어떤 구현체를 생성할 것인지를 설정하는 부분
 * AppConfig는 생성한 객체 인스턴스의 참조를 생성자를 통해 주입해준다.
 * ex) return new MemberServiceImpl(new MemoryMemberRepository());
 * new MemoryMemberRepository()로 객체를 생성하고 그 참조값을 MemberServiceImpl에 넣어줌
 * MemberServiceImpl 입장에서는 외부(AppConfig)에서 의존관계(사용할 구현체)를 주입해주는 것 같다고 해서
 * DI(Dependency Injection) 우리말로는 의존관계 주입 또는 의존성 주입이라 한다.
 */
public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }
}