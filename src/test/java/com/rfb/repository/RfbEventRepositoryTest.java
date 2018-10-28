package com.rfb.repository;

import com.rfb.RfbloyaltyApp;
import com.rfb.bootstrap.RfbBootstrap;
import com.rfb.domain.RfbEvent;
import com.rfb.domain.RfbLocation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest(classes = {RfbloyaltyApp.class})
public class RfbEventRepositoryTest extends AbstractRepositoryTest {

    //@org.junit.jupiter.api.BeforeEach
    @Before
    public void setUp() {
        RfbBootstrap rfbBootstrap = new RfbBootstrap(rfbLocationRepository, rfbEventRepository, rfbEventAttendanceRepository, rfbUserRepository);
    }

    //@org.junit.jupiter.api.Test
    @Test
    public void findByRfbLocationAndEventDate() {
        RfbLocation aleAndTheWitch = rfbLocationRepository.findByLocationName("St Pete - Ale and the Witch");
        assertNotNull(aleAndTheWitch);

        RfbEvent event = rfbEventRepository.findByRfbLocationAndEventDate(aleAndTheWitch, LocalDate.now());
        assertNotNull(event);
    }
}
