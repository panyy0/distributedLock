package cn.wuyaoyao;

import cn.wuyaoyao.exception.LockTimeOutException;

/**
 * 分布式锁接口，所有的锁实现都要继承该接口
 *
 * @author 容县人
 * create date 2019-05-24 23:43
 **/
public interface DistributedLock {

    /**
     * 阻塞锁
     * @throws LockTimeOutException
     *          超时异常
     */
    boolean lock() throws LockTimeOutException;

    /**
     * 获取非阻塞锁
     * @return
     *      true 成功
     *      false 失败
     */
    boolean tryLock();

    /**
     * 解锁
     * @return
     *      true 成功
     *      false失败
     */
    boolean releaseLock() throws InterruptedException;
}
