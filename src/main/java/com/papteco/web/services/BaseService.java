package com.papteco.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.papteco.web.utils.FoldersUtils;

@Service
public class BaseService {
	@Autowired
	protected FoldersUtils foldersUtils;

	/* mandatory constructor method */
	public BaseService() {
	}
}
