package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    /* 객체지향 특징을 살려서 바꿔서 사용할 수 있는 목록 */
    // DB
    private final MemberRepository memberRepository = new MemoryMemberRepository();
    // 할인 정책 (현재) 고정값 할인
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

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
