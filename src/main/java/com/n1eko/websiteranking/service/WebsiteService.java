package com.n1eko.websiteranking.service;

import com.n1eko.websiteranking.model.Category;
import com.n1eko.websiteranking.model.Website;
import com.n1eko.websiteranking.repository.WebsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebsiteService {

    @Autowired
    private WebsiteRepository websiteRepository;

    public List<Website> getAllWebsitesByCategory(Category category) {
        return websiteRepository.findAllByCategory(category);
    }

}
