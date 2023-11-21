package com.magicqoven.service.inter;

import com.magicqoven.entity.SavedQueryBuilt;
import com.magicqoven.entity.User;

import java.util.List;

public interface SavedQueryService {
    SavedQueryBuilt saveQuery(SavedQueryBuilt query);
    List<SavedQueryBuilt> getQueriesByUser(User user);
    List<SavedQueryBuilt> getQueriesByName(String name);
    List<SavedQueryBuilt> getAllQueries();
    void deleteQuery(SavedQueryBuilt query);
}
