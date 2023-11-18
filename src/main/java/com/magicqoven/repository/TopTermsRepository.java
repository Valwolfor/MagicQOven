package com.magicqoven.repository;

import com.magicqoven.entity.TopTerms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TopTermsRepository extends JpaRepository<TopTerms, Long> {
    @Query("SELECT t FROM TopTerms t WHERE t.week BETWEEN :week AND :refreshWeek")
    List<TopTerms> getBetweenWeekAndRefreshWeeK(LocalDate week, LocalDate refresh);

    TopTerms findByDmaName(String dmaName);

    List<TopTerms> findByTerm(String term);

    List<TopTerms> findByScore(Integer score);

    List<TopTerms> findBytermRank(Integer termRank);
}
