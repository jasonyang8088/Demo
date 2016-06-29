package com.zxxk.zss.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zxxk.zss.service.SplitService;
import com.zxxk.zss.service.split.ChineseSplitServiceImpl;
import com.zxxk.zss.service.split.CommonSplitServiceImpl;
import com.zxxk.zss.service.split.EnglishSplitServiceImpl;

@Component
public class SplitFactory {

	@Autowired
	private ChineseSplitServiceImpl chineseSplitServiceImpl;
	
	@Autowired
	private EnglishSplitServiceImpl englishSplitServiceImpl;
	
//	@Autowired
//	private HistorySplitServiceImpl historySplitServiceImpl;
//	
//	@Autowired
//	private BiologySplitServiceImpl biologySplitServiceImpl;
//	
//	@Autowired
//	private ChemistrySplitServiceImpl chemistrySplitServiceImpl;
//	
//	@Autowired
//	private MathSplitServiceImpl mathSplitServiceImpl;
	
	@Autowired
	private CommonSplitServiceImpl commonSplitServiceImpl;

	private SplitService splitService;

	public SplitService createSplitService(String s) {
		switch (s) {
		case "语文":
			splitService = chineseSplitServiceImpl;
			break;
		case "英语":
			splitService = englishSplitServiceImpl;
			break;
		default:
			splitService = commonSplitServiceImpl;
		}
		return splitService;
	}

}
