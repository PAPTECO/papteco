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

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.papteco.web.beans.UsersBean;
import com.papteco.web.beans.UsersFormBean;
import com.papteco.web.dbs.UserDAO;

/**
 * Handles both client-side and server-side handler depending on which
 * constructor was called.
 */
public class LoginServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(
            LoginServerHandler.class.getName());

    public LoginServerHandler(){
    }
    @Override
    public void channelRead(
            ChannelHandlerContext ctx, Object msg) throws Exception {
        // Echo back the received object to the client.
    	UsersFormBean request = (UsersFormBean) msg;
    	UsersBean user = UserDAO.getUser(request.getCreateUserName());
		if(user == null)
			ctx.write("NOUSER");
		else if(user.getPassword().equals(request.getCreatePassword()))
			ctx.write("SUCC");
		else
			ctx.write("PWDINC");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.log(
                Level.WARNING,
                "Unexpected exception from downstream.", cause);
        ctx.close();
    }
    
    protected String combineFolderPath(String path1, String path2) {
		File f = new File(path1, path2);
		if (!f.exists()) {
			f.mkdirs();
			f.setExecutable(true, false);
			f.setReadable(true, false);
			f.setWritable(true, false);
		}
		return f.getPath();
	}
}
