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

/**
 * Modification of {@link EchoClient} which utilizes Java object serialization.
 */
public class ReleaseFileClientBuilder implements Runnable{

    private final String host;
    private final int port = 8083;
    private String filepath;
    private String fileStructPath;
    private String fileid;
    private String taskid;

    public ReleaseFileClientBuilder(String ip) {
    	this.host = ip;
    }
    public ReleaseFileClientBuilder(String ip, String filepath, String fileStructPath, String fileid, String taskid) {
    	this.host = ip;
    	this.filepath = filepath;
    	this.fileStructPath = fileStructPath;
    	this.fileid = fileid;
    	this.taskid = taskid;
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
                            new ReleaseFileClientHandler(filepath, fileStructPath, fileid, taskid));
                }
             });

            // Start the connection attempt.
            b.connect(host, port).sync().channel().closeFuture().sync();
//            System.out.println(b.connect(host, port).sync().channel().closeFuture().sync().isSuccess());
//            if(b.connect(host, port).sync().channel().closeFuture().sync().isSuccess()){
//            	FileLockDAO.deleteFileLockBean(fileid);
//            }else{
//            	System.out.println("Connection fail! Client is not running!");
//            }
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
