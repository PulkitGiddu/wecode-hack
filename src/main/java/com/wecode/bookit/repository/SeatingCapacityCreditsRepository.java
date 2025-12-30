package com.wecode.bookit.repository;

import com.wecode.bookit.entity.SeatingCapacityCredits;
import com.wecode.bookit.dto.SeatingCapacityCreditsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatingCapacityCreditsRepository extends JpaRepository<SeatingCapacityCredits, SeatingCapacityCreditsId> {

    @Query("SELECT s FROM SeatingCapacityCredits s WHERE :capacity BETWEEN s.minCapacity AND s.maxCapacity")
    Optional<SeatingCapacityCredits> findByCapacityRange(@Param("capacity") Integer capacity);
}

