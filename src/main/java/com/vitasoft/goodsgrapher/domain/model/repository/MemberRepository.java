package com.vitasoft.goodsgrapher.domain.model.repository;

import com.vitasoft.goodsgrapher.domain.model.entity.Member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByProviderId(String providerId);
}
