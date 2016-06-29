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
@Transactional
public class MathSplitServiceImpl implements SplitService {

	private Logger logger = LoggerFactory.getLogger(MathSplitServiceImpl.class);

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
			if (s.contains("选择题")) {
				TypeAndIndex ti = new TypeAndIndex();
				ti.setType("选择题");
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
				if (type.equals("选择题")) {
					lstQuestion.addAll(this.handleXuanZe(lstContent1,"选择题"));
				}
				if (type.equals("填空题")) {
					lstQuestion.addAll(this.handleXuanZe(lstContent1,"填空题"));
				}
				if (type.equals("解答题")) {
					lstQuestion.addAll(this.handleXuanZe(lstContent1,"解答题"));
				}
				if (type.equals("答案开始")) {
					break;
				}
			}
		}
		return lstQuestion;
	}

	public List<Question> handleXuanZe(List<String> lstContent,String type) {
		logger.info("---拆分选择题---");
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
		for(int i=0;i<indexs.size();i++){
			Question q1 = new Question();
			if (i < indexs.size() - 1) {
				q1 = this.splitOption(lstContent.subList(indexs.get(i), indexs.get(i + 1)),type);
			} else if (i == indexs.size() - 1) {
				q1 = this.splitOption(lstContent.subList(indexs.get(i), lstContent.size()),type);
			}
			q1.setType(type);
			questionDao.save(q1);
			lstQuestion.add(q1);
		}
		return lstQuestion;
	}

	public Question splitOption(List<String> lstContent,String type) {
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
