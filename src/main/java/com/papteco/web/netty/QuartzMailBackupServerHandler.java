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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import com.papteco.web.beans.ClientRequestBean;
import com.papteco.web.beans.UsersBean;
import com.papteco.web.dbs.UserDAO;

/**
 * Handles both client-side and server-side handler depending on which
 * constructor was called.
 */
public class QuartzMailBackupServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(
            QuartzMailBackupServerHandler.class.getName());

    private String mailbackup_dir;
    private UsersBean user;
    
    public QuartzMailBackupServerHandler(String rootpath){
    	this.mailbackup_dir = new File(rootpath, "MAILS_BACKUP").getPath();
    }
    @Override
    public void channelRead(
            ChannelHandlerContext ctx, Object msg) throws Exception {
        // Echo back the received object to the client.
    	ClientRequestBean bean = (ClientRequestBean) msg;
    	switch (bean.getActionType())
    	{
    	case 'M':
    		user = UserDAO.getUser(bean.getReqUser());
    		if(StringUtils.isNotBlank(user.getMailFileTimeStamp()))
    			bean.setTimestamp(user.getMailFileTimeStamp());
    		ctx.write(bean);
    		break;
    	case 'U':
    		if(bean.getPrjObj() != null){
        		byte[] buffer = (byte[]) bean.getPrjObj();
        		BufferedOutputStream buff = null;
        		String storingPath = new File(mailbackup_dir, bean.getReqUser()).getPath();
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");

        		File mailbackupfile = new File(storingPath,sdf.format(new Date()) + bean.getMailfileSuffix());
        		if(!mailbackupfile.exists()){
        			mailbackupfile.getParentFile().mkdirs();
        			mailbackupfile.createNewFile();
        		}
        		buff = new BufferedOutputStream(new FileOutputStream(mailbackupfile));
        		buff.write(buffer);
        		buff.flush();
        		buff.close();
        		user.setMailFileTimeStamp(bean.getTimestamp());
        		UserDAO.saveUser(user);
        	}
        	ctx.close();
    		break;
    	}
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
    
}
