package com.example.boardex.repository;

import com.example.boardex.domains.entity.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long> , QuerydslPredicateExecutor<Guestbook> {
}
