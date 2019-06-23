package cn.wuyaoyao.manager;

import cn.wuyaoyao.DistributedLock;
import cn.wuyaoyao.DistributedLockFactory;
import cn.wuyaoyao.DistributedLockFactoryManager;
import cn.wuyaoyao.config.BaseDistributedLockProperties;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分布式锁管理类，所有的分布式锁获取入口都从这个类中统一进行
 * @author 容县人
 * create date 2019-05-25 15:23
 **/
public class DefaultDistributedLockFactoryManager implements DistributedLockFactoryManager {


    private Map<Class<? extends BaseDistributedLockProperties>, DistributedLockFactory<BaseDistributedLockProperties>> factoryMap;

    public DefaultDistributedLockFactoryManager(List<DistributedLockFactory<BaseDistributedLockProperties>> distributedLockFactoryList) {
        factoryMap = initFactoryMap(distributedLockFactoryList);
    }

    private Map<Class<? extends BaseDistributedLockProperties>, DistributedLockFactory<BaseDistributedLockProperties>> initFactoryMap(
            List<DistributedLockFactory<BaseDistributedLockProperties>> distributedLockFactoryList
    ) {

        if (distributedLockFactoryList == null || distributedLockFactoryList.isEmpty()) {
            throw new IllegalArgumentException("请配置对应的分布式锁工厂");
        }

        Set<DistributedLockFactory<BaseDistributedLockProperties>> set =
                new HashSet<>(distributedLockFactoryList);
        //对应的配置类只能对应一种分布式锁实现，否则会导致产生的锁id不唯一
        if (set.size() < distributedLockFactoryList.size()) {
            throw new IllegalArgumentException("supported 类型重复");
        }

        return distributedLockFactoryList.stream().collect(Collectors.toMap(
                DistributedLockFactory::getSupportedType,
                a -> a,
                (m, n) -> m
        ));
    }

    @Override
    public DistributedLock get(BaseDistributedLockProperties properties) {

        return factoryMap.get(properties.getClass()).get(properties);
    }
}
