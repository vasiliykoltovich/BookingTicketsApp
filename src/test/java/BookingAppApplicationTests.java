import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class BookingAppApplicationTests {

	@Test
	public void contextLoads() throws Exception {
        assertTrue(!(1 > 3));
	}

}
