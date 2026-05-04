package com.example.crud.domain;

import jakarta.persistence.*;

@Entity // class가 DB table과 1:1로 매칭되도록 ㅏㅁ
@Table(name = "members")
public class Member {
    
    @Id // 이 필드가 Primary Key(PK)임을 뜻함
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID를 1, 2, 3... 자동으로 생성
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    public Member() {}

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
}
