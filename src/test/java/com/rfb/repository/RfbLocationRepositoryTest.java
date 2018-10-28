package com.rfb.repository;

//import static org.junit.jupiter.api.Assertions.*;

import com.rfb.RfbloyaltyApp;
import com.rfb.bootstrap.RfbBootstrap;
import com.rfb.domain.RfbLocation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.DayOfWeek;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RfbloyaltyApp.class})
public class RfbLocationRepositoryTest extends AbstractRepositoryTest {

    //@org.junit.jupiter.api.BeforeEach
    @Before
    public void setUp() {
        RfbBootstrap rfbBootstrap = new RfbBootstrap(rfbLocationRepository, rfbEventRepository,
            rfbEventAttendanceRepository, rfbUserRepository);
    }

    //@org.junit.jupiter.api.Test
    @Test
    public void findAllByRunDayOfWeek() {
        List<RfbLocation> mondayLocations = rfbLocationRepository.findAllByRunDayOfWeek(DayOfWeek.MONDAY.getValue());
        List<RfbLocation> tuesdayLocations = rfbLocationRepository.findAllByRunDayOfWeek(DayOfWeek.TUESDAY.getValue());
        List<RfbLocation> wednesdayLocations = rfbLocationRepository.findAllByRunDayOfWeek(DayOfWeek.WEDNESDAY.getValue());

        assertEquals(1, mondayLocations.size());
        assertEquals(1, tuesdayLocations.size());
        assertEquals(1, wednesdayLocations.size());
    }

    //@org.junit.jupiter.api.Test
    @Test
    public void findByLocationName() {
        RfbLocation location = rfbLocationRepository.findByLocationName("St Pete - Ale and the Witch");
        assertNotNull(location);
        assertEquals(location.getLocationName(), "St Pete - Ale and the Witch");
    }
}
