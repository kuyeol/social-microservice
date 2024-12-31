public class UserConversionException extends RuntimeException {

    public UserConversionException(String message) {

        super(message);
    }


    public UserConversionException(String message, Throwable cause) {

        super(message, cause);
    }


    public UserConversionException(Throwable cause) {

        super(cause);
    }


    // 필요한 경우 추가
    protected UserConversionException(String message,
                                      Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }


}