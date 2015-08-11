package cn.teaey.apns4j.network;

/**
 * @author xiaofei.wxf
 * @date 2015/8/11
 */
public class ApnsException extends RuntimeException {
    public ApnsException() {
    }

    public ApnsException(String message) {
        super(message);
    }

    public ApnsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApnsException(Throwable cause) {
        super(cause);
    }

    public ApnsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
