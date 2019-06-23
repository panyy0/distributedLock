package cn.wuyaoyao;

import cn.wuyaoyao.config.BaseDistributedLockProperties;

/**
 *
 * @author 容县人
 * create date 2019-05-24 23:38
 **/
public interface DistributedLockFactory<T extends BaseDistributedLockProperties> {

    /**
     * 获取设置的分布式锁工厂
     * @param <T>
     *         对应的配置类 配置类与工厂类一对一
     * @return
     *      设置的配置类
     */
    Class<T> getSupportedType();

    /**
     * 获取一个分布式锁
     * @param properties
     *      锁配置
     * @return 返回一个具体的分布式锁
     */
    DistributedLock get(T properties);
}
