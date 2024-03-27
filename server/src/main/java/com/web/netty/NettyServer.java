package com.web.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.InetSocketAddress;

@Component
@Slf4j
public class NettyServer {


    @PostConstruct
    public void start() throws Exception {
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);

        log.info("-----------------------iot server start----------------------");
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(boss, work)
                .channel(NioServerSocketChannel.class)
                //.option(ChannelOption.SO_BACKLOG, 1024)
                .localAddress(new InetSocketAddress(4563))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(LOGGING_HANDLER);
                        ch.pipeline().addLast(new StringDecoder());
                        ByteBuf delimiter = Unpooled.copiedBuffer ("}".getBytes ());
                        ch.pipeline ().addLast (new DelimiterBasedFrameDecoder(2048, delimiter));

                        ch.pipeline().addLast(new EchoServerHandler());
                    }
                });
        ChannelFuture channelFuture = b.bind().sync();
        if (channelFuture.isSuccess()) {
            log.info("-----------------------iot server run----------------------");
        }
    }


}