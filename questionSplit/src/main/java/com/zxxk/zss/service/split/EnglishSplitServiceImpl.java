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
import org.springframework.transaction.annotation.Transactional;

import com.zxxk.zss.dao.QuestionDAO;
import com.zxxk.zss.entity.Question;
import com.zxxk.zss.service.SplitService;
import com.zxxk.zss.utils.StringUtil;

@Service
public class EnglishSplitServiceImpl implements SplitService {

	private Logger logger = LoggerFactory.getLogger(EnglishSplitServiceImpl.class);

	@Autowired
	private QuestionDAO questionDao;

	@Override
	public List<Question> splitQuestion(List<String> lstContent) {
		// List<Question> lstQuestion = new ArrayList<Question>();
		// List<Question> lstQuestion1=this.handleYueDu(lstContent);
		// List<Question> lstQuestion2=this.handleJiChu(lstContent);
		// List<Question> lstQuestion3=this.handleXieZuo(lstContent);
		// if(lstQuestion1!=null)lstQuestion.addAll(lstQuestion1);
		// if(lstQuestion2!=null)lstQuestion.addAll(lstQuestion2);
		// if(lstQuestion3!=null)lstQuestion.addAll(lstQuestion3);
		// return lstQuestion;
		logger.info("---拆分大题---");
		List<Question> lstQuestion = new ArrayList<Question>();
		List<TypeAndIndex> lstTI = new ArrayList<TypeAndIndex>();
		for (int i = 0; i < lstContent.size(); i++) {
			String s = lstContent.get(i);
			s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
			String regex = "^(\t| )*(第[一二三四五六七八九]部分|【)?[：:]?[ ]*(阅读理解|英语知识运用|写作|答案开始).*";
			if (s.matches(regex)) {
				if (s.contains("阅读理解")) {
					TypeAndIndex ti = new TypeAndIndex();
					ti.setType("阅读理解");
					ti.setIndex(i);
					lstTI.add(ti);
				}
				if (s.contains("英语知识运用")) {
					TypeAndIndex ti = new TypeAndIndex();
					ti.setType("英语知识运用");
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
					break;
				}
			}
		}
		Collections.sort(lstTI);
		if (lstTI.size() > 0) {
			for (int i = 0; i < lstTI.size(); i++) {
				String type = lstTI.get(i).getType();
				List<String> lstContent1 = new ArrayList<String>();
				if (i < lstTI.size() - 1) {
					lstContent1 = lstContent.subList(lstTI.get(i).getIndex(), lstTI.get(i + 1).getIndex());
				} else if (i == lstTI.size() - 1) {
					lstContent1 = lstContent.subList(lstTI.get(i).getIndex(), lstContent.size());
				}
				if (type.equals("阅读理解")) {
					lstQuestion.addAll(this.splitYueDu(lstContent1));
				} else if(type.equals("英语知识运用")) {
					lstQuestion.addAll(this.splitJiChu(lstContent1));
				}else if(type.equals("写作")){
					lstQuestion.addAll(this.splitXieZuo(lstContent1));
				}else if(type.equals("答案开始")){
					
				}
			}
		}
		return lstQuestion;
	}

	// 得到阅读的List
	public List<Question> handleYueDu(List<String> lstContent) {
		int beginIndex = 0;
		int endIndex = 0;
		for (int i = 0; i < lstContent.size(); i++) {
			if (lstContent.get(i).contains("阅读理解")) {
				if (beginIndex == 0)
					beginIndex = i;
			}
			if (lstContent.get(i).contains("英语知识运用")) {
				if (endIndex == 0)
					endIndex = i;
			}
			if (lstContent.get(i).contains("写作")) {
				if (endIndex == 0)
					endIndex = i;
			}
		}
		List<String> lstYueDuContent = lstContent.subList(beginIndex, endIndex);
		return this.splitYueDu(lstYueDuContent);
	}

	// 得到基础知识应用的List
	public List<Question> handleJiChu(List<String> lstContent) {
		int beginIndex = 0;
		int endIndex = 0;
		for (int i = 0; i < lstContent.size(); i++) {
			if (lstContent.get(i).contains("英语知识运用")) {
				if (beginIndex == 0)
					beginIndex = i;
			}
			if (lstContent.get(i).contains("写作")) {
				if (endIndex == 0)
					endIndex = i;
			}
		}
		List<String> lstJiChu = lstContent.subList(beginIndex, endIndex);
		return this.splitJiChu(lstJiChu);
	}

