package com.example.boardex.domains.member.domain;

import com.example.boardex.common.value.Address;
import com.example.boardex.domains.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String authId;
    private String authPw;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String name;
    private String phone;
    @Embedded
    private Address address;


    @Builder
    private MemberEntity(String authId, String authPw, String name, String phone, Address address) {
        this.role = Role.USER;
        this.authId = authId;
        this.authPw = authPw;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

}
