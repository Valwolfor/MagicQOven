package com.magicqoven.service.impl;

import com.magicqoven.entity.SavedQueryBuilt;
import com.magicqoven.entity.User;
import com.magicqoven.repository.SavedQueryRepository;
import com.magicqoven.service.inter.SavedQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SavedQueryServiceImpl implements SavedQueryService {

    private final SavedQueryRepository savedQueryRepository;

    @Autowired
    public SavedQueryServiceImpl(SavedQueryRepository savedQueryRepository) {
        this.savedQueryRepository = savedQueryRepository;
    }

    @Override
    public SavedQueryBuilt saveQuery(SavedQueryBuilt query) {
        return savedQueryRepository.save(query);
    }

    @Override
    public List<SavedQueryBuilt> getQueriesByUser(User user) {
        return savedQueryRepository.findByUser(user);
    }

    @Override
    public List<SavedQueryBuilt> getQueriesByName(String name) {
        return savedQueryRepository.findByName(name);
    }

    @Override
    public List<SavedQueryBuilt> getAllQueries() {
        return savedQueryRepository.findAll();
    }

    @Override
    public void deleteQuery(SavedQueryBuilt query) {
        savedQueryRepository.delete(query);
    }
}