	// 得到写作的List
	public List<Question> handleXieZuo(List<String> lstContent) {
		int beginIndex = 0;
		int endIndex = 0;
		for (int i = 0; i < lstContent.size(); i++) {
			if (lstContent.get(i).contains("写作")) {
				if (beginIndex == 0)
					beginIndex = i;
			}
			if (lstContent.get(i).contains("答案开始")) {
				if (endIndex == 0)
					endIndex = i;
			}
		}
		List<String> lstJiChu = new ArrayList<String>();
		if (endIndex == 0) {
			lstJiChu = lstContent.subList(beginIndex, lstContent.size());
		} else {
			lstJiChu = lstContent.subList(beginIndex, endIndex);
		}
		return this.splitXieZuo(lstJiChu);
	}

	// 拆分阅读理解
	public List<Question> splitYueDu(List<String> lstYueDuContent) {
		int index1 = 0;
		int index2 = 0;
		List<Question> lstQuestion = new ArrayList<Question>();

		for (int i = 0; i < lstYueDuContent.size(); i++) {
			String s = lstYueDuContent.get(i);
			s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
			if (s.contains("第一节")) {
				if (index1 == 0)
					index1 = i;
			}
			if (s.contains("第二节")) {
				if (index2 == 0)
					index2 = i;
			}
		}
		// 试卷有第一节第二节
		if (index1 != 0 && index2 != 0) {
			List<Question> lst = new ArrayList<Question>();
			lst = this.handleYueDu1(lstYueDuContent.subList(index1, index2));
			if (lst != null) {
				lstQuestion.addAll(lst);
			}
			List<Question> lst2 = new ArrayList<Question>();
			lst2 = this.handleYueDu2(lstYueDuContent.subList(index2, lstYueDuContent.size()));
			if (lst2 != null) {
				lstQuestion.addAll(lst2);
			}
		}
		return lstQuestion;
	}

	// 处理阅读第一节
	public List<Question> handleYueDu1(List<String> lstYueDuContent) {
		logger.info("---处理阅读第一节---");
		int indexA = 0;
		int indexB = 0;
		int indexC = 0;
		int indexD = 0;
		List<Question> lstQuestion = new ArrayList<Question>();

		for (int i = 0; i < lstYueDuContent.size(); i++) {
			String s = lstYueDuContent.get(i);
			s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
			s = s.replaceAll("[ ]*", "");
			if (s.equals("A")) {
				indexA = i;
			}
			if (s.equals("B")) {
				indexB = i;
			}
			if (s.equals("C")) {
				indexC = i;
			}
			if (s.equals("D")) {
				indexD = i;
			}
		}
		if (indexB != 0) {
			List<Question> lst = new ArrayList<Question>();
			lst = this.handleYueDu11(lstYueDuContent.subList(indexA + 1, indexB), "A");
			if (lst != null) {
				lstQuestion.addAll(lst);
			}
		}
		if (indexC != 0) {
			List<Question> lst = new ArrayList<Question>();
			lst = this.handleYueDu11(lstYueDuContent.subList(indexB + 1, indexC), "B");
			if (lst != null) {
				lstQuestion.addAll(lst);
			}
		}
		if (indexD != 0) {
			List<Question> lst = new ArrayList<Question>();
			lst = this.handleYueDu11(lstYueDuContent.subList(indexC + 1, indexD), "C");
			if (lst != null) {
				lstQuestion.addAll(lst);
			}
			List<Question> lst2 = new ArrayList<Question>();
			lst2 = this.handleYueDu11(lstYueDuContent.subList(indexD + 1, lstYueDuContent.size()), "D");
			if (lst2 != null) {
				lstQuestion.addAll(lst2);
			}
		}
		return lstQuestion;
	}

	// 处理阅读第二节
	public List<Question> handleYueDu2(List<String> lstYueDuContent) {
		logger.info("---处理阅读第二节---");
		List<Question> lstQuestion = new ArrayList<Question>();
		String regex = "<(\\S*?)[^>]*>(\t| )*[A][．.、,][\\s\\S]*<(\\S*?)[^>]*>";
		Pattern p = Pattern.compile(regex);
		List<Integer> indexs = new ArrayList<Integer>();
		for (int i = 0; i < lstYueDuContent.size(); i++) {
			String s = lstYueDuContent.get(i);
			Matcher m = p.matcher(s);
			while (m.find()) {
				indexs.add(i);
			}
		}
		Question q = new Question();
		if (indexs.size() > 2) {
			logger.debug("--拆分选项错误--拆出：" + indexs.size());
		} else {
			q.setType("阅读理解");
			String stem = this.removeTihao(lstYueDuContent.subList(1, indexs.get(0)));
			q.setStem(stem);
			q.setOptions(StringUtil.listToString(lstYueDuContent.subList(indexs.get(0), lstYueDuContent.size())));
			questionDao.save(q);
			lstQuestion.add(q);
		}
		return lstQuestion;
	}

