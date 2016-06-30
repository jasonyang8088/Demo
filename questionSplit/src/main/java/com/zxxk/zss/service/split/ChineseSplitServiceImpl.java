package com.zxxk.zss.service.split;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxxk.zss.dao.QuestionDAO;
import com.zxxk.zss.entity.Question;
import com.zxxk.zss.service.SplitService;
import com.zxxk.zss.utils.StringUtil;

@Service
public class ChineseSplitServiceImpl implements SplitService {

	private Logger logger = LoggerFactory.getLogger(ChineseSplitServiceImpl.class);
	
	private Question question;
	
	@Autowired
	private QuestionDAO questionDao;

	@Override
	public List<Question> splitQuestion(List<String> lstContent,Question q) throws InstantiationException, IllegalAccessException {
		logger.info("---拆分大题中的小大题，例如（一）---");
		question = q;
		List<Question> lstQuestion = new ArrayList<Question>();
		List<TypeAndIndex> lstTI = new ArrayList<TypeAndIndex>();
		for (int i = 0; i < lstContent.size(); i++) {
			String s = lstContent.get(i);
			s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
			if (s.contains("现代文阅读")) {
				TypeAndIndex ti = new TypeAndIndex();
				ti.setType("现代文阅读");
				ti.setIndex(i);
				lstTI.add(ti);
			}
			if (s.contains("古诗文阅读")||s.contains("古代诗文阅读")) {
				TypeAndIndex ti = new TypeAndIndex();
				ti.setType("古诗文阅读");
				ti.setIndex(i);
				lstTI.add(ti);
			}
			if (s.contains("文学类文本阅读")) {
				TypeAndIndex ti = new TypeAndIndex();
				ti.setType("文学类文本阅读");
				ti.setIndex(i);
				lstTI.add(ti);
			}
			if (s.contains("实用类文本阅读")) {
				TypeAndIndex ti = new TypeAndIndex();
				ti.setType("实用类文本阅读");
				ti.setIndex(i);
				lstTI.add(ti);
			}
			if (s.contains("语言文字运用")) {
				TypeAndIndex ti = new TypeAndIndex();
				ti.setType("语言文字运用");
				ti.setIndex(i);
				lstTI.add(ti);
			}
			if (s.contains("写作")) {
				TypeAndIndex ti = new TypeAndIndex();
				ti.setType("写作");
				ti.setIndex(i);
				lstTI.add(ti);
			}
			if (s.contains("答案开始")) {
				TypeAndIndex ti = new TypeAndIndex();
				ti.setType("答案开始");
				ti.setIndex(i);
				lstTI.add(ti);
			}
		}
		Collections.sort(lstTI);
		if (lstTI.size() > 1) {
			for (int i = 0; i < lstTI.size(); i++) {
				String type = lstTI.get(i).getType();
				List<String> lstContent1 = new ArrayList<String>();
				if (i < lstTI.size() - 1) {
					lstContent1 = lstContent.subList(lstTI.get(i).getIndex(), lstTI.get(i + 1).getIndex());
				} else if (i == lstTI.size() - 1) {
					lstContent1 = lstContent.subList(lstTI.get(i).getIndex(), lstContent.size());
				}
				if (type.equals("现代文阅读")) {
					lstQuestion.addAll(this.handleDaTi(lstContent1,"现代文阅读"));
				}
				if (type.equals("古诗文阅读")) {
					lstQuestion.addAll(this.handleDaTi(lstContent1,"古诗文阅读"));
				}
				if (type.equals("文学类文本阅读")) {
					lstQuestion.addAll(this.handleDaTi(lstContent1,"文学类文本阅读"));
				}
				if (type.equals("实用类文本阅读")) {
					lstQuestion.addAll(this.handleDaTi(lstContent1,"实用类文本阅读"));
				}
				if (type.equals("语言文字运用")) {
					lstQuestion.addAll(this.handleDaTi(lstContent1,"语言文字运用"));
				}
				if (type.equals("写作")) {
					lstQuestion.addAll(this.handleDaTi(lstContent1,"写作"));
				}
				if (type.equals("答案开始")) {
					break;
				}
			}
		}
		return lstQuestion;
	}

