package testHash;

import cn.hutool.core.util.HashUtil;
import org.junit.Test;

public class HashToInt {

    @Test
    public void testHashUtil(){
        long l = HashUtil.mixHash("192.168.0.100");
        System.out.println(l);
    }
}
