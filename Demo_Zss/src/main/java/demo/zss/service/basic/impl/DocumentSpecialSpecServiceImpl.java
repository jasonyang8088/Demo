package demo.zss.service.basic.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import demo.zss.entity.basic.BookNode;
import demo.zss.entity.basic.DocumentSpecialSpec;
import demo.zss.entity.basic.KnowledgePoint;
import demo.zss.repository.basic.DocumentSpecialSpecRepository;
import demo.zss.service.basic.DocumentSpecialSpecService;

@Service
public class DocumentSpecialSpecServiceImpl implements DocumentSpecialSpecService {

	@Autowired
	private DocumentSpecialSpecRepository repository;

	@Override
	public DocumentSpecialSpec returnSpecial(DocumentSpecialSpec special) {
		BookNode bd = special.getBookNode();
		if (bd != null) {
			if (bd.getDepth() == 3) {
				special.setBookNode3(bd);
				special.setBookNode2(bd.getParent());
				special.setBookNode1(bd.getParent().getParent());
			}

			if (bd.getDepth() == 2) {
				special.setBookNode2(bd);
				special.setBookNode1(bd.getParent());
			}

			if (bd.getDepth() == 1) {
				special.setBookNode1(bd);
			}
		}

		KnowledgePoint kp = special.getKnowledgePoint();
		if (kp != null) {

			if (kp.getDepth() == 5) {
				KnowledgePoint kp4 = kp.getParent();
				KnowledgePoint kp3 = kp4.getParent();
				KnowledgePoint kp2 = kp3.getParent();
				KnowledgePoint kp1 = kp2.getParent();
				special.setKnowledgePoint5(kp);
				special.setKnowledgePoint4(kp4);
				special.setKnowledgePoint3(kp3);
				special.setKnowledgePoint2(kp2);
				special.setKnowledgePoint1(kp1);
			}
			if (kp.getDepth() == 4) {
				KnowledgePoint kp3 = kp.getParent();
				KnowledgePoint kp2 = kp3.getParent();
				KnowledgePoint kp1 = kp2.getParent();
				special.setKnowledgePoint4(kp);
				special.setKnowledgePoint3(kp3);
				special.setKnowledgePoint2(kp2);
				special.setKnowledgePoint1(kp1);
			}
			if (kp.getDepth() == 3) {
				KnowledgePoint kp2 = kp.getParent();
				KnowledgePoint kp1 = kp2.getParent();
				special.setKnowledgePoint3(kp);
				special.setKnowledgePoint2(kp2);
				special.setKnowledgePoint1(kp1);
			}
			if (kp.getDepth() == 2) {
				special.setKnowledgePoint2(kp);
				special.setKnowledgePoint1(kp.getParent());
			}
			if (kp.getDepth() == 1) {
				special.setKnowledgePoint1(kp);
			}
		}
		DocumentSpecialSpec spec = repository.findOne(createSpecification(special));
		if (spec == null) {
			repository.save(special);
			return special;
		} else {
			return spec;
		}
	}

	public Specification<DocumentSpecialSpec> createSpecification(DocumentSpecialSpec special) {
		Specification<DocumentSpecialSpec> spec = new Specification<DocumentSpecialSpec>() {
			public Predicate toPredicate(Root<DocumentSpecialSpec> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (null != special.getSubject()) {
					list.add(cb.equal(root.get("subject").get("id").as(Long.class), special.getSubject().getId()));
				} else {
					list.add(cb.isNull(root.get("subject")));
				}
				if (null != special.getVersion()) {
					list.add(cb.equal(root.get("version").get("id").as(Long.class), special.getVersion().getId()));
				} else {
					list.add(cb.isNull(root.get("version")));
				}
				if (null != special.getTextBook()) {
					list.add(cb.equal(root.get("textBook").get("id").as(Long.class), special.getTextBook().getId()));
				} else {
					list.add(cb.isNull(root.get("textBook")));
				}
				if (null != special.getBookNode1()) {
					list.add(cb.equal(root.get("bookNode1").get("id").as(Long.class), special.getBookNode1().getId()));
				} else {
					list.add(cb.isNull(root.get("bookNode1")));
				}
				if (null != special.getBookNode2()) {
					list.add(cb.equal(root.get("bookNode2").get("id").as(Long.class), special.getBookNode2().getId()));
				} else {
					list.add(cb.isNull(root.get("bookNode2")));
				}
				if (null != special.getBookNode3()) {
					list.add(cb.equal(root.get("bookNode3").get("id").as(Long.class), special.getBookNode3().getId()));
				} else {
					list.add(cb.isNull(root.get("bookNode3")));
				}
				if (null != special.getKnowledgePoint1()) {
					list.add(cb.equal(root.get("knowledgePoint1").get("id").as(Long.class), special.getKnowledgePoint1().getId()));
				} else {
					list.add(cb.isNull(root.get("knowledgePoint1")));
				}
				if (null != special.getKnowledgePoint2()) {
					list.add(cb.equal(root.get("knowledgePoint2").get("id").as(Long.class), special.getKnowledgePoint2().getId()));
				} else {
					list.add(cb.isNull(root.get("knowledgePoint2")));
				}
				if (null != special.getKnowledgePoint3()) {
					list.add(cb.equal(root.get("knowledgePoint3").get("id").as(Long.class), special.getKnowledgePoint3().getId()));
				} else {
					list.add(cb.isNull(root.get("knowledgePoint3")));
				}
				if (null != special.getKnowledgePoint4()) {
					list.add(cb.equal(root.get("knowledgePoint4").get("id").as(Long.class), special.getKnowledgePoint4().getId()));
				} else {
					list.add(cb.isNull(root.get("knowledgePoint4")));
				}
				if (null != special.getKnowledgePoint5()) {
					list.add(cb.equal(root.get("knowledgePoint5").get("id").as(Long.class), special.getKnowledgePoint5().getId()));
				} else {
					list.add(cb.isNull(root.get("knowledgePoint5")));
				}

				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		};
		return spec;
	}

}
