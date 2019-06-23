package cn.wuyaoyao.redis;

import cn.wuyaoyao.DistributedLock;
import cn.wuyaoyao.DistributedLockFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author 容县人
 * create date 2019-05-25 23:24
 **/
@Component
@ConditionalOnBean(value = {StringRedisTemplate.class})
public class RedisDistributedLockFactory implements DistributedLockFactory<RedisDistributedLockProperties> {

    private StringRedisTemplate stringRedisTemplate;

    public RedisDistributedLockFactory(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Class<RedisDistributedLockProperties> getSupportedType() {

        return RedisDistributedLockProperties.class;
    }

    @Override
    public DistributedLock get(RedisDistributedLockProperties properties) {

        return new RedisDistributedLock(stringRedisTemplate, properties);
    }
}
