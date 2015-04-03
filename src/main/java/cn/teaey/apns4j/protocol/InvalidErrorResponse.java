package cn.teaey.apns4j.protocol;
/**
 * User: Teaey
 * Date: 13-8-31
 *
 * @author xiaofei.wxf
 * @version $Id: $Id
 */
public class InvalidErrorResponse extends RuntimeException
{
    /**
     * <p>Constructor for InvalidErrorResponse.</p>
     *
     * @param msg a {@link String} object.
     */
    public InvalidErrorResponse(String msg){
        super(msg);
    }
}
