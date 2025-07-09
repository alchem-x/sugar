package sugar;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sugar.support.Data;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestContainer.class)
class SugarSpringTests {

    @Test
    void testReadYaml() {
        var data = SugarSpring.readYaml("data.yaml", Data.class);
        assertNotNull(data);
        assertEquals("hello world", data.value());
    }
}