	public List<Question> handleXianDaiWenYueDu(List<String> lstContent) throws InstantiationException, IllegalAccessException {
		logger.info("---拆分现代文阅读---");
		List<Question> lstQuestion = new ArrayList<Question>();
		String regex = "^(\t| )*(\\(|（)[一二三四五六七八九].";
		Pattern p = Pattern.compile(regex);
		List<Integer> indexs = new ArrayList<Integer>();
		for (int i = 1; i < lstContent.size(); i++) {
			String s = lstContent.get(i);
			s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
			Matcher m = p.matcher(s);
			while (m.find()) {
				indexs.add(i);
			}
		}
		if (indexs.size() == 0) {
			lstQuestion = this.handleXiaoTi(lstContent.subList(1, lstContent.size()), "现代文阅读");
		} else if (indexs.size() > 0) {
			for (int i = 0; i < indexs.size(); i++) {
				List<String> lstContent1 = new ArrayList<String>();
				if (i < indexs.size() - 1) {
					lstContent1 = lstContent.subList(indexs.get(i), indexs.get(i + 1));
				} else if (i == indexs.size() - 1) {
					lstContent1 = lstContent.subList(indexs.get(i), lstContent.size());
				}
				List<Question> list = new ArrayList<Question>();
				list = this.handleXiaoTi(lstContent1, "古诗文阅读");
				if (list != null) {
					lstQuestion.addAll(list);
				}
			}
		}
		return lstQuestion;
	}
	
	

	public List<Question> handleGuShiWenYueDu(List<String> lstContent) throws InstantiationException, IllegalAccessException {
		logger.info("---拆分古诗文阅读---");
		List<Question> lstQuestion = new ArrayList<Question>();
		String regex = "^(\t| )*(\\(|（)[一二三四五六七八九].";
		Pattern p = Pattern.compile(regex);
		List<Integer> indexs = new ArrayList<Integer>();
		for (int i = 1; i < lstContent.size(); i++) {
			String s = lstContent.get(i);
			s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
			Matcher m = p.matcher(s);
			while (m.find()) {
				indexs.add(i);
			}
		}
		if (indexs.size() == 0) {
			lstQuestion = this.handleXiaoTi(lstContent.subList(1, lstContent.size()), "古诗文阅读");
		} else if (indexs.size() > 0) {
			for (int i = 0; i < indexs.size(); i++) {
				List<String> lstContent1 = new ArrayList<String>();
				if (i < indexs.size() - 1) {
					lstContent1 = lstContent.subList(indexs.get(i), indexs.get(i + 1));
				} else if (i == indexs.size() - 1) {
					lstContent1 = lstContent.subList(indexs.get(i), lstContent.size());
				}
				List<Question> list = new ArrayList<Question>();
				list = this.handleXiaoTi(lstContent1, "古诗文阅读");
				if (list != null) {
					lstQuestion.addAll(list);
				}
			}
		}
		return lstQuestion;
	}
	
	public List<Question> handleDaTi(List<String> lstContent,String type) throws InstantiationException, IllegalAccessException {
		logger.info("---拆分大题，类似（一）---");
		List<Question> lstQuestion = new ArrayList<Question>();
		String regex = "^(\t| )*(\\(|（)[一二三四五六七八九].";
		Pattern p = Pattern.compile(regex);
		List<Integer> indexs = new ArrayList<Integer>();
		for (int i = 1; i < lstContent.size(); i++) {
			String s = lstContent.get(i);
			s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
			Matcher m = p.matcher(s);
			while (m.find()) {
				indexs.add(i);
			}
		}
		if (indexs.size() == 0) {
			lstQuestion = this.handleXiaoTi(lstContent.subList(1, lstContent.size()), type);
		} else if (indexs.size() > 0) {
			for (int i = 0; i < indexs.size(); i++) {
				List<String> lstContent1 = new ArrayList<String>();
				if (i < indexs.size() - 1) {
					lstContent1 = lstContent.subList(indexs.get(i), indexs.get(i + 1));
				} else if (i == indexs.size() - 1) {
					lstContent1 = lstContent.subList(indexs.get(i), lstContent.size());
				}
				List<Question> list = new ArrayList<Question>();
				list = this.handleXiaoTi(lstContent1, type);
				if (list != null) {
					lstQuestion.addAll(list);
				}
			}
		}
		return lstQuestion;
	}

