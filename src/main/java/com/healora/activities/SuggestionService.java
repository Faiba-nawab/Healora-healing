package com.healora.activities;

import java.util.*;

public class SuggestionService {

    private final HealingActivityDAO activityDAO = new HealingActivityDAO();
    private final QuoteDAO quoteDAO = new QuoteDAO();
    private final MoodStatsProvider statsProvider;

    public SuggestionService(MoodStatsProvider statsProvider) {
        this.statsProvider = statsProvider;
    }

    public List<HealingActivity> getSuggestionsForUser(int userId, MoodType fallback, int limit) {
        MoodType target = fallback;
        Map<MoodType,Integer> counts = statsProvider != null ? statsProvider.getRecentMoodCounts(userId, 7) : null;
        if (counts != null && !counts.isEmpty()) {
            target = counts.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(fallback);
        }
        List<HealingActivity> pool = activityDAO.findByMood(target, limit);

        // always attach a quote if available
        String q = quoteDAO.randomQuote(target);
        if (q != null) {
            HealingActivity quote = new HealingActivity();
            quote.id = -1;
            quote.title = "A note for you";
            quote.type = ActivityType.QUOTE;
            quote.moodTag = target;
            quote.difficulty = 1;
            quote.durationSec = 30;
            quote.content = q;
            quote.active = true;
            pool.add(0, quote);
        }

        // if list is smaller, try fetch random to fill
        if (pool.size() < limit) {
            HealingActivity extra = activityDAO.findById(pool.isEmpty() ? 1 : pool.get(0).id);
            if (extra != null) pool.add(extra);
        }

        return pool.size() > limit ? pool.subList(0, limit) : pool;
    }
}

