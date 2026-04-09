package com.example.crud.controller;

import com.example.crud.domain.Member;
import com.example.crud.repository.MemberRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberRepository repository;

    public MemberController(MemberRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Member save(@RequestBody Member member) {
        return repository.save(member);
    }

    @GetMapping
    public List<Member> findAll() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Member update(@PathVariable Long id, @RequestBody Member memberDetails) {
        Member member = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 회원이 없습니다. id = " + id));
        
        member.setName(memberDetails.getName());
        member.setEmail(memberDetails.getEmail());

        return repository.save(member);
    }
    
}
