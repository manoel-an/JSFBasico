import java.util.List;

import model.Pessoa;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import util.HibernateUtil;


public class TesteHibernate {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Session session = HibernateUtil.getSession();
		
		List<Pessoa> pessoas = session.createCriteria(Pessoa.class)
				.list();
		
		for (Pessoa p : pessoas) {
			System.out.println(p.getCodigo() + " - " + p.getNome());
		}
		
		session.close();
	}
	
}