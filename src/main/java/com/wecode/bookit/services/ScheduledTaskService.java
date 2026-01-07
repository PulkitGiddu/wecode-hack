package com.wecode.bookit.services;

import com.wecode.bookit.entity.Booking;
import com.wecode.bookit.entity.ManagerCreditSummary;
import com.wecode.bookit.repository.BookingRepository;
import com.wecode.bookit.repository.ManagerCreditSummaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Scheduled Tasks Service
 * 1. Marking completed meetings
 * 2. Applying penalties for no-shows
 * 3. Resetting manager credits
 */
@Service
public class ScheduledTaskService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskService.class);
    private static final int PENALTY_AMOUNT = 50;

    private final BookingRepository bookingRepository;
    private final ManagerCreditSummaryRepository managerCreditSummaryRepository;

    public ScheduledTaskService(BookingRepository bookingRepository,
                                ManagerCreditSummaryRepository managerCreditSummaryRepository) {
        this.bookingRepository = bookingRepository;
        this.managerCreditSummaryRepository = managerCreditSummaryRepository;
    }

    /**
     * Runs daily at 12:01 AM to mark completed meetings and apply penalty
     */
    @Scheduled(cron = "0 1 0 * * *") // Daily at 12:01 AM
    @Transactional
    public void markCompletedMeetingsAndApplyPenalties() {
        logger.info("Starting scheduled task: Mark completed meetings and apply penalties");

        try {
            LocalDate today = LocalDate.now();
            LocalDate yesterday = today.minusDays(1);

            List<Booking> pastBookings = bookingRepository.findByStatusAndMeetingDateBefore("ACTIVE", yesterday);

            for (Booking booking : pastBookings) {
                booking.setStatus("COMPLETED");
                if ("PENDING".equals(booking.getCheckInStatus())) {
                    booking.setCheckInStatus("NO_SHOW");
                    booking.setPenaltyApplied(true);
                    booking.setPenaltyAmount(PENALTY_AMOUNT);

                    logger.info("Applying penalty to booking: {} | Manager: {} | Penalty: {}",
                               booking.getBookingId(), booking.getUserId(), PENALTY_AMOUNT);
                    deductPenaltyFromManager(booking.getUserId(), PENALTY_AMOUNT);
                }

                bookingRepository.save(booking);
            }

            logger.info("Completed marking {} meetings as completed/no-show", pastBookings.size());

        } catch (Exception e) {
            logger.error("Error in marking completed meetings and applying penalties", e);
        }
    }

    /**
     * CRON JOB to reset manager credits every Monday at 6:00 AM
     */
    @Scheduled(cron = "0 0 6 ? * MON")
    @Transactional
    public void resetManagerCreditsWeekly() {
        logger.info("Starting scheduled task: Weekly credit reset at Monday 6:00 AM");

        try {
            List<ManagerCreditSummary> allManagers = managerCreditSummaryRepository.findAll();

            for (ManagerCreditSummary manager : allManagers) {
                logger.info("Resetting credits for manager: {} | Previous credits: {}",
                           manager.getManagerName(), manager.getTotalCredits());

                manager.setTotalCredits(2000);
                manager.setCreditsUsed(0);
                manager.setPenalty(0);
                manager.setLastResetAt(LocalDateTime.now());

                managerCreditSummaryRepository.save(manager);

                logger.info("Reset complete for manager: {} | New credits: 2000",
                           manager.getManagerName());
            }

            logger.info("Completed credit reset for {} managers", allManagers.size());

        } catch (Exception e) {
            logger.error("Error in weekly credit reset", e);
        }
    }

    /**
     * Deduct penalty amount from manager's total credits
     * Updates the manager_credit_summary table
     *
     * @param managerId UUID of the manager
     * @param penaltyAmount Amount to deduct
     */
    private void deductPenaltyFromManager(UUID managerId, int penaltyAmount) {
        try {
            ManagerCreditSummary manager = managerCreditSummaryRepository.findById(managerId)
                    .orElseThrow(() -> new RuntimeException("Manager not found: " + managerId));

            int newTotalCredits = manager.getTotalCredits() - penaltyAmount;
            manager.setTotalCredits(Math.max(newTotalCredits, 0)); // Ensure it doesn't go below 0

            manager.setPenalty(manager.getPenalty() + penaltyAmount);

            managerCreditSummaryRepository.save(manager);

            logger.info("Deducted {} penalty from manager: {} | New total credits: {}",
                       penaltyAmount, managerId, manager.getTotalCredits());

        } catch (Exception e) {
            logger.error("Error deducting penalty from manager: {}", managerId, e);
        }
    }

    /**
     * Mark specific booking as completed (manually)
     */
    @Transactional
    public void markBookingAsCompleted(UUID bookingId) {
        try {
            Booking booking = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));

            booking.setStatus("COMPLETED");

            if ("PENDING".equals(booking.getCheckInStatus())) {
                booking.setCheckInStatus("NO_SHOW");
                booking.setPenaltyApplied(true);
                booking.setPenaltyAmount(PENALTY_AMOUNT);
                deductPenaltyFromManager(booking.getUserId(), PENALTY_AMOUNT);
            }

            bookingRepository.save(booking);
            logger.info("Marked booking {} as completed", bookingId);

        } catch (Exception e) {
            logger.error("Error marking booking as completed", e);
        }
    }
}

