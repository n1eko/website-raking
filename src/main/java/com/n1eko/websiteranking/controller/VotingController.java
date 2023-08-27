package com.n1eko.websiteranking.controller;

import com.n1eko.websiteranking.model.Vote;
import com.n1eko.websiteranking.model.VoteType;
import com.n1eko.websiteranking.model.Website;
import com.n1eko.websiteranking.service.VoteService;
import com.n1eko.websiteranking.service.WebsiteService;
import com.n1eko.websiteranking.utils.HttpUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.Optional;

@CrossOrigin(origins = "${security.cors.allowed-origin}", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class VotingController {

    Logger logger = LoggerFactory.getLogger(VotingController.class);
    @Autowired
    private VoteService voteService;
    @Autowired
    private WebsiteService websiteService;
    @Value("${vote.max-allowed-per-day}")
    private int maxAllowedVotesPerDay;

    @PostMapping("/vote")
    public ResponseEntity vote(@RequestParam Long websiteId, @RequestParam VoteType voteType, HttpServletRequest request) {
        debugMethod(request);
        String clientIp = HttpUtils.getClientIpAddr(request);
        if (voteService.countVotesForIpWithinLast24Hours(clientIp) < maxAllowedVotesPerDay) {
            Optional<Website> website = websiteService.findWebsiteById(websiteId);
            if (website.isPresent() && voteType != null) {
                voteService.saveVote(Vote.builder()
                        .voteType(voteType)
                        .ip(clientIp)
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
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }

    private void debugMethod(HttpServletRequest request){
        Enumeration<String> headerNames = request.getHeaderNames();

        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                logger.info("Header: " + request.getHeader(headerNames.nextElement()));
            }
        }
    }

}
