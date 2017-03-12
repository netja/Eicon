package br.com.teste.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import br.com.teste.controller.PedidosController;
import br.com.teste.model.Pedidos;


/**
 * 
 * @author Alexandre JARDIM
 *
 */

@Path("/pedidos")
public class PedidosResource {
	//http://localhost:8080/Pedidos/pedidos/listarPedidos?pedido=ABCD&dataCadastro=10/03/2017
	
	@GET
	@Path("/listarPedidos")
	//@Produces("application/json")
	@Produces({"application/xml", "application/json"})
	public ArrayList<Pedidos> listarPedidos(@QueryParam("pedido") String strPedido, @QueryParam("dataCadastro") String strDataCadastro) {
		String strParametros = "Pedido = '" + strPedido + "' | Data = '" + strDataCadastro + "'";
		System.out.println(strParametros);
		

		return new PedidosController().listarPedidos(strPedido, strDataCadastro);
	}
	
	
	
	@POST
	@Path("/adicionar")
	//@Consumes("application/xml")
	//@Consumes("application/json")
	@Consumes({"application/xml", "application/json"})
	@Produces("text/plain")
	public String adicionaPedido(List<Pedidos> pedidos) {
		if (pedidos.size() < 1){
			return "Deve conter ao menos 1 Pedido!";
		}else if (pedidos.size() > 10){
			return "Serviço não aceita mais do que 10 Pedidos!";
		}else{
			return new PedidosController().adiciona(pedidos);
		}
	}
	
	
	

}
