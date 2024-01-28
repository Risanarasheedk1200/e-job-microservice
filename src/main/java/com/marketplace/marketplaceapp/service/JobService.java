package com.marketplace.marketplaceapp.service;

import java.util.List;
import java.util.Optional;

import com.marketplace.marketplaceapp.models.Job;

public interface JobService {
    List<Job> getAllJobs();
    Job saveJob(Job job);
    List<Job> getRecentlyPublishedJobs();
    List<Job> getTop10JobsByNumBids();
    Optional<Job> getJobById(String id);
    Job updateNumBids(String id, String leastBidAmount, String email, String name);
    void handleExpiredJobs();
    List<Job> getAllActiveJobs() ;
}