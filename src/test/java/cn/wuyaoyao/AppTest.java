package cn.wuyaoyao;

import cn.wuyaoyao.config.BaseDistributedLockProperties;
import cn.wuyaoyao.redis.RedisDistributedLockProperties;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * Unit test for simple App.
 */
public class AppTest  {

    @Resource
    private DistributedLockFactoryManager distributedLockFactoryManager;

    public void test() {
        BaseDistributedLockProperties properties = new RedisDistributedLockProperties();
        properties.setResourceId("your resource id");
        properties.setExpires(Duration.ofSeconds(5));
        properties.setLockTimeout(Duration.ZERO);

        DistributedLock lock = distributedLockFactoryManager.get(properties);
    }
}
