package hello.core.member;

public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository; // = new MemoryMemberRepository();

    // 직접 호출하던 구현체를 지우고 생성자를 만듦
    // appConfig에서 구현체를 선택하므로 DIP를 지키는 형태로 완성됨
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
