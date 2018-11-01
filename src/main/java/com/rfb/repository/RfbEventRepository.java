package com.rfb.repository;

import com.rfb.domain.RfbEvent;
import com.rfb.domain.RfbLocation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;


/**
 * Spring Data  repository for the RfbEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RfbEventRepository extends JpaRepository<RfbEvent, Long> {

    Optional<RfbEvent> findByRfbLocationAndEventDate(RfbLocation location, LocalDate now);
}
