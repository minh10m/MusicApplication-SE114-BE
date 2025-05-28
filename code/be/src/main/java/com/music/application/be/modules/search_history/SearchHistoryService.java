package com.music.application.be.modules.search_history;

import com.music.application.be.modules.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;

    public SearchHistory addSearchHistory(User user, String query) {
        SearchHistory searchHistory = SearchHistory.builder()
                .user(user)
                .query(query)
                .build();
        return searchHistoryRepository.save(searchHistory);
    }

    public List<SearchHistory> getSearchHistoryByUser(User user) {
        return searchHistoryRepository.findByUserOrderBySearchedAtDesc(user);
    }

    public void clearSearchHistory(User user) {
        List<SearchHistory> userHistory = searchHistoryRepository.findByUserOrderBySearchedAtDesc(user);
        searchHistoryRepository.deleteAll(userHistory);
    }
}


