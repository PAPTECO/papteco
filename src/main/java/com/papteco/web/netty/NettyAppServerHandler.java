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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.papteco.web.beans.ClientRequestBean;
import com.papteco.web.beans.FolderBean;
import com.papteco.web.beans.IPItem;
import com.papteco.web.beans.ProjectBean;
import com.papteco.web.beans.QueueItem;
import com.papteco.web.beans.UsersBean;
import com.papteco.web.dbs.ProjectCacheDAO;
import com.papteco.web.dbs.UserDAO;
import com.papteco.web.dbs.UserIPDAO;
import com.papteco.web.utils.FilesUtils;
import com.papteco.web.utils.Roles2RightsConfiguration;

/**
 * Handles both client-side and server-side handler depending on which
 * constructor was called.
 */
public class NettyAppServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(
            NettyAppServerHandler.class.getName());
    private Properties rolessetting = Roles2RightsConfiguration.getRolesSetting();

    private final String rootpath;
    public NettyAppServerHandler(String rootpath){
    	this.rootpath = rootpath;
    }
    @Override
    public void channelRead(
            ChannelHandlerContext ctx, Object msg) throws Exception {
        // Echo back the received object to the client.
    	ClientRequestBean request = (ClientRequestBean) msg;
    	switch (request.getActionType())
    	{
    	case 'I':
    		IPItem ipItem = request.getIpItem();
    		UserIPDAO.saveUserIPBean(ipItem);
    		logger.info(ipItem.getPCID()+":"+ipItem.getPCIP()+":"+ipItem.getPCNAME());
    		ctx.write(request);
    		break;
    	case 'P':
    		String reqUser = request.getReqUser();
    		UsersBean user = UserDAO.getUser(reqUser);
    		if(user == null){
    			request.setReqUser(null);
    			ctx.write(request);
    			break;
    		}
    		
    		Set<String> tempAllowFunctions = new HashSet<String>();
			for(String role : user.getRoles()){
				List<String> rights = Arrays.asList(rolessetting.get(role).toString().split(","));
				tempAllowFunctions.addAll(rights);
			}
			List<String> allowDocTypes = new ArrayList<String>();
			for(String allowDocType : tempAllowFunctions){
				if(allowDocType.startsWith("D") && allowDocType.endsWith("V")){
					String tmp = allowDocType.substring(1, allowDocType.length()-1);
					char[] tmpchar = tmp.toCharArray();
					for(char c : tmpchar){
						allowDocTypes.add(String.valueOf(c));
					}
				}
			}
			
    		String prjCde = request.getPrjCde();
    		ProjectBean project = ProjectCacheDAO.getProjectBeanByFilterProjectCde(prjCde);
    		if(project == null){
    			ctx.write(request);
    			break;
    		}
    		
    		List<FolderBean> folders = project.getFolderTree();
    		List<FolderBean> resultFolders = new ArrayList<FolderBean>();
    		for(FolderBean folder : folders){
    			if(allowDocTypes.contains(folder.getDocType())){
    				resultFolders.add(folder);
    			}
    		}
    		project.setFolderTree(resultFolders);
    		
    		request.setPrjObj(project);
    		ctx.write(request);
    		break;
    	case 'F':
    		QueueItem qItem = (QueueItem) request.getqItem();
//    		File file = new File(combineFolderPath(this.rootpath,qItem.getPrjCde()), qItem.getParam());//simple change
    		File file = new File(FilesUtils.combine(this.rootpath,qItem.getPrjCde(), FilesUtils.combine(qItem.getParam())));
    		logger.log(
                    Level.INFO,">>Downloading File:" + file.getName() + "on Server side!.");
    		if(file.exists()){
    			InputStream fis = new BufferedInputStream(new FileInputStream(file));
    			byte[] buffer = new byte[fis.available()];
    	        fis.read(buffer);
    	        fis.close();
    	        request.setPrjObj(buffer);
    	        ctx.write(request);
    		}else{
    			logger.log(
                        Level.WARNING,">>File not existing:" + file.getName() + "on Server side!.");
    		}
    		break;
    	case 'G':
    		String username = request.getReqUser();
    		
    		List<String> mails = FilesUtils.scanFilesFromFolder(combineFolderPath(rootpath,combineFolderPath("MAILS_BACKUP",username)));
    		
    		request.setAdditional6(mails);
    		ctx.write(request);
    		break;
    	case 'D':
    		String usrname = request.getReqUser();
    		String mailfile = request.getAdditional1();
    		File f = new File(combineFolderPath(rootpath,combineFolderPath("MAILS_BACKUP",combineFolderPath(usrname,mailfile))));
    		if(f.exists()){
    			InputStream fis = new BufferedInputStream(new FileInputStream(f));
    			byte[] buffer = new byte[fis.available()];
    	        fis.read(buffer);
    	        fis.close();
    	        request.setPrjObj(buffer);
    		}
    		ctx.write(request);
    		break;
    	}
//    	ctx.close();
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
