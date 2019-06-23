package cn.wuyaoyao.redis;

import cn.wuyaoyao.DistributedLock;
import cn.wuyaoyao.exception.LockTimeOutException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;

/**
 * @author 容县人
 * create date 2019-05-25 23:13
 **/
public class RedisDistributedLock implements DistributedLock {

    private StringRedisTemplate stringRedisTemplate;

    private RedisDistributedLockProperties properties;

    private static final Long DEFAULT_SLEEP_TIME = 100L;

    public RedisDistributedLock(StringRedisTemplate stringRedisTemplate, RedisDistributedLockProperties properties) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.properties = properties;
    }

    /**
     * 锁定脚本
     */
    private static final String LOCK_SCRIPT = "if(redis.call('exists',KEYS[1])==0)then redis.call('hset',KEYS[1],ARGV[2],1);" +
            "redis.call('pexpire',KEYS[1],ARGV[1]);return nil;end;" +
            "if(redis.call('hexists',KEYS[1],ARGV[2])==1)then redis.call('hincrby',KEYS[1],ARGV[2],1);" +
            "redis.call('pexpire',KEYS[1],ARGV[1]);return nil;end;return redis.call('pttl',KEYS[1]);";
    /**
     * 解锁脚本
     */
    private static final String UNLOCK_SCRIPT = "if(redis.call('exists',KEYS[1])==0)then return 1;end;" +
            "if(redis.call('hexists',KEYS[1],ARGV[2])==0)then return nil;end;" +
            "local c=redis.call('hincrby',KEYS[1],ARGV[2],-1);" +
            "if(c>0)then redis.call('pexpire',KEYS[1],ARGV[1]);return 0;end;" +
            "redis.call('del',KEYS[1]);return 1;";

    /**
     * <T> The script result type. Should be one of Long, Boolean, List, or deserialized value type. Can be null if
     * the script returns a throw-away status (i.e "OK")
     * 一般情况下定义返回一个Long
     */
    private static final RedisScript<Long> LOCK_EXECUTOR = new DefaultRedisScript<>(LOCK_SCRIPT, Long.class);

    private static final RedisScript<Long> UNLOCK_EXECUTOR = new DefaultRedisScript<>(UNLOCK_SCRIPT, Long.class);

    @Override
    public boolean lock() throws LockTimeOutException {
        Long timeout = properties.getLockTimeout().toMillis();

        while (timeout > 0) {

            boolean result = tryLock();
            if (result) {
                return true;
            }
            // 逐步减少休眠时间
            timeout -= DEFAULT_SLEEP_TIME;


            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                throw new LockTimeOutException(properties.getLockTimeout());
            }

        }

        return false;
    }

    @Override
    public boolean tryLock() {

        Long result = stringRedisTemplate.execute(
                LOCK_EXECUTOR,
                Collections.singletonList(properties.getResourceId()),
                getDefaultValue(),
                properties.getExpires().toMillis()
        );

        return result == null;
    }

    @Override
    public boolean releaseLock() throws InterruptedException {

        Long result = stringRedisTemplate.execute(
                UNLOCK_EXECUTOR,
                Collections.singletonList(properties.getResourceId()),
                getDefaultValue()
        );

        //当前线程未持有锁
        if (result == null) {
            throw new InterruptedException("解锁失败，当前线程ID=" + Thread.currentThread().getId());
        }

        return true;
    }


    private String getDefaultValue() {
        return properties.getProcessId() + ":" + Thread.currentThread().getId();
    }
}
