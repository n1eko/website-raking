package com.n1eko.websiteranking.repository;

import com.n1eko.websiteranking.model.Category;
import com.n1eko.websiteranking.model.Website;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebsiteRepository extends JpaRepository<Website, Long> {
    List<Website> findAllByCategory(Category category);
}