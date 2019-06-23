package cn.wuyaoyao.exception;

import java.time.Duration;

/**
 *
 * @author 容县人
 * create date 2019-05-24 23:45
 **/
public class LockTimeOutException extends RuntimeException {

    private Duration duration;

    public LockTimeOutException(Duration duration) {
        this.duration = duration;
    }

    @Override
    public String getMessage() {

        return "获取阻塞锁超时失败, 默认超时时间=" + duration.getSeconds() + "秒";
    }
}
