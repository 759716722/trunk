import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.math.BigDecimal;

/**
 * Created by wyb on 2018/1/19.
 */
public class TestPassword {

    public BigDecimal chang(Object o){
        if (o == null) {
            return BigDecimal.ZERO;
        } else if (o instanceof BigDecimal) {
            return (BigDecimal) o;
        }
        String str = o.toString();
        if (StringUtils.isNotBlank(str)) {
            return new BigDecimal(str);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public static void main(String[] args) {
        String password = new SimpleHash("md5", "123456",null,4).toString();
        System.out.println(password);

        Object o = "";

        String a = "12345";
        System.out.println(System.getProperty("java.version"));
        System.out.println(Long.getLong("java.version"));

        Byte qq = null;
    }
}
