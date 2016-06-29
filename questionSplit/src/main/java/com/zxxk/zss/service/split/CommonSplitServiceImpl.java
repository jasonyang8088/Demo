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
public class CommonSplitServiceImpl implements SplitService {
	private Logger logger = LoggerFactory.getLogger(CommonSplitServiceImpl.class);

	@Autowired
	private QuestionDAO questionDao;

	@Override
	public List<Question> splitQuestion(List<String> lstContent) {
		logger.info("---拆分大题---");
		List<Question> lstQuestion = new ArrayList<Question>();
		List<TypeAndIndex> lstTI = new ArrayList<TypeAndIndex>();
		for (int i = 0; i < lstContent.size(); i++) {
			String s = lstContent.get(i);
			s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
			String regex = "^(\t| )*[一二三四五六七八九]?.[ ]*(选择题|单项选择题|双项选择题|多项选择题|非选择题|填空题|解答题|综合题|探究题|答案开始).*";
			if (s.matches(regex)) {
				if (s.contains("选择题") && !s.contains("非选择题")) {
					TypeAndIndex ti = new TypeAndIndex();
					ti.setType("选择题");
					ti.setIndex(i);
					lstTI.add(ti);
				}
				if (s.contains("单项选择题")) {
					TypeAndIndex ti = new TypeAndIndex();
					ti.setType("单项选择题");
					ti.setIndex(i);
					lstTI.add(ti);
				}
				if (s.contains("双项选择题")) {
					TypeAndIndex ti = new TypeAndIndex();
					ti.setType("双项选择题");
					ti.setIndex(i);
					lstTI.add(ti);
				}
				if (s.contains("多项选择题")) {
					TypeAndIndex ti = new TypeAndIndex();
					ti.setType("多项选择题");
					ti.setIndex(i);
					lstTI.add(ti);
				}
				if (s.contains("非选择题")) {
					TypeAndIndex ti = new TypeAndIndex();
					ti.setType("非选择题");
					ti.setIndex(i);
					lstTI.add(ti);
				}
				if (s.contains("填空题")) {
					TypeAndIndex ti = new TypeAndIndex();
					ti.setType("填空题");
					ti.setIndex(i);
					lstTI.add(ti);
				}
				if (s.contains("解答题")) {
					TypeAndIndex ti = new TypeAndIndex();
					ti.setType("解答题");
					ti.setIndex(i);
					lstTI.add(ti);
				}
				if (s.contains("综合题")) {
					TypeAndIndex ti = new TypeAndIndex();
					ti.setType("综合题");
					ti.setIndex(i);
					lstTI.add(ti);
				}
				if (s.contains("探究题")) {
					TypeAndIndex ti = new TypeAndIndex();
					ti.setType("探究题");
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
				// if (type.equals("选择题")) {
				// lstQuestion.addAll(this.handleXuanZe(lstContent1, "选择题"));
				// }
				// if (type.equals("非选择题")) {
				// lstQuestion.addAll(this.handleXuanZe(lstContent1, "非选择题"));
				// }
				// if (type.equals("综合题")) {
				// lstQuestion.addAll(this.handleXuanZe(lstContent1, "综合题"));
				// }
				if (!type.equals("答案开始")) {
					lstQuestion.addAll(this.handleXuanZe(lstContent1, type));
				} else {
					lstQuestion = this.handleAnswer(lstQuestion, lstContent1);
				}
			}
		}
		return lstQuestion;
	}

	public List<Question> handleXuanZe(List<String> lstContent, String type) {
		logger.info("---拆分"+type+"---");
		Boolean tableSign = true;
		List<Question> lstQuestion = new ArrayList<Question>();
		String regex = "^(\t| )*[0-9]{1,2}[．.、,][\\s\\S]*";
		Pattern p = Pattern.compile(regex);
		List<Integer> indexs = new ArrayList<Integer>();
		for (int i = 0; i < lstContent.size(); i++) {
			String s = lstContent.get(i);
			if (s.contains("<table"))
				tableSign = false;
			if (s.contains("</table>"))
				tableSign = true;
			if (tableSign) {
				s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
				Matcher m = p.matcher(s);
				while (m.find()) {
					indexs.add(i);
				}
			}
		}
		for (int i = 0; i < indexs.size(); i++) {
			Question q1 = new Question();
			if (i < indexs.size() - 1) {
				q1 = this.splitSign(lstContent.subList(indexs.get(i), indexs.get(i + 1)), type);
			} else if (i == indexs.size() - 1) {
				q1 = this.splitSign(lstContent.subList(indexs.get(i), lstContent.size()), type);
			}
			q1.setType(type);
			questionDao.save(q1);
			lstQuestion.add(q1);
		}
		return lstQuestion;
	}

	public Question splitOption(List<String> lstContent, String type) {
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
		Question q = new Question();
		if (indexs.size() > 2) {
			logger.debug("--拆分选项错误--拆出：" + indexs.size());
		} else if (indexs.size() == 1) { // 判断是否选择题
			q.setTihao(this.splitTihao(lstContent.get(0)));
			q.setType(type);
			String s = lstContent.get(0);
			s = this.removeTihao(s);
			lstContent.set(0, s);
			String stem = StringUtil.listToString(lstContent.subList(0, indexs.get(0)));
			q.setStem(stem);
			q.setOptions(StringUtil.listToString(lstContent.subList(indexs.get(0), lstContent.size())));
		} else if (indexs.size() == 0) { // 判断是否非选择题
			q.setTihao(this.splitTihao(lstContent.get(0)));
			q.setType(type);
			String stem = StringUtil.listToString(lstContent.subList(0, lstContent.size()));
			q.setStem(stem);
		}
		return q;
	}

	public String splitTihao(String s) {
		s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
		String regex = "^(\t| )*([0-9]{1,2})[．.、,][\\s\\S]*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		while (m.find()) {
			return m.group(2);
		}
		return null;
	}

