import com.orion.goods.dao.BrandMapper;
import net.minidev.json.JSONUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BrandTest {
    @Autowired
    private BrandMapper brandMapper;
    @Test
    public void test1() {
        System.out.println(brandMapper.selectAll());
    }
}
