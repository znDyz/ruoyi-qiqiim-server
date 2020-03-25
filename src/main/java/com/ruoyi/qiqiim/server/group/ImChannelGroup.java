/**
 ***************************************************************************************
 *  @Author     1044053532@qq.com   
 *  @License    http://www.apache.org/licenses/LICENSE-2.0
 ***************************************************************************************
 */
package com.ruoyi.qiqiim.server.group;

import com.ruoyi.qiqiim.server.model.proto.MessageProto;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
/**
 * Im广播组
 * 用于推送全员信息及系统消息 或断开所有用户连接
 *
 */
public class ImChannelGroup {
	 
    private static final ChannelGroup CHANNELGROUP = new DefaultChannelGroup("ChannelGroup", GlobalEventExecutor.INSTANCE);
     
    public static void add(Channel channel) {
        CHANNELGROUP.add(channel);
    }
    public static void remove(Channel channel) {
        CHANNELGROUP.remove(channel);
    }
  
    /**
     * 广播
     */
    public static ChannelGroupFuture broadcast(Object msg) {
        MessageProto.Model message = ( MessageProto.Model)msg;
        return CHANNELGROUP.writeAndFlush(message);
    }
    /**
     * 广播
     */
    public static ChannelGroupFuture broadcast(Object msg, ChannelMatcher matcher) {
        MessageProto.Model message = ( MessageProto.Model)msg;
        return CHANNELGROUP.writeAndFlush(message, matcher);
    }
    /**
     * 刷新
     */
    public static ChannelGroup flush() {
        return CHANNELGROUP.flush();
    }
    /**
     * 丢弃无用连接
     */
    public static boolean discard(Channel channel) {
        return CHANNELGROUP.remove(channel);
    }
    /**
     * 断开所有连接
     */
    public static ChannelGroupFuture disconnect() {
        return CHANNELGROUP.disconnect();
    }
    /**
     * 断开指定连接
     */
    public static ChannelGroupFuture disconnect(ChannelMatcher matcher) {
        return CHANNELGROUP.disconnect(matcher);
    }
    /**
     * 判断广播组中是否包含指定session
     */
    public static boolean isExist(Channel channel) {
        return CHANNELGROUP.contains(channel);
    }
    /**
     * 获取广播组中的成员数
     */
    public static int size() {
        return CHANNELGROUP.size();
    }
}