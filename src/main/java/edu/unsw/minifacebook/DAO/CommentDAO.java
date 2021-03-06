package edu.unsw.minifacebook.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.unsw.minifacebook.bean.CommentBean;
import edu.unsw.minifacebook.bean.CommentBean;
import edu.unsw.minifacebook.bean.UserBean;

public class CommentDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	
	private Session getCurrentSession() {
		return sessionFactory.openSession();
	}
	
	public void saveObject(Object obj) throws HibernateException{
		this.getCurrentSession().save(obj);
	}
	
	public void addComments(UserBean reply_from, UserBean reply_to, String content) {
		CommentBean commentBean = new CommentBean();
		commentBean.setReplyFrom(reply_from);
		commentBean.setReplyTo(reply_to);
		commentBean.setContent(content);
		this.getCurrentSession().save(commentBean);
	}
	
	public void deleteComments(int id) {
		try {
			Transaction trn = this.getCurrentSession().beginTransaction();
			CommentBean uncommentBean = (CommentBean) this.getCurrentSession().get(CommentBean.class, id);
			this.getCurrentSession().delete(uncommentBean);
			trn.commit();
			this.getCurrentSession().close();
		} catch (HibernateException e) {
			e.printStackTrace();
			this.getCurrentSession().getTransaction().rollback();
		}
	}

}
