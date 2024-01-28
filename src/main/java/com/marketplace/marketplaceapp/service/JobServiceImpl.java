
package com.marketplace.marketplaceapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.marketplace.marketplaceapp.exception.JobNotFoundException;
import com.marketplace.marketplaceapp.models.Job;
import com.marketplace.marketplaceapp.repository.JobRepository;
import com.marketplace.marketplaceapp.repository.UserRepository;

@Service
public class JobServiceImpl implements JobService {
    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    private EmailService emailService;

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
    
    @Override
    public List<Job> getAllActiveJobs() {
        return jobRepository.findActiveJobs();
    }
    //I can use a factory pattern for job as well here if the creation of job involves some complex logic
    @Override
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
     public List<Job> getRecentlyPublishedJobs() {
        // Fetch the most recently published ten items
        
        //List<Job> recentlyPublishedJobs = jobRepository.findTop10ByStatusOrderByCreatedAtDesc("Active", PageRequest.of(0, 10));

        //logger.info("Recently Published Jobs: {}", recentlyPublishedJobs);

        return jobRepository.findTop10ByStatusOrderByCreatedAtDesc("Active",PageRequest.of(0, 10));
    }

    @Override
    public List<Job> getTop10JobsByNumBids() {
        return jobRepository.findTop10ByStatusOrderByNumBidsDesc("Active");
    }

    @Override
    public Optional<Job> getJobById(String id) {
        return jobRepository.findById(id);
    }

    @Override
    public Job updateNumBids(String id, String leastBidAmount,String email,String name) {
        Optional<Job> optionalJob = jobRepository.findById(id);

        if (optionalJob.isPresent()) {
            Job existingJob = optionalJob.get();
            existingJob.setNumBids(existingJob.getNumBids()+1);
            existingJob.setLeastBidAmount(leastBidAmount);
            existingJob.setJobBidderContactInfo(email);
            existingJob.setJobBidderName(name);
            return jobRepository.save(existingJob);
        } else {
            throw new JobNotFoundException("Job not found with ID: " + id);
        }
    }

    @Override
    public void handleExpiredJobs() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<Job> expiredJobs = jobRepository.findByExpireAtBeforeAndStatus(currentDateTime, "Active");

        // Handle each expired job, e.g., mark as expired 
        for (Job expiredJob : expiredJobs) {
            expiredJob.setStatus("Expired");
            jobRepository.save(expiredJob);
            //send mail to the final bidder and poster
            sendExpiredJobEmails(expiredJob);
        }
    }

    
    private void sendExpiredJobEmails(Job expiredJob) {
        // Subject and content for the email to the job poster
        if(expiredJob.getNumBids()!=0){
        String subject = "Final Bid for Your Job";
        String content = "Hi "+expiredJob.getJobPosterName()+" Your job  "+expiredJob.getTitle()+ " has received a final bid. The winner is: " +expiredJob.getJobBidderName()+",contact him at " +expiredJob.getJobBidderContactInfo() +
                         " with a bid amount of: " + expiredJob.getLeastBidAmount();
    
        // Send email to job poster (jobContactInfo)
        emailService.sendEmail(expiredJob.getJobContactInfo(), subject, content);
        //find if the job poster is present in user repository
        String userId = userRepository.findByEmail(expiredJob.getJobContactInfo()).get().getId();

        //add notification for the user
        notificationService.addNotification(userId, content);

        // If there's a bidder, send an email to the bidder (jobBidderContactInfo)
        if (expiredJob.getJobBidderContactInfo() != null && !expiredJob.getJobBidderContactInfo().isEmpty()) {
            // Subject and content for the email to the bidder
            String bidderSubject = "Congratulations! " +expiredJob.getJobBidderName()+" You've Won the Bid";
            String bidderContent = "Congratulations! You've won the bid for the job."+expiredJob.getTitle()+" The bid amount is: " + 
                                   expiredJob.getLeastBidAmount();

            //send mail to the final bidder
            emailService.sendEmail(expiredJob.getJobBidderContactInfo(), bidderSubject, bidderContent);
            
            //find if the bidder is present in user repository
            String bidderId = userRepository.findByEmail(expiredJob.getJobBidderContactInfo()).get().getId();

            //add notification for the user
            notificationService.addNotification(bidderId, bidderContent);
        }
    }else {
        //if there was no bid at all
        String subject = "We are sorry,there was no Bid for Your Job";
        String content = "Hi "+expiredJob.getJobPosterName()+" Your job "+expiredJob.getTitle()+ "has not received any bid. We are sorry!";
    
        // Send email to job poster (jobContactInfo)
        emailService.sendEmail(expiredJob.getJobContactInfo(), subject, content);
        String userId = userRepository.findByEmail(expiredJob.getJobContactInfo()).get().getId();
        //add nofication for the user
        notificationService.addNotification(userId, content);

    }

    }

    
}