	public Question splitSign(List<String> lstContent, String type) {
		Question q = new Question();
		String regex = "^(【)?(\t| )*(答案|解析|题型|难度|分值)";
		Pattern p = Pattern.compile(regex);
		List<Integer> indexs = new ArrayList<Integer>();
		List<TypeAndIndex> lstTI = new ArrayList<TypeAndIndex>();
		for (int i = 0; i < lstContent.size(); i++) {
			TypeAndIndex ti = new TypeAndIndex();
			String s = lstContent.get(i);
			s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
			Matcher m = p.matcher(s);
			while (m.find()) {
				indexs.add(i);
				ti.setType(m.group(3));
				ti.setIndex(i);
				lstTI.add(ti);
			}

		}
		if (lstTI.size() > 0) {
			Collections.sort(lstTI);
			q = this.splitOption(lstContent.subList(0, lstTI.get(0).getIndex()), type);
			for (int i = 0; i < lstTI.size(); i++) {
				String sign = lstTI.get(i).getType();
				if (i < indexs.size() - 1) {
					List<String> listS = new ArrayList<String>();
					listS = lstContent.subList(lstTI.get(i).getIndex(), lstTI.get(i + 1).getIndex());
					String temp1 = listS.get(0);
					temp1 = temp1.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
					temp1 = temp1.replaceAll("^(【)?(\t| )*(答案|解析|题型|难度|分值)[ :：】]", "");
					listS.set(0, temp1);
					String s = StringUtil.listToString(listS);
					if (sign.equals("答案"))
						q.setAnswer(s);
					if (sign.equals("解析"))
						q.setAnalyse(s);
					if (sign.equals("难度"))
						q.setDifficult(s);
					if (sign.equals("分值"))
						q.setFenzhi(s);
				} else if (i == indexs.size() - 1) {
					List<String> listS = new ArrayList<String>();
					listS = lstContent.subList(lstTI.get(i).getIndex(), lstContent.size());
					String temp1 = listS.get(0);
					temp1 = temp1.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
					temp1 = temp1.replaceAll("^(【)?(\t| )*(答案|解析|题型|难度|分值)[ :：】]", "");
					listS.set(0, temp1);
					String s = StringUtil.listToString(listS);
					if (sign.equals("答案"))
						q.setAnswer(s);
					if (sign.equals("解析"))
						q.setAnalyse(s);
					if (sign.equals("难度"))
						q.setDifficult(s);
					if (sign.equals("分值"))
						q.setFenzhi(s);
				}
			}
		} else {
			q = this.splitOption(lstContent, type);
		}
		return q;
	}

