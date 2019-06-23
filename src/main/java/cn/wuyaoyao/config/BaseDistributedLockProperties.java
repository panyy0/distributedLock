package cn.wuyaoyao.config;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

/**
 * 所有配置类的基类，定义一些默认的配置信息可扩展
 *
 * @author 容县人
 * create date 2019-05-24 23:53
 **/
public abstract class BaseDistributedLockProperties {

    private static final String DEFAULT_PROCESS_ID =
            LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() + ":" + UUID.randomUUID().toString();

    /**
     * 需要锁住的资源ID
     */
    protected String getResourceId() {
        return "";
    }

    /**
     * 超时时间
     */
    protected Duration getLockTimeout() {

        return Duration.ZERO;
    }

    /**
     * 默认的processId，这个可以不重写
     */
    protected String getProcessId() {

        return DEFAULT_PROCESS_ID;
    }

}