	public List<Question> handleYueDu11(List<String> lstYueDuContent, String sign) {
		logger.info("---处理阅读理解" + sign + "篇---");
		List<Question> lstQuestion = new ArrayList<Question>();
		String regex = "<(\\S*?)[^>]*>(\t| )*[0-9]{1,2}[．.、,][\\s\\S]*<(\\S*?)[^>]*>";
		Pattern p = Pattern.compile(regex);
		List<Integer> indexs = new ArrayList<Integer>();
		for (int i = 0; i < lstYueDuContent.size(); i++) {
			String s = lstYueDuContent.get(i);
			// s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
			Matcher m = p.matcher(s);
			while (m.find()) {
				indexs.add(i);
			}
		}
		// 处理材料
		Question q = new Question();
		q.setTihao(sign);
		q.setType("材料类型");
		q.setStem(StringUtil.listToString(lstYueDuContent.subList(0, indexs.get(0))));
		questionDao.save(q);
		lstQuestion.add(q);
		// 处理除最后一道小题的小题
		for (int i = 0; i < indexs.size(); i++) {
			Question q1 = new Question();
			if (i < indexs.size() - 1) {
				q1 = this.splitOption(lstYueDuContent.subList(indexs.get(i), indexs.get(i + 1)));
			} else if (i == indexs.size() - 1) {
				q1 = this.splitOption(lstYueDuContent.subList(indexs.get(i), lstYueDuContent.size()));
			}
			q1.setType("阅读理解");
			q1.setPid(q.getQid());
			questionDao.save(q1);
			lstQuestion.add(q1);
		}
		return lstQuestion;
	}

	public Question splitOption(List<String> lstContent) {
		logger.info("---拆分小题选项---");
		String regex = "<(\\S*?)[^>]*>(\t| )*[A][．.、,][\\s\\S]*<(\\S*?)[^>]*>";
		Pattern p = Pattern.compile(regex);
		List<Integer> indexs = new ArrayList<Integer>();
		for (int i = 0; i < lstContent.size(); i++) {
			String s = lstContent.get(i);
			Matcher m = p.matcher(s);
			while (m.find()) {
				indexs.add(i);
			}
		}
		Question q = new Question();
		if (indexs.size() > 2) {
			logger.debug("--拆分选项错误--拆出：" + indexs.size());
		} else {
			q.setTihao(this.splitTihao(lstContent.get(0)));
			String stem = this.removeTihao(lstContent.subList(0, indexs.get(0)));
			q.setStem(stem);
			q.setOptions(StringUtil.listToString(lstContent.subList(indexs.get(0), lstContent.size())));
		}
		return q;
	}

	public String splitTihao(String s) {
		String regex = "<(\\S*?)[^>]*>(\t| )*([0-9]{1,2})[．.、,][\\s\\S]*<(\\S*?)[^>]*>";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		while (m.find()) {
			return m.group(3);
		}
		return null;
	}

	public List<Question> splitJiChu(List<String> lstJiChu) {
		logger.info("---拆分基础知识应用---");
		int index1 = 0;
		int index2 = 0;
		List<Question> lstQuestion = new ArrayList<Question>();
		for (int i = 0; i < lstJiChu.size(); i++) {
			String s = lstJiChu.get(i);
			s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
			if (s.contains("完形填空")) {
				if (index1 == 0)
					index1 = i;
			}
			if (s.contains("语法填空")) {
				if (index2 == 0)
					index2 = i;
			}
		}
		// 只有完形填空
		if (index1 != 0 && index2 == 0) {
			List<Question> lst = new ArrayList<Question>();
			lst = this.handleWanXing(lstJiChu.subList(index1, lstJiChu.size()));
			if (lst != null) {
				lstQuestion.addAll(lst);
			}
		}
		// 只有语法填空
		if (index1 == 0 && index2 != 0) {
			List<Question> lst = new ArrayList<Question>();
			lst = this.handleYuFa(lstJiChu.subList(index2, lstJiChu.size()));
			if (lst != null) {
				lstQuestion.addAll(lst);
			}
		}
		// 既有完形填空也有语法填空
		if (index1 != 0 && index2 != 0) {
			List<Question> lst = new ArrayList<Question>();
			lst = this.handleWanXing(lstJiChu.subList(index1, index2));
			if (lst != null) {
				lstQuestion.addAll(lst);
			}
			List<Question> lst2 = new ArrayList<Question>();
			lst = this.handleYuFa(lstJiChu.subList(index2, lstJiChu.size()));
			if (lst2 != null) {
				lstQuestion.addAll(lst2);
			}
		}

		return lstQuestion;
	}

