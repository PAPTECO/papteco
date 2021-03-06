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

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Modification of {@link EchoServer} which utilizes Java object serialization.
 */
public class NettyAppServerBuilder extends BasicBuilder implements Runnable {

	private String rootpath;

	public NettyAppServerBuilder(String rootpath) {
		this.rootpath = rootpath;
	}

	public void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline().addLast(
									new ObjectEncoder(),
									new NewObjectDecoder(ClassResolvers
											.cacheDisabled(null)),
									new NettyAppServerHandler(rootpath));
						}
					});

			// Bind and start to accept incoming connections.
			b.bind(PortTranslater(envsetting.getProperty("comm_nett_port")))
					.sync().channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	// public static void main(String[] args) throws Exception {
	// int port;
	// if (args.length > 0) {
	// port = Integer.parseInt(args[0]);
	// } else {
	// port = 8080;
	// }
	// new ObjectEchoServer(port).run();
	// }
}
