package com.n1eko.websiteranking.repository;

import com.n1eko.websiteranking.model.Category;
import com.n1eko.websiteranking.model.Website;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebsiteRepository extends JpaRepository<Website, Long> {
    List<Website> findAllByCategory(Category category);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Website findWebsiteForUpdateById(Long id);

}