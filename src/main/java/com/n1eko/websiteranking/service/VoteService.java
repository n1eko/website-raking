package com.n1eko.websiteranking.service;

import com.n1eko.websiteranking.model.Vote;
import com.n1eko.websiteranking.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    public Vote saveVote(Vote vote) {
        return voteRepository.save(vote);
    }

}
