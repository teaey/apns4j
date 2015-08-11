package cn.teaey.apns4j.protocol;

import java.nio.ByteBuffer;

/**
 * @author teaey
 * @date 13-8-31
 * @since 1.0.0
 */
public class ErrorResponse {
    private final byte command;
    private final Status status;
    private final int identifier;
    /**
     * <p>Constructor for ErrorResponse.</p>
     *
     * @param data an array of byte.
     */
    public ErrorResponse(byte[] data) {
        if (null == data || data.length != 6) {
            throw new InvalidErrorResponse("");
        }
        ByteBuffer wrapper = ByteBuffer.wrap(data);
        this.command = wrapper.get();
        this.status = Status.resolove(wrapper.get());
        this.identifier = wrapper.getInt();
    }

    /**
     * <p>Constructor for ErrorResponse.</p>
     *
     * @param command    a byte.
     * @param status     a byte.
     * @param identifier a int.
     */
    public ErrorResponse(byte command, byte status, int identifier) {
        this.command = command;
        this.status = Status.resolove(status);
        this.identifier = identifier;
    }

    /**
     * <p>Getter for the field <code>command</code>.</p>
     *
     * @return a byte.
     */
    public byte getCommand() {
        return command;
    }

    /**
     * <p>Getter for the field <code>status</code>.</p>
     *
     * @return
     */
    public Status getStatus() {
        return status;
    }

    /**
     * <p>Getter for the field <code>identifier</code>.</p>
     *
     * @return a int.
     */
    public int getIdentifier() {
        return identifier;
    }

    public enum Status {
        OK(0, "No errors encountered"),
        ERROR(Integer.MAX_VALUE, "Unknown"),
        ERROR1(1, "Processing error"),
        ERROR2(2, "Missing device token"),
        ERROR3(3, "Missing topic"),
        ERROR4(4, "Missing payload"),
        ERROR5(5, "Invalid token size"),
        ERROR6(6, "Invalid topic size"),
        ERROR7(7, "Invalid payload size"),
        ERROR8(8, "Invalid token"),
        ERROR10(10, "Shutdown"),
        ERROR255(255, "None (unknown)");
        int code;
        String desc;

        Status(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static Status resolove(int code) {
            for (Status each : Status.values()) {
                if (each.code == code) {
                    return each;
                }
            }
            return Status.ERROR;
        }
    }
}
