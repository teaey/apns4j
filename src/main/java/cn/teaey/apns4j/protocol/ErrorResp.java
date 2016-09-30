/*
 *
 *  * Copyright 2015 The Apns4j Project
 *  *
 *  * The Netty Project licenses this file to you under the Apache License,
 *  * version 2.0 (the "License"); you may not use this file except in compliance
 *  * with the License. You may obtain a copy of the License at:
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  * License for the specific language governing permissions and limitations
 *  * under the License.
 *
 */

package cn.teaey.apns4j.protocol;

import java.nio.ByteBuffer;

/**
 * @author teaey(xiaofei.wxf)
 * @since 1.0.3
 */
public class ErrorResp {

    private final byte command;
    private final Status status;
    private final int identifier;

    /**
     * <p>Constructor for ErrorResp.</p>
     *
     * @param data an array of byte.
     */
    public ErrorResp(byte[] data) {
        if (null == data || data.length != 6) {
            throw new InvalidErrorResponse("");
        }
        ByteBuffer wrapper = ByteBuffer.wrap(data);
        this.command = wrapper.get();
        this.status = Status.resolove(wrapper.get());
        this.identifier = wrapper.getInt();
    }

    /**
     * <p>Constructor for ErrorResp.</p>
     *
     * @param command    a byte.
     * @param status     a byte.
     * @param identifier a int.
     */
    public ErrorResp(byte command, byte status, int identifier) {
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

    @Override
    public String toString() {
        return "ErrorResp{" +
                "command=" + command +
                ", status=" + status +
                ", identifier=" + identifier +
                '}';
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
