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
import java.util.logging.Level;
import java.util.logging.Logger;

import com.papteco.web.beans.ClientRequestBean;
import com.papteco.web.beans.QueueItem;

/**
 * Handles both client-side and server-side handler depending on which
 * constructor was called.
 */
public class PushFileToClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(
            PushFileToClientHandler.class.getName());

    private final String filepath;
    private String fileStructPath;
    
    public PushFileToClientHandler(String filepath, String fileStructPath){
    	this.filepath = filepath;
    	this.fileStructPath = fileStructPath;
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // Send the first message if this handler is a client-side handler.
    	File file = new File(this.filepath);
		if(file.exists()){
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
    public void channelRead(
            ChannelHandlerContext ctx, Object msg) throws Exception {
    	System.out.println("PushFile Feedback.");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
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
