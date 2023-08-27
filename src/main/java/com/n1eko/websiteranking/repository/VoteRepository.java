package com.n1eko.websiteranking.repository;

import com.n1eko.websiteranking.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("SELECT v FROM Vote v WHERE v.ip = :ip AND v.time >= :timeThreshold")
    List<Vote> findAllByIpAndTimeThreshold(String ip, LocalDateTime timeThreshold);


}