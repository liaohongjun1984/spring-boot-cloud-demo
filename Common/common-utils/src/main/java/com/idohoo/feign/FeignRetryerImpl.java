package com.idohoo.feign;

import feign.RetryableException;
import feign.Retryer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by henser on 17-6-5.
 */
public class FeignRetryerImpl implements Retryer{

    private static Logger logger =LoggerFactory.getLogger(FeignRetryerImpl.class);

    private final int maxAttempts;
    private final long period;
    private final long maxPeriod;
    int attempt;
    long sleptForMillis;

    private static FeignRetryerImpl feignRetryer = null;

    private FeignRetryerImpl() {
        this(100, SECONDS.toMillis(1), 3);
    }

    private FeignRetryerImpl(long period, long maxPeriod, int maxAttempts) {
        this.period = period;
        this.maxPeriod = maxPeriod;
        this.maxAttempts = maxAttempts;
        this.attempt = 1;
    }

    public static FeignRetryerImpl getInstance(long period, long maxPeriod, int maxAttempts) {

        if(feignRetryer == null) {
            synchronized (FeignRetryerImpl.class) {
                if(feignRetryer == null) {
                    feignRetryer = new FeignRetryerImpl(period, maxPeriod,maxAttempts);
                }
            }
        }
        return feignRetryer;
    }

    // visible for testing;
    protected long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public void continueOrPropagate(RetryableException e) {

        if (attempt++ >= maxAttempts) {
            throw e;
        }

        long interval;
        if (e.retryAfter() != null) {
            logger.info("请求重试后的时间 retryAfter :{} ，当前时间 currentTimeMillis :{}",e.retryAfter().getTime(),currentTimeMillis());
            interval = e.retryAfter().getTime() - currentTimeMillis();
            if (interval > maxPeriod) {
                interval = maxPeriod;
            }
            if (interval < 0) {
                return;
            }
        } else {
            interval = nextMaxInterval();
        }
        logger.info("请求重试次数 attempt :{} ,间隔时间差 interval :{}",attempt,interval);
        try {
            Thread.sleep(interval);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        sleptForMillis += interval;
    }

    /**
     * Calculates the time interval to a retry attempt. <br> The interval increases exponentially
     * with each attempt, at a rate of nextInterval *= 1.5 (where 1.5 is the backoff factor), to the
     * maximum interval.
     *
     * @return time in nanoseconds from now until the next attempt.
     */
    long nextMaxInterval() {
        long interval = (long) (period * Math.pow(1.5, attempt - 1));
        return interval > maxPeriod ? maxPeriod : interval;
    }

    @Override
    public Retryer clone() {
        return new Default(period, maxPeriod, maxAttempts);
    }

    public static void main(String[] args) {
        double a = Math.pow(2, 2);
        System.out.println("a:" +a);
    }
}
