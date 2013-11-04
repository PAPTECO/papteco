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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.papteco.web.beans.ClientRequestBean;
import com.papteco.web.beans.QueueItem;

/**
 * Handler implementation for the object echo client.  It initiates the
 * ping-pong traffic between the object echo client and server by sending the
 * first message to the server.
 */
public class ReleaseFileClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(
            ReleaseFileClientHandler.class.getName());

    private String filepath;
    private String fileStructPath;
    /**
     * Creates a client-side handler.
     */
    public ReleaseFileClientHandler(String filepath, String fileStructPath) {
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
    	ClientRequestBean bean = new ClientRequestBean('R');
		QueueItem qItem = new QueueItem();
		qItem.setParam(this.fileStructPath);
		bean.setqItem(qItem);
        ctx.writeAndFlush(bean);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Echo back the received object to the server.
    	ClientRequestBean bean = new ClientRequestBean('O');
    	if(bean.getPrjObj() != null){
    		byte[] buffer = (byte[]) bean.getPrjObj();
    		BufferedOutputStream buff = null;
    		buff = new BufferedOutputStream(new FileOutputStream(new File(this.filepath)));
    		buff.write(buffer);
    		buff.flush();
    		buff.close();
    	}
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.flush();
    }

    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.log(
                Level.WARNING,
                "Unexpected exception from downstream.", cause);
        ctx.close();
    }
}