	public List<Question> handleXiaoTi(List<String> lstContent, String type) throws InstantiationException, IllegalAccessException {
		logger.info("---拆分小题---");
		List<Question> lstQuestion = new ArrayList<Question>();
		String regex = "^(\t| )*[0-9]{1,2}[．.、,][\\s\\S]*";
		Pattern p = Pattern.compile(regex);
		List<Integer> indexs = new ArrayList<Integer>();
		for (int i = 0; i < lstContent.size(); i++) {
			String s = lstContent.get(i);
			s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
			Matcher m = p.matcher(s);
			while (m.find()) {
				indexs.add(i);
			}
		}
		if (indexs.size() != 0 && indexs.get(0) > 3) {
			Question q =  question.getClass().newInstance();
			q.setType("材料类型");
			q.setStem(StringUtil.listToString(lstContent.subList(1, indexs.get(0))));
			questionDao.save(q);
			lstQuestion.add(q);
			// 处理除最后一道小题的小题
			for (int i = 0; i < indexs.size(); i++) {
				Question q1 =question.getClass().newInstance();
				if (i < indexs.size() - 1) {
					q1 = this.splitOption(lstContent.subList(indexs.get(i), indexs.get(i + 1)),type);
				} else if (i == indexs.size() - 1) {
					q1 = this.splitOption(lstContent.subList(indexs.get(i), lstContent.size()),type);
				}
				q1.setType(type);
				q1.setPid(q.getQid());
				questionDao.save(q1);
				lstQuestion.add(q1);
			}
		}  else {
			for (int i = 0; i < indexs.size(); i++) {
				List<Question> list = new ArrayList<Question>();
				if (i < indexs.size() - 1) {
					list=this.handleXiaoXiaoTi(lstContent.subList(indexs.get(i), indexs.get(i + 1)),type);
				} else if (i == indexs.size() - 1) {
					list=this.handleXiaoXiaoTi(lstContent.subList(indexs.get(i), lstContent.size()),type);
				}
				if(list!=null){
					lstQuestion.addAll(list);
				}
			}
		}
		return lstQuestion;
	}
	
	public List<Question> handleXiaoXiaoTi(List<String> lstContent,String type) throws InstantiationException, IllegalAccessException{
		logger.info("---拆分小题中的小题---");
		List<Question> lstQuestion = new ArrayList<Question>();
		String regex = "^(\t| )*(\\(|（)[1-9](\\)|）)";
		Pattern p = Pattern.compile(regex);
		List<Integer> indexs = new ArrayList<Integer>();
		for (int i = 0; i < lstContent.size(); i++) {
			String s = lstContent.get(i);
			s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
			Matcher m = p.matcher(s);
			while (m.find()) {
				indexs.add(i);
			}
		}
		if (indexs.size() != 0 && indexs.get(0) > 3) {
			Question q = question.getClass().newInstance();
			q.setType("材料类型");
			q.setStem(StringUtil.listToString(lstContent.subList(1, indexs.get(0))));
			questionDao.save(q);
			lstQuestion.add(q);
			// 处理除最后一道小题的小题
			for (int i = 0; i < indexs.size(); i++) {
				Question q1 = question.getClass().newInstance();
				if (i < indexs.size() - 1) {
					q1 = this.splitOption(lstContent.subList(indexs.get(i), indexs.get(i + 1)),type);
				} else if (i == indexs.size() - 1) {
					q1 = this.splitOption(lstContent.subList(indexs.get(i), lstContent.size()),type);
				}
				q1.setPid(q.getQid());
				q1.setType(type);
				questionDao.save(q1);
				lstQuestion.add(q1);
			}
		}  else {
			Question q1 = question.getClass().newInstance();
			q1 = this.splitOption(lstContent,type);
			questionDao.save(q1);
			lstQuestion.add(q1);
		}
		return lstQuestion;
	}

	public Question splitOption(List<String> lstContent,String type) throws InstantiationException, IllegalAccessException {
		logger.info("---拆分小题选项---");
		String regex = "^(\t| )*A[.．]";
		Pattern p = Pattern.compile(regex);
		List<Integer> indexs = new ArrayList<Integer>();
		for (int i = 0; i < lstContent.size(); i++) {
			String s = lstContent.get(i);
			s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
			Matcher m = p.matcher(s);
			while (m.find()) {
				indexs.add(i);
			}
		}
		Question q =question.getClass().newInstance();
		if (indexs.size() > 2) {
			logger.debug("--拆分选项错误--拆出：" + indexs.size());
		} else if (indexs.size() == 1) {
			q.setTihao(this.splitTihao(lstContent.get(0)));
			q.setType(type);
			q.setStem(StringUtil.listToString(lstContent.subList(0, indexs.get(0))));
			q.setOptions(StringUtil.listToString(lstContent.subList(indexs.get(0), lstContent.size())));
		} else if (indexs.size() == 0) {
			q.setTihao(this.splitTihao(lstContent.get(0)));
			q.setType(type);
			q.setStem(StringUtil.listToString(lstContent.subList(0, lstContent.size())));
		}
		return q;
	}

	public String splitTihao(String s) {
		s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
		String regex = "^(\t| )*([0-9]{1,2})[．.、,][\\s\\S]*";
		System.out.println(s);
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		while (m.find()) {
			return m.group(2);
		}
		return null;
	}

}
