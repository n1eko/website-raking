package com.n1eko.websiteranking.controller;


import com.n1eko.websiteranking.model.Vote;
import com.n1eko.websiteranking.model.VoteType;
import com.n1eko.websiteranking.model.Website;
import com.n1eko.websiteranking.service.VoteService;
import com.n1eko.websiteranking.service.WebsiteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@CrossOrigin(origins = "${security.cors.allowed-origin}", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class VotingController {

    @Autowired
    private VoteService voteService;
    @Autowired
    private WebsiteService websiteService;

    @PostMapping("/vote")
    public ResponseEntity vote(@RequestParam Long websiteId, @RequestParam VoteType voteType, HttpServletRequest request) {
        Optional<Website> website = websiteService.findWebsiteById(websiteId);
        if (website.isPresent() && voteType != null) {
            voteService.saveVote(Vote.builder()
                    .voteType(voteType)
                    .ip(request.getRemoteAddr())
                    .website(website.get())
                    .time(LocalDateTime.now())
                    .build());
            if (voteType.equals(VoteType.UPVOTE)) {
                websiteService.upvoteWebsite(website.get().getId());
            } else {
                websiteService.downvoteWebsite(website.get().getId());
            }
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
