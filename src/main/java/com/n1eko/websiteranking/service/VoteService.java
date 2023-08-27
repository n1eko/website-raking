package com.n1eko.websiteranking.service;

import com.n1eko.websiteranking.model.Vote;
import com.n1eko.websiteranking.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    public Vote saveVote(Vote vote) {
        return voteRepository.save(vote);
    }

    public int countVotesForIpWithinLast24Hours(String ip) {
        LocalDateTime timeThreshold = LocalDateTime.now().minus(24, ChronoUnit.HOURS);
        return voteRepository.findAllByIpAndTimeThreshold(ip, timeThreshold).size();
    }

}
