package com.vitasoft.goodsgrapher.domain.service;

import com.vitasoft.goodsgrapher.application.request.LoginRequest;
import com.vitasoft.goodsgrapher.application.response.LoginResponse;
import com.vitasoft.goodsgrapher.application.response.MemberResponse;
import com.vitasoft.goodsgrapher.application.response.TokenResponse;
import com.vitasoft.goodsgrapher.core.security.JwtTokenProvider;
import com.vitasoft.goodsgrapher.domain.exception.member.MemberNotFoundException;
import com.vitasoft.goodsgrapher.domain.model.sso.entity.Member;
import com.vitasoft.goodsgrapher.domain.model.sso.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByProviderId(loginRequest.getProviderId()).orElseThrow(MemberNotFoundException::new);
        MemberResponse memberResponse = new MemberResponse(member);

        String accessToken = jwtTokenProvider.generateJwtToken(member.getMemberId(), member.getMemberRole());
        TokenResponse tokenResponse = new TokenResponse(accessToken);

        return new LoginResponse(memberResponse,tokenResponse);
    }
}
