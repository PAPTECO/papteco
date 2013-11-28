/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.papteco.web.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectEncoder;

import com.papteco.web.beans.QueueItem;

/**
 * Modification of {@link EchoClient} which utilizes Java object serialization.
 */
public class OpenFileClientBuilder extends BasicBuilder implements Runnable{

    private final String host;
    private QueueItem openfile;
    private String filepath;
    private String fileStructPath;

    public OpenFileClientBuilder(String ip) {
    	this.host = ip;
    }
    public OpenFileClientBuilder(String ip, QueueItem openfile, String filepath, String fileStructPath) {
    	this.host = ip;
    	this.openfile = openfile;
    	this.filepath = filepath;
    	this.fileStructPath = fileStructPath;
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(
                            new ObjectEncoder(),
                            new NewObjectDecoder(ClassResolvers.cacheDisabled(null)),
                            new OpenFileClientHandler(openfile, filepath, fileStructPath));
                }
             });

            // Start the connection attempt.
            b.connect(host, PortTranslater(envsetting.getProperty("open_file_port"))).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        // Print usage if no argument is specified.
//        if (args.length < 2 || args.length > 3) {
//            System.err.println(
//                    "Usage: " + ObjectEchoClient.class.getSimpleName() +
//                    " <host> <port> [<first message size>]");
//            return;
//        }

        // Parse options.
//        final String host = "localhost";
//        final int port = 8080;
//        final int firstMessageSize;
//
//        if (args.length == 3) {
//            firstMessageSize = Integer.parseInt(args[2]);
//        } else {
//            firstMessageSize = 256;
//        }
//
//        new SustainableAppConnClientBuilder(host, port, firstMessageSize).run();
    }
}