	public List<Question> handleAnswer(List<Question> lstQuestion, List<String> lstContent) {
		Boolean tableSign = true;
		String regex = "^(\t| )*[0-9]{1,2}[．.、,][\\s\\S]*";
		Pattern p = Pattern.compile(regex);
		List<Integer> indexs = new ArrayList<Integer>();
		for (int i = 0; i < lstContent.size(); i++) {
			String s = lstContent.get(i);
			if (s.contains("<table"))
				tableSign = false;
			if (s.contains("</table>"))
				tableSign = true;
			if (tableSign) {
				s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
				Matcher m = p.matcher(s);
				while (m.find()) {
					indexs.add(i);
				}
			}
		}
		for (int i = 0; i < indexs.size(); i++) {
			Question q1 = new Question();
			if (i < indexs.size() - 1) {
				q1 = this.splitAnswerSign(lstContent.subList(indexs.get(i), indexs.get(i + 1)));
			} else if (i == indexs.size() - 1) {
				q1 = this.splitAnswerSign(lstContent.subList(indexs.get(i), lstContent.size()));
			}
			for (Question q : lstQuestion) {
				if (q.getTihao() != null && q1.getTihao() != null && q1.getTihao().equals(q.getTihao())) {
					if (q1.getAnswer() != null)
						q.setAnswer(q1.getAnswer());
					if (q1.getAnalyse() != null)
						q.setAnalyse(q1.getAnalyse());
					if (q1.getDifficult() != null)
						q.setDifficult(q1.getDifficult());
					if (q1.getFenzhi() != null)
						q.setFenzhi(q1.getFenzhi());
					questionDao.update(q);
				}
			}
		}
		return lstQuestion;
	}

	public Question splitAnswerSign(List<String> lstContent) {
		Question q = new Question();
		String tihao = this.splitTihao(lstContent.get(0));
		q.setTihao(tihao);
		String temp = lstContent.get(0);
		temp = temp.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
		temp = temp.replaceAll("^(\t| )*[0-9]{1,2}[．.、,]", "");
		lstContent.set(0, temp);
		String regex = "^(【)?(\t| )*(答案|解析|题型|难度|分值)";
		Pattern p = Pattern.compile(regex);
		List<Integer> indexs = new ArrayList<Integer>();
		List<TypeAndIndex> lstTI = new ArrayList<TypeAndIndex>();
		for (int i = 0; i < lstContent.size(); i++) {
			TypeAndIndex ti = new TypeAndIndex();
			String s = lstContent.get(i);
			s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
			Matcher m = p.matcher(s);
			while (m.find()) {
				indexs.add(i);
				ti.setType(m.group(3));
				ti.setIndex(i);
				lstTI.add(ti);
			}

		}
		if (lstTI.size() > 0) {
			Collections.sort(lstTI);
			for (int i = 0; i < lstTI.size(); i++) {
				String sign = lstTI.get(i).getType();
				if (i < indexs.size() - 1) {
					List<String> listS = new ArrayList<String>();
					listS = lstContent.subList(lstTI.get(i).getIndex(), lstTI.get(i + 1).getIndex());
					String temp1 = listS.get(0);
					temp1 = temp1.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
					temp1 = temp1.replaceAll("^(【)?(\t| )*(答案|解析|题型|难度|分值)[ :：】]", "");
					listS.set(0, temp1);
					String s = StringUtil.listToString(listS);
					if (sign.equals("答案"))
						q.setAnswer(s);
					if (sign.equals("解析"))
						q.setAnalyse(s);
					if (sign.equals("难度"))
						q.setDifficult(s);
					if (sign.equals("分值"))
						q.setFenzhi(s);
				} else if (i == indexs.size() - 1) {
					List<String> listS = new ArrayList<String>();
					listS = lstContent.subList(lstTI.get(i).getIndex(), lstContent.size());
					String temp1 = listS.get(0);
					temp1 = temp1.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
					temp1 = temp1.replaceAll("^(【)?(\t| )*(答案|解析|题型|难度|分值)[ :：】]", "");
					listS.set(0, temp1);
					String s = StringUtil.listToString(listS);
					if (sign.equals("答案"))
						q.setAnswer(s);
					if (sign.equals("解析"))
						q.setAnalyse(s);
					if (sign.equals("难度"))
						q.setDifficult(s);
					if (sign.equals("分值"))
						q.setFenzhi(s);
				}
			}
		}
		return q;
	}

	public String removeTihao(String s) {
		s = s.replaceAll("<(\\S*?)[^>]*>([\\s\\S]*?)", "");
		s = s.replaceAll("^(\t| )*[0-9]{1,2}[．.、,]", "");
		return s;
	}

}
