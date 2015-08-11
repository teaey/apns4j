package cn.teaey.apns4j.protocol;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
 */
public abstract class Payload
{
    private final        Map<String, Object> root       = new HashMap<String, Object>();
    private static final AtomicInteger       IDENTIFIER = new AtomicInteger(Integer.MIN_VALUE);
    private static final int                 DEF_EXPIRY = (int) TimeUnit.DAYS.toSeconds(1);
    private final        int                 identifier = IDENTIFIER.incrementAndGet();
    /**
     *
     */
    private              int                 expiry     = ((int) System.currentTimeMillis() / 1000) + DEF_EXPIRY;
    /**
     * <p>addDictionary.</p>
     *
     * @param key a {@link String} object.
     * @param value a {@link Object} object.
     * @return a {@link Object} object.
     */
    protected Object addDictionary(String key, Object value)
    {
        return root.put(key, value);
    }
    /**
     * <p>removeDictionary.</p>
     *
     * @param key a {@link String} object.
     * @return a {@link Object} object.
     */
    public Object removeDictionary(String key)
    {
        return root.remove(key);
    }
    /**
     * <p>toJsonString.</p>
     *
     * @return a {@link String} object.
     */
    public String toJsonString()
    {
        return JsonParser.toJsonString(root);
    }
    /**
     * <p>toJsonBytes.</p>
     *
     * @return an array of byte.
     */
    public byte[] toJsonBytes()
    {
        return toJsonString().getBytes(Protocal.DEF_CHARSET);
    }
    /**
     * <p>Getter for the field <code>identifier</code>.</p>
     *
     * @return a int.
     */
    public int getIdentifier()
    {
        return identifier;
    }
    /**
     * <p>Getter for the field <code>expiry</code>.</p>
     *
     * @return a int.
     */
    public int getExpiry()
    {
        return expiry;
    }
    /**
     * <p>Setter for the field <code>expiry</code>.</p>
     *
     * @param expiry a int.
     */
    public void setExpiry(int expiry)
    {
        this.expiry = expiry;
    }
}
