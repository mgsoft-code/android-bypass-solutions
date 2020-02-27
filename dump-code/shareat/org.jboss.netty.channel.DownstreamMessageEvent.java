package org.jboss.netty.channel;

import java.net.SocketAddress;
import org.jboss.netty.util.internal.StringUtil;

public class DownstreamMessageEvent implements MessageEvent {
    private final Channel channel;
    private final ChannelFuture future;
    private final Object message;
    private final SocketAddress remoteAddress;

    public DownstreamMessageEvent(Channel channel2, ChannelFuture future2, Object message2, SocketAddress remoteAddress2) {
        if (channel2 == null) {
            throw new NullPointerException("channel");
        } else if (future2 == null) {
            throw new NullPointerException("future");
        } else if (message2 == null) {
            throw new NullPointerException("message");
        } else {
            this.channel = channel2;
            this.future = future2;
            this.message = message2;
            if (remoteAddress2 != null) {
                this.remoteAddress = remoteAddress2;
            } else {
                this.remoteAddress = channel2.getRemoteAddress();
            }
        }
    }

    public Channel getChannel() {
        return this.channel;
    }

    public ChannelFuture getFuture() {
        return this.future;
    }

    public Object getMessage() {
        return this.message;
    }

    public SocketAddress getRemoteAddress() {
        return this.remoteAddress;
    }

    public String toString() {
        if (getRemoteAddress() == getChannel().getRemoteAddress()) {
            return getChannel().toString() + " WRITE: " + StringUtil.stripControlCharacters(getMessage());
        }
        return getChannel().toString() + " WRITE: " + StringUtil.stripControlCharacters(getMessage()) + " to " + getRemoteAddress();
    }
}