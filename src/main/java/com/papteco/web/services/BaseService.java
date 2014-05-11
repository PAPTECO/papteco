package com.papteco.web.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.papteco.web.utils.FilesUtils;
import com.papteco.web.utils.FoldersUtils;

@Service
public class BaseService {

	protected static final Logger logger = Logger.getLogger(BaseService.class
			.getName());
	@Autowired
	protected FoldersUtils foldersUtils;

	@Autowired
	protected FilesUtils filesUtils;

	/* mandatory constructor method */
	public BaseService() {
	}
}