	public List<Question> handleWanXing(List<String> lstWanXing) {
		logger.info("---处理完形填空---");
		List<Question> lstQuestion = new ArrayList<Question>();
		String regex = "<(\\S*?)[^>]*>(\t| )*[0-9]{1,2}[．.、,][\\s\\S]*<(\\S*?)[^>]*>";
		Pattern p = Pattern.compile(regex);
		List<Integer> indexs = new ArrayList<Integer>();
		for (int i = 1; i < lstWanXing.size(); i++) {
			String s = lstWanXing.get(i);
			Matcher m = p.matcher(s);
			while (m.find()) {
				indexs.add(i);
			}
		}
		// 处理材料
		Question q = new Question();
		q.setType("材料类型");
		String stem = this.removeTihao(lstWanXing.subList(1, indexs.get(0)));
		q.setStem(stem);
		questionDao.save(q);
		lstQuestion.add(q);
		for (int i = 0; i < indexs.size(); i++) {
			Question q1 = new Question();
			if (i < indexs.size() - 1) {
				q1.setTihao(this.splitTihao(lstWanXing.get(indexs.get(i))));
				q1.setOptions(StringUtil.listToString(lstWanXing.subList(indexs.get(i), indexs.get(i + 1))));
			} else if (i == indexs.size() - 1) {
				q1.setTihao(this.splitTihao(lstWanXing.get(indexs.get(i))));
				q1.setOptions(StringUtil.listToString(lstWanXing.subList(indexs.get(i), lstWanXing.size())));
			}
			q1.setType("完形填空");
			q1.setPid(q.getQid());
			questionDao.save(q1);
			lstQuestion.add(q1);
		}
		return lstQuestion;
	}

	public List<Question> handleYuFa(List<String> lstYuFa) {
		logger.info("---处理语法填空---");
		List<Question> lstQuestion = new ArrayList<Question>();
		Question q = new Question();
		q.setType("语法填空");
		String stem = this.removeTihao(lstYuFa.subList(1, lstYuFa.size()));
		q.setStem(stem);
		questionDao.save(q);
		lstQuestion.add(q);
		return lstQuestion;
	}

	// 拆分写作
	public List<Question> splitXieZuo(List<String> lstXieZuo) {
		logger.info("---拆分写作---");
		List<Question> lstQuestion = new ArrayList<Question>();
		List<TypeAndIndex> lstTI = new ArrayList<TypeAndIndex>();
		for (int i = 0; i < lstXieZuo.size(); i++) {
			String s = lstXieZuo.get(i);
			s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
			if (s.contains("单词拼写")) {
				TypeAndIndex ti = new TypeAndIndex();
				ti.setType("单词拼写");
				ti.setIndex(i);
				lstTI.add(ti);
			}
			if (s.contains("句子填空")) {
				TypeAndIndex ti = new TypeAndIndex();
				ti.setType("句子填空");
				ti.setIndex(i);
				lstTI.add(ti);
			}
			if (s.contains("短文改错")) {
				TypeAndIndex ti = new TypeAndIndex();
				ti.setType("短文改错");
				ti.setIndex(i);
				lstTI.add(ti);
			}
			if (s.contains("书面表达")) {
				TypeAndIndex ti = new TypeAndIndex();
				ti.setType("书面表达");
				ti.setIndex(i);
				lstTI.add(ti);
			}
		}
		Collections.sort(lstTI);
		if (lstTI.size() > 1) {
			for (int i = 0; i < lstTI.size(); i++) {
				String type = lstTI.get(i).getType();
				List<String> lstContent = new ArrayList<String>();
				if (i < lstTI.size() - 1) {
					lstContent = lstXieZuo.subList(lstTI.get(i).getIndex(), lstTI.get(i + 1).getIndex());
				} else if (i == lstTI.size() - 1) {
					lstContent = lstXieZuo.subList(lstTI.get(i).getIndex(), lstXieZuo.size());
				}
				if (type.equals("单词拼写")) {
					lstQuestion.addAll(this.handleDanCi(lstContent));
				}
				if (type.equals("句子填空")) {
					lstQuestion.addAll(this.handleJuZi(lstContent));
				}
				if (type.equals("短文改错")) {
					lstQuestion.addAll(this.handleDuanWen(lstContent));
				}
				if (type.equals("书面表达")) {
					lstQuestion.addAll(this.handleShuMian(lstContent));
				}
			}
		}
		return lstQuestion;
	}

