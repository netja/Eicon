package br.com.teste.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.teste.dao.PedidosDAO;
import br.com.teste.model.Pedidos;


public class PedidosController {

	public ArrayList<Pedidos> listarPedidos(String strPedido, String strDataCadastro){		
		return PedidosDAO.getInstance().listarPedidos(strPedido, strDataCadastro);
	}
	
	public String adiciona(List<Pedidos> pedidos){		
		String strReturn = "";
		try{
			
			for (Pedidos pedido : pedidos){			
				if (isExistePed(pedido)){
					strReturn += "Pedido " + pedido.getNumero() + " já cadastrado!\n";
					continue;
				}
				if (!isExisteCliente(pedido)){
					strReturn += "Pedido " + pedido.getNumero() + ". Cliente " + pedido.getCliente() + " não existe!'\n";
					continue;
				}
				if (pedido.getData() == null){
					pedido.setData(new Timestamp(System.currentTimeMillis()));
				}
				if (pedido.getQuantidade() < 1){
					pedido.setQuantidade(1);
				}
				
				pedido.setValortotal(pedido.getQuantidade() * pedido.getValor());
				if (pedido.getQuantidade() > 10){
					pedido.setValortotal(pedido.getValortotal() - (pedido.getValortotal() * 0.1));
				}else if (pedido.getQuantidade() > 5){
					pedido.setValortotal(pedido.getValortotal() - (pedido.getValortotal() * 0.05));
				}		
				
				PedidosDAO.getInstance().save(pedido);
			}			
		}catch (Exception e) {
			System.out.println("Erro : " + e.getMessage());
			e.printStackTrace();
		} finally {
		}
		if (strReturn.trim().length() == 0)
			return "Pedidos Adicionados";
		else 
			return "Pedidos Adicionados com advetências!\n" +strReturn;
		
	}
	
	private boolean isExistePed(Pedidos pedido) throws Exception{		
		return PedidosDAO.getInstance().isExistePed(pedido);
	}
	private boolean isExisteCliente(Pedidos pedido){
		return  (pedido.getCliente() > 0 && pedido.getCliente() <= 10);
		
	}
}
