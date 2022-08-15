import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.junit.Test;


public class TestId {


    @Test
    public void genId(){
        Snowflake snowflake = IdUtil.createSnowflake(24, 24);
//        IdSequence idSequence = new SecondsIdSequenceFactory().create(4356);
        for (int i = 0; i < 10000; i++) {
            System.out.println(snowflake.nextId());
        }
    }
}
