# distributedLock
## 简单的分布式锁
基于springboot实现分布式Redis分布式锁，
现只实现了Redis方式实现的可重入的分布式锁(还不完整)

使用方式
```
BaseDistributedLockProperties properties = new RedisDistributedLockProperties();
        properties.setResourceId("your resource id");
        properties.setExpires(Duration.ofSeconds(5));
        properties.setLockTimeout(...);

        DistributedLock lock = distributedLockFactoryManager.get(properties);
```

TODO

1. spring集成方法
2. zookeeper 分布式锁