	public List<Question> handleDanCi(List<String> lstDanCi) {
		logger.info("---处理单词填空---");
		List<Question> lstQuestion = new ArrayList<Question>();
		String regex = "<(\\S*?)[^>]*>(\t| )*[0-9]{1,2}[．.、,][\\s\\S]*<(\\S*?)[^>]*>";
		Pattern p = Pattern.compile(regex);
		List<Integer> indexs = new ArrayList<Integer>();
		for (int i = 1; i < lstDanCi.size(); i++) {
			String s = lstDanCi.get(i);
			Matcher m = p.matcher(s);
			while (m.find()) {
				indexs.add(i);
			}
		}
		// 处理除最后一道小题的小题
		for (int i = 0; i < indexs.size(); i++) {
			Question q1 = new Question();
			if (i < indexs.size() - 1) {
				q1.setTihao(this.splitTihao(lstDanCi.get(indexs.get(i))));
				String stem = this.removeTihao(lstDanCi.subList(indexs.get(i), indexs.get(i + 1)));
				q1.setStem(stem);
			} else if (i == indexs.size() - 1) {
				q1.setTihao(this.splitTihao(lstDanCi.get(indexs.get(i))));
				String stem = this.removeTihao(lstDanCi.subList(indexs.get(i), lstDanCi.size()));
				q1.setStem(stem);
			}
			q1.setType("单词填空");
			questionDao.save(q1);
			lstQuestion.add(q1);
		}
		return lstQuestion;
	}

	// 处理句子填空
	public List<Question> handleJuZi(List<String> lstDanCi) {
		logger.info("---处理句子填空---");
		List<Question> lstQuestion = new ArrayList<Question>();
		String regex = "<(\\S*?)[^>]*>(\t| )*[0-9]{1,2}[．.、,][\\s\\S]*<(\\S*?)[^>]*>";
		Pattern p = Pattern.compile(regex);
		List<Integer> indexs = new ArrayList<Integer>();
		for (int i = 1; i < lstDanCi.size(); i++) {
			String s = lstDanCi.get(i);
			Matcher m = p.matcher(s);
			while (m.find()) {
				indexs.add(i);
			}
		}
		// 处理除最后一道小题的小题
		for (int i = 0; i < indexs.size(); i++) {
			Question q1 = new Question();
			if (i < indexs.size() - 1) {
				q1.setTihao(this.splitTihao(lstDanCi.get(indexs.get(i))));
				String stem = this.removeTihao(lstDanCi.subList(indexs.get(i), indexs.get(i + 1)));
				q1.setStem(stem);
			} else if (i == indexs.size() - 1) {
				q1.setTihao(this.splitTihao(lstDanCi.get(indexs.get(i))));
				String stem = this.removeTihao(lstDanCi.subList(indexs.get(i), lstDanCi.size()));
				q1.setStem(stem);
			}
			q1.setType("句子填空");
			questionDao.save(q1);
			lstQuestion.add(q1);
		}
		return lstQuestion;
	}

	public List<Question> handleDuanWen(List<String> lstDanCi) {
		logger.info("---处理短文改错---");
		List<Question> lstQuestion = new ArrayList<Question>();
		Question q = new Question();
		q.setType("短文改错");
		String stem = this.removeTihao(lstDanCi.subList(1, lstDanCi.size()));
		q.setStem(stem);
		questionDao.save(q);
		lstQuestion.add(q);
		return lstQuestion;
	}

	public List<Question> handleShuMian(List<String> lstShuMian) {
		logger.info("---处理书面表达---");
		List<Question> lstQuestion = new ArrayList<Question>();
		Question q = new Question();
		q.setType("书面表达");
		String stem = this.removeTihao(lstShuMian.subList(1, lstShuMian.size()));
		q.setStem(stem);
		questionDao.save(q);
		lstQuestion.add(q);
		return lstQuestion;
	}

	public String removeTihao(List<String> lstContent) {
		String s = lstContent.get(0);
		s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
		s = s.replaceAll("^(\t| )*[0-9]{1,2}[．.、,]", "");
		lstContent.set(0, s);
		String stem = StringUtil.listToString(lstContent);
		return stem;
	}

}
