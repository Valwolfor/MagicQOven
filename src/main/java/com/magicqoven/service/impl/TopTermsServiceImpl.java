package com.magicqoven.service.impl;

import com.magicqoven.entity.TopTerms;
import com.magicqoven.repository.TopTermsRepository;
import com.magicqoven.service.inter.TopTermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TopTermsServiceImpl implements TopTermsService {

    private final TopTermsRepository repository;

    @Autowired
    public TopTermsServiceImpl(TopTermsRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TopTerms> getAllData() {
        return repository.findAll();
    }

    @Override
    public void saveData(TopTerms data) {
        repository.save(data);
    }

    @Override
    public void saveAllData(List<TopTerms> data) {
        repository.saveAll(data);
    }

    @Override
    public List<TopTerms> getBetweenWeekAndRefreshWeeK(LocalDate week, LocalDate refresh) {
        return repository.getBetweenWeekAndRefreshWeeK(week, refresh);
    }

    @Override
    public TopTerms getByDmaId(Long id) {
        return repository.getReferenceById(id);
    }

    @Override
    public TopTerms getByDmaName(String dmaName) {
        return repository.findByDmaName(dmaName);
    }

    @Override
    public List<TopTerms> getByTerm(String term) {
        return repository.findByTerm(term);
    }

    @Override
    public List<TopTerms> getByScore(Integer score) {
        return repository.findByScore(score);
    }

    @Override
    public List<TopTerms> getByRank(Integer rank) {
        return repository.findBytermRank(rank);
    }
}
