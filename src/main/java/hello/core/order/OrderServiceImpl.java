package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService {

    /* 객체지향 특징을 살려서 바꿔서 사용할 수 있는 목록 */
    // DB
    // private final MemberRepository memberRepository = new MemoryMemberRepository();
    // 할인 정책 (현재) 고정값 할인

    /* 1) 인터페이스를 선언하면서 구현체 직접 지정 생성 */
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    /*
    * 문제점 발견
    * 위 코드는 역할(DiscountPolicy)과 구현(FixDiscountPolicy)을 충실하게 분리했다.
    * 다형성을 활용했고, 인터페이스와 구현 객체를 분리했다.
    * 그러나 OCP(개방폐쇄원칙), DIP(의존역전원칙) 같은 객체지향 설계 원칙을 준수하지 못했다.
    *
    * private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    * DIP 위반 : 현재 인터페이스도 의존하고 구현체도 의존하는 형태
    * OCP 위반 : OrderServiceImpl 내의 코드를 변경해야 함
    *  */

    /* 2) 인터페이스만 호출 */
    // private DiscountPolicy discountPolicy;  // DIP만 지켜진 상태
    /*
    * 문제점 발견
    * 아래 createOrder() 메소드 내부에서 discountPolicy.discount()로 직접 호출하고 있는데
    * discountPolicy의 구현체를 생성하지 않은 상태로 접근해서 내부 메소드를 호출했기 때문에
    * nullPointException 발생
    * */

    /* 해결책 */
    // OrderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성하고 주입해 주어야 한다.
    // 어떤 구현체를 선택할 것인지를 담당하는 역할은 따로 존재해야 함 ==> AppConfig
    // AppConfig : 애플리케이션의 전체 동작 방식을 구성하기 위해, 구현 객체를 생성하고, 연결하는 책임을 가지는 별도의 설정 클래스

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        // 멤버의 등급이 VIP일 때 할인 정책이 적용되어야 함
        Member member = memberRepository.findById(memberId);
        // 할인에 대한 결과만, 단일책임원칙
        // 정책 관련된 수정사항이 생겨도 cerateOrder 내부에서 코드를 변경하지 않아도 됨
        int discountPrice = discountPolicy.discount(member, itemPrice);
        // 필요한 데이터는 멤버의 등급이지만 프로젝트 확장성을 위해 멤버 객체를 넘김

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
