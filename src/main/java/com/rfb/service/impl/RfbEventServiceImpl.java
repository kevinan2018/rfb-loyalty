package com.rfb.service.impl;

import com.rfb.domain.RfbLocation;
import com.rfb.repository.RfbLocationRepository;
import com.rfb.service.RfbEventService;
import com.rfb.domain.RfbEvent;
import com.rfb.repository.RfbEventRepository;
import com.rfb.service.dto.RfbEventDTO;
import com.rfb.service.mapper.RfbEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Service Implementation for managing RfbEvent.
 */
@Service
@Transactional
public class RfbEventServiceImpl implements RfbEventService {

    private final Logger log = LoggerFactory.getLogger(RfbEventServiceImpl.class);

    private RfbEventRepository rfbEventRepository;

    private RfbEventMapper rfbEventMapper;

    private final RfbLocationRepository rfbLocationRepository;

    public RfbEventServiceImpl(RfbEventRepository rfbEventRepository, RfbEventMapper rfbEventMapper,
                               RfbLocationRepository rfbLocationRepository) {
        this.rfbEventRepository = rfbEventRepository;
        this.rfbEventMapper = rfbEventMapper;
        this.rfbLocationRepository = rfbLocationRepository;
    }

    /**
     * Save a rfbEvent.
     *
     * @param rfbEventDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RfbEventDTO save(RfbEventDTO rfbEventDTO) {
        log.debug("Request to save RfbEvent : {}", rfbEventDTO);

        RfbEvent rfbEvent = rfbEventMapper.toEntity(rfbEventDTO);
        rfbEvent = rfbEventRepository.save(rfbEvent);
        return rfbEventMapper.toDto(rfbEvent);
    }

    /**
     * Get all the rfbEvents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RfbEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RfbEvents");
        return rfbEventRepository.findAll(pageable)
            .map(rfbEventMapper::toDto);
    }


    /**
     * Get one rfbEvent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RfbEventDTO> findOne(Long id) {
        log.debug("Request to get RfbEvent : {}", id);
        return rfbEventRepository.findById(id)
            .map(rfbEventMapper::toDto);
    }

    /**
     * Delete the rfbEvent by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RfbEvent : {}", id);
        rfbEventRepository.deleteById(id);
    }

    /**
     * Get the rfbevent by today and location
     * @param locationId the location id
     * @return the entity
     */
    @Override
    public Optional<RfbEventDTO> findByTodayAndLocation(Long locationId) {
        log.debug("Request to get RfbEvent by location: {}", locationId);
        Optional<RfbLocation> location = rfbLocationRepository.findById(locationId);
        if (location.isPresent()) {
            return rfbEventRepository.findByRfbLocationAndEventDate(location.get(), LocalDate.now())
                .map(rfbEventMapper::toDto);
        } else {
            return Optional.empty();
        }
    }
}
