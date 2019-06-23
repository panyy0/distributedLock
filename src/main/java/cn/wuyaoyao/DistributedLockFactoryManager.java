package cn.wuyaoyao;

import cn.wuyaoyao.config.BaseDistributedLockProperties;

/**
 * 分布式锁工厂管理类，所有注册的分布式锁都统一从这个入口类去拿
 * @author 容县人
 * create date 2019-05-24 23:35
 **/
public interface DistributedLockFactoryManager {

    /**
     * 根据配置获取指定的分布式锁
     * @param properties
     *      分布式锁配置类
     * @return
     *      获取指定的分布式锁
     */
    DistributedLock get(BaseDistributedLockProperties properties);

}
