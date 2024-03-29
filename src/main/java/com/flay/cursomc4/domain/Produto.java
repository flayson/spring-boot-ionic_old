package com.flay.cursomc4.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Produto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Double valor;
	//Retirou o  @JsonManagedReference //omite a lista de categorias, pois já foi feita consulta em categorias
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "PRODUTO_CATEGORIA",
	joinColumns = @JoinColumn(name="produto_id"), 
	inverseJoinColumns = @JoinColumn(name= "categoria_id")) //produto_id é o nome da chave estrangeira da tabela
	
	private List<Categoria> categorias = new ArrayList<>();
	@JsonIgnore //ignora a lista de produto na serealização
	@OneToMany(mappedBy="id.produto")
	private Set<ItemPedido> itens = new HashSet<>();//garante que não tenha itens pedidos repetidos.

	public Produto() {

	}

	public Produto(Integer id, String nome, Double valor) {
		super();
		this.id = id;
		this.nome = nome;
		this.valor = valor;
	}
	@JsonIgnore //não permite a serealização, sem esse comando ocorre referência cíclica.
	public List<Pedido> getPedidos(){ //tudo que começa com get automaticamente é serealizado, neste caso tem que ignorar. 
		List<Pedido> lista = new ArrayList<>();
		for(ItemPedido item : getItens()) {
			lista.add(item.getPedido());
		}
		return lista;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

}
