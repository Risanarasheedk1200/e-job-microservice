package com.marketplace.marketplaceapp.repository;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketplace.marketplaceapp.models.Job;

@Repository
public interface JobRepository extends MongoRepository<Job, String> {
       // Find top 10 active jobs ordered by createdAt in descending order
       List<Job> findTop10ByStatusOrderByCreatedAtDesc(String status, Pageable pageable);

       // Find top 10 active jobs ordered by numBids in descending order
       List<Job> findTop10ByStatusOrderByNumBidsDesc(String status);
   
       // Find jobs that have expired before the specified date and have a specific status
       List<Job> findByExpireAtBeforeAndStatus(LocalDateTime expireDate, String status);


       List<Job> findByStatus(String string);

       @Query("{'status': 'Active'}")
       List<Job> findActiveJobs();
   

}


