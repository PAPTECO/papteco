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

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.papteco.web.beans.ClientRequestBean;
import com.papteco.web.beans.QueueItem;

/**
 * Handler implementation for the object echo client. It initiates the ping-pong
 * traffic between the object echo client and server by sending the first
 * message to the server.
 */
public class OpenFileClientHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = Logger
			.getLogger(OpenFileClientHandler.class.getName());

	private QueueItem openfile;
	private String filepath;
	private String[] fileStructPath;

	/**
	 * Creates a client-side handler.
	 */
	public OpenFileClientHandler(QueueItem openfile, String filepath,
			String[] fileStructPath) {
		this.openfile = openfile;
		this.filepath = filepath;
		this.fileStructPath = fileStructPath;
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// Send the first message if this handler is a client-side handler.
		File file = new File(this.filepath);
		if (file.exists()) {
			ClientRequestBean bean = new ClientRequestBean('P');
			QueueItem qItem = new QueueItem();
			qItem.setParam(this.fileStructPath);
			bean.setqItem(qItem);
			InputStream fis = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			bean.setPrjObj(buffer);
			fis.close();
			ctx.writeAndFlush(bean);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// Echo back the received object to the server.
		ClientRequestBean bean = new ClientRequestBean('O');
		bean.setqItem(openfile);
		ctx.writeAndFlush(bean);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.info("Unexpected exception from downstream.");
		ctx.close();
	}
}
