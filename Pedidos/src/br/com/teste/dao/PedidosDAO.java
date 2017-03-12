package br.com.teste.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Criteria;

import br.com.teste.HibernateUtil;
import br.com.teste.model.Pedidos;


public class PedidosDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	private static PedidosDAO instance;

	protected Session session;
	
	public static PedidosDAO getInstance(){
		if(instance == null)
			instance = new PedidosDAO();
		return instance;
	}
	
	public ArrayList<Pedidos> listarPedidos(String strPedido, String strDataCadastro){
		System.out.println("LISTANDO Pedidos");
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			Criteria cr = session.createCriteria(Pedidos.class);
			if (strPedido!=null && strPedido.trim().length() > 0){
				cr.add(Restrictions.eq("numero", strPedido));
			}
			if (strDataCadastro!=null &&  strDataCadastro.trim().length() > 0){
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
 		        Date date = formatter.parse(strDataCadastro);
				Timestamp data = new Timestamp(date.getTime());
				cr.add(Restrictions.eq("data", data));
			}
			System.out.println("Vai Abrir");
			List<Pedidos> P = cr.list();
			
			System.out.println("Abriu " + P.size());
			return new ArrayList<Pedidos>(P);
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}catch(ParseException ex){
			ex.printStackTrace();
		} finally {
			session.close();
		}
		
		return null;
	}
	
	public boolean isExistePed(Pedidos pedido) throws Exception{
		session = HibernateUtil.getSessionfactory().openSession();
		try {
			Criteria cr = session.createCriteria(Pedidos.class);
			cr.add(Restrictions.eq("numero", pedido.getNumero()));			
			List<Pedidos> P = cr.list();			
			System.out.println("Abriu " + P.size());
			return P.size() > 0;
		} catch (HibernateException ex) {
			ex.printStackTrace();		
		} finally {
			session.close();
		}		
		return false;
	}
	
	public void save(Pedidos ped) {
		session = HibernateUtil.getSessionfactory().openSession();

		try {
			session.getTransaction().begin();
			session.saveOrUpdate(ped);
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	
}
