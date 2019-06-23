package cn.wuyaoyao.redis;

import cn.wuyaoyao.config.BaseDistributedLockProperties;

import java.time.Duration;

/**
 *
 * @author 容县人
 * create date 2019-05-25 00:08
 **/
public class RedisDistributedLockProperties extends BaseDistributedLockProperties {

    private String resourceId;
    /**
     * 锁的过期时间，也就是被锁资源的过期时间
     */
    private Duration expires;

    private Duration lockTimeout;

    @Override
    public String getResourceId() {
        return resourceId;
    }

    @Override
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public Duration getExpires() {
        return expires;
    }

    @Override
    public void setExpires(Duration expires) {
        this.expires = expires;
    }

    @Override
    protected Duration getLockTimeout() {
        return lockTimeout == null ? super.getLockTimeout() : this.lockTimeout;
    }

    @Override
    public void setLockTimeout(Duration lockTimeout) {
        this.lockTimeout = lockTimeout;
    }

    @Override
    protected String getProcessId() {
        return super.getProcessId();
    }
}
