package com.gizwits.test;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class TCPClient {

    public static void main(String[] args) {
        //创建客户端发送消息
        IoConnector connector = new NioSocketConnector();
        //设置超时时间
        connector.setConnectTimeoutMillis(30000);
        //设置过滤器
        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(
                        new TextLineCodecFactory(
                                Charset.forName("UTF-8"),
                                LineDelimiter.MAC.getValue(),
                                LineDelimiter.MAC.getValue()
                        )
                )
        );
        //注册IoHandler：
        connector.setHandler(new ClientHandler("你好！\r\n 大家好！"));
        //绑定端口
        int port = 9123;
        ConnectFuture connect = connector.connect(new InetSocketAddress("localhost", port));

        connect.awaitUninterruptibly();//  等待我们的链接

        connect.getSession().getCloseFuture().awaitUninterruptibly();// 等待关闭

       // connector.dispose();// 关闭客户端

    }
}

class ClientHandler extends IoHandlerAdapter {
    private final static Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

    private final String values;

    public ClientHandler(String values) {
        this.values = values;
    }

    @Override
    public void sessionOpened(IoSession session) {

        session.write(values);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {

        //  客户端 接收数据
        LOGGER.info("客户端接收数据 The message received is [" + message.toString() + "]");


    }


}