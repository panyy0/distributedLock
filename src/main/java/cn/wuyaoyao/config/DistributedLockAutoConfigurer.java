package cn.wuyaoyao.config;

import cn.wuyaoyao.DistributedLockFactory;
import cn.wuyaoyao.DistributedLockFactoryManager;
import cn.wuyaoyao.manager.DefaultDistributedLockFactoryManager;
import cn.wuyaoyao.redis.RedisDistributedLockFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * spring starter 配置入口类
 *
 * @author 容县人
 * create date 2019-05-25 15:00
 **/
@Configuration
@Import(value = {StringRedisTemplate.class, RedisDistributedLockFactory.class})
public class DistributedLockAutoConfigurer {


    @Bean
    @ConditionalOnMissingBean(DistributedLockFactoryManager.class)
    @ConditionalOnBean(DistributedLockFactory.class)
    public DistributedLockFactoryManager getDistributedLock(
            List<DistributedLockFactory<BaseDistributedLockProperties>> distributedLockFactoryList
    ) {
        return new DefaultDistributedLockFactoryManager(
                distributedLockFactoryList
        );
    }


}
