import com.jwei.sys.dao.SysUserMapper;
import com.jwei.sys.entity.SysUser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jwei on 2018/1/31.
 */
public class TestApplicationContext {

    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        try{
            SysUserMapper mapper=context.getBean(SysUserMapper.class);//或者是Mapper mapper=(Mapper) context.getBean("mapper");

            SysUser m=mapper.selectByPrimaryKey(Long.parseLong("1"));
            System.out.print(m.getCreateId());
        }catch (Exception e){
            System.out.print(e);
        }
    }
}
