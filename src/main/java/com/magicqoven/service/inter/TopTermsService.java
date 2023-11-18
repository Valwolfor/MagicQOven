package com.magicqoven.service.inter;

import com.magicqoven.entity.TopTerms;
import com.magicqoven.repository.TopTermsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


public interface TopTermsService {

    public List<TopTerms> getAllData();

    public void saveData(TopTerms data);
    public void saveAllData(List<TopTerms> data);

    public List<TopTerms> getBetweenWeekAndRefreshWeeK(LocalDate week, LocalDate refresh);

    public TopTerms getByDmaId(Long id);

    public TopTerms getByDmaName(String dmaName);

    public List<TopTerms> getByTerm(String term);

    public List<TopTerms> getByScore(Integer score);

    public List<TopTerms> getByRank(Integer rank);
}
