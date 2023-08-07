package com.stock.dividend.service;

import com.stock.dividend.exception.impl.AlreadyExistUserException;
import com.stock.dividend.model.Auth;
import com.stock.dividend.persist.MemberRepository;
import com.stock.dividend.persist.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("couldn't find user -> " + username));
    }
    public MemberEntity register(Auth.SignUp member) {
        // 아이디가 존재하는 경우 exception 발생
        boolean exists = false; // not implemented yet
        if (exists) {
            throw new AlreadyExistUserException();
        }

        // ID 생성 가능한 경우, 멤버 테이블에 저장
        // 비밀번호는 암호화 되어서 저장되어야함
        throw new NotYetImplementedException();
    }

    public MemberEntity authenticate(Auth.SignIn member) {
        // id 로 멤버 조회

        // 패스워드 일치 여부 확인
        //      - 일치하지 않는 경우 400 status 코드와 적합한 에러 메시지 반환
        //      - 일치하는 경우, 해당 멤버 엔티티 반환
        throw new NotYetImplementedException();
    }
}
