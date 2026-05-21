package com.example.feed_app.service;

import com.example.feed_app.config.JwtTokenProvider;
import com.example.feed_app.domain.Member;
import com.example.feed_app.dto.LoginRequest;
import com.example.feed_app.dto.LoginResponse;
import com.example.feed_app.dto.MemberResponse;
import com.example.feed_app.dto.SignupRequest;
import com.example.feed_app.exception.DuplicateEmailException;
import com.example.feed_app.exception.LoginFailedException;
import com.example.feed_app.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberResponse signup(SignupRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException();
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member member = new Member(request.getEmail(), encodedPassword, request.getNickname());
        Member savedMember = memberRepository.save(member);

        return MemberResponse.from(savedMember);
    }

    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(LoginFailedException::new);

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new LoginFailedException();
        }

        String accessToken = jwtTokenProvider.createToken(member);

        return LoginResponse.of(accessToken, member);
    }
}
