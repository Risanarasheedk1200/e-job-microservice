package com.marketplace.marketplaceapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.marketplaceapp.models.Job;
import com.marketplace.marketplaceapp.service.JobService;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    @Autowired
    private JobService jobService;

    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/active")
    public List<Job> getAllActiveJobs() {
        return jobService.getAllActiveJobs();
    }

     @PostMapping
    public Job saveJob(@RequestBody Job job) {
        return jobService.saveJob(job);
    }

    @GetMapping("/recent10")
    public List<Job> getTenJobs() {
        return jobService.getRecentlyPublishedJobs();
    }

    @GetMapping("/top10bybid")
    public List<Job> getTop10JobsByNumBids() {
        return jobService.getTop10JobsByNumBids();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable String id) {
        Optional<Job> job = jobService.getJobById(id);
        return job.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/update-bids")
    public ResponseEntity<Job> updateNumBids(@PathVariable String id, @RequestParam String leastBidAmount, @RequestParam String email,@RequestParam String name) {
        Job updatedJob = jobService.updateNumBids(id, leastBidAmount,email,name);
        return ResponseEntity.ok(updatedJob);
    }
}

