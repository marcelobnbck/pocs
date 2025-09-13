package org.example.music;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RockBandApplicationTests {

    @Test
    void contextLoads() {
        // This test will pass if the Spring context loads successfully
    }

    @Test
    void mainMethodRuns() {
        RockBandApplication.main(new String[] {});
    }
}
