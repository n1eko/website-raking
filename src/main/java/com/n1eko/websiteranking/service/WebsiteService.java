package com.n1eko.websiteranking.service;

import com.n1eko.websiteranking.model.Website;
import com.n1eko.websiteranking.repository.WebsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class WebsiteService {

    @Autowired
    private WebsiteRepository websiteRepository;

    public Optional<Website> findWebsiteById(Long websiteId) {
        return websiteRepository.findById(websiteId);
    }

    @Transactional
    public void upvoteWebsite(Long websiteId) {
        Website website = websiteRepository.findWebsiteForUpdateById(websiteId);
        website.setVotes(website.getVotes() + 1);
        websiteRepository.save(website);
    }

    @Transactional
    public void downvoteWebsite(Long websiteId) {
        Website website = websiteRepository.findWebsiteForUpdateById(websiteId);
        website.setVotes(website.getVotes() - 1);
        websiteRepository.save(website);
    }

}
