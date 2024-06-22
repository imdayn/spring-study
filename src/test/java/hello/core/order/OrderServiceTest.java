package hello.core.order;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    MemberService memberService = new MemberServiceImpl();
    OrderService orderService = new OrderServiceImpl();

    @Test
    void createOrder() {
        // 1. 객체 정보 생성
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        // 회원가입 = memberRepository 에 save
        memberService.join(member);

        // 2. 주문 생성
        Order order = orderService.createOrder(memberId, "itemA", 10000);

        // Test
        // 결과값을 isEqualTo로 비교해서 확인
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
