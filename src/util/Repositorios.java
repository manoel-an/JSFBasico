package util;

import infra.LancamentosHibernate;
import infra.PessoasHibernate;

import java.io.Serializable;

import org.hibernate.Session;

import repository.Lancamentos;
import repository.Pessoas;

public class Repositorios implements Serializable {

	public Pessoas getPessoas() {
		return new PessoasHibernate(this.getSession());
	}
	
	public Lancamentos getLancamentos() {
		return new LancamentosHibernate(this.getSession());
	}
	
	private Session getSession() {
		return (Session) FacesUtil.getRequestAttribute("session");
	}
	
}