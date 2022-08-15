package testencrypt;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import com.yice.edu.cn.common.pojo.Constant;
import org.junit.Test;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

public class EncryptTest {
    @Test
    public void testSha(){
        System.out.println(DigestUtil.sha1Hex(DigestUtil.md5Hex("doaoicifeinsoc2588")));
    }

    public void testSelf(){
        String content = "1234567890321456897";

        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        AES aes = SecureUtil.aes(key);
        // 加密为16进制表示
        String encryptHex = aes.encryptHex(content);
        System.out.println(encryptHex);
// 解密为字符串
        String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println(decryptStr);
    }
    @Test
    public void testSelf1(){
        Random random = new Random();
        List<Character> result = new ArrayList<>();
        while(result.size()<26){
            char c = (char) (Math.random() * 26 + 'a');
            if(!result.contains(c)){
                if(result.size()<5){
                    result.add(c);
                }else{
                    result.add((int)Math.floor(Math.random()*(result.size()-1)),c);
                }

            }
        }
        while(result.size()<52){
            char c = (char) (Math.random() * 26 + 'A');
            if(!result.contains(c)){
                if(result.size()<5){
                    result.add(c);
                }else{
                    result.add((int)Math.floor(Math.random()*(result.size()-1)),c);
                }
            }
        }
        while(result.size()<62){
            char c = (char) (Math.random() * 10+'0');
            if(!result.contains(c)){
                result.add((int)Math.floor(Math.random()*(result.size()-1)),c);
            }
        }
        for (Character character : result) {
            System.out.print("'"+character+"',");
        }

    }
}
