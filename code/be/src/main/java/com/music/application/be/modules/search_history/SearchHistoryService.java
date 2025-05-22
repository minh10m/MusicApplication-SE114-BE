package com.music.application.be.modules.search_history;

import com.music.application.be.modules.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;

    @CacheEvict(value = "searchHistory", key = "#user.id")
    public SearchHistory addSearchHistory(User user, String query) {
        SearchHistory searchHistory = SearchHistory.builder()
                .user(user)
                .query(query)
                .build();
        return searchHistoryRepository.save(searchHistory);
    }

    @Cacheable(value = "searchHistory", key = "#user.id")
    public List<SearchHistory> getSearchHistoryByUser(User user) {
        return searchHistoryRepository.findByUserOrderBySearchedAtDesc(user);
    }

    @CacheEvict(value = "searchHistory", key = "#user.id")
    public void clearSearchHistory(User user) {
        List<SearchHistory> userHistory = searchHistoryRepository.findByUserOrderBySearchedAtDesc(user);
        searchHistoryRepository.deleteAll(userHistory);
    }
}