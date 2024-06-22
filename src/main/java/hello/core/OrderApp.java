package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class OrderApp {

    public static void main(String[] args) {
        // service 가져다 사용하려고 선언, 보통 controller 에서 호출함
        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();

        // 1. 객체 정보 생성
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        // 회원가입 = memberRepository 에 save
        memberService.join(member);

        // 2. 주문 생성
        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);
        System.out.println("order.calculatePrice = " + order.calculatePrice());
    }

}