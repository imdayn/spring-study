package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
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
    // 어떤 구현체를 사용할 것인지를 선택하는 역할을 AppConfig에서 하고 있기 때문에
    // 외부에서 주입해준다고 해서 의존관계 주입, DI 컨테이너라고 한다.
    // IoC 컨테이너, 어샘블러, 오브젝트 팩토리로 부르기도 한다.

    // 추후 XML 파일로 역할을 대신할 수 있음

    public MemberService memberService() {
        // return new MemberServiceImpl(new MemoryMemberRepository()); // Control + Alt + M 으로 Refactor
        return new MemberServiceImpl(memberRepository());
    }

    // 장점
    // 중복 제거 (new MemoryMemberRepository())
    // 역할과 구현 메소드가 한눈에 들어옴 -> 애플리케이션 전체 구성을 빠르게 파악할 수 있음
    // 교체가 쉬움
    private MemberRepository memberRepository() {
        // DB로 교체 시에 아래 코드만 교체하면 됨
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    private DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
        // return new RateDiscountPolicy();
    }
}
