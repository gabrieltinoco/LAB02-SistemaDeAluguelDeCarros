package com.laboratorio02.rentwheels.dto;

import com.laboratorio02.rentwheels.models.Cliente;

// É uma classe DTO (Data Transfer Object)
public class RequisicaoFormCliente {

    private String nome;
    private String email;
    private String senha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Cliente toCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome(this.nome);
        cliente.setEmail(this.email);
        cliente.setSenha(this.senha);
        return cliente;
    }

    public Cliente toCliente(Cliente cliente) {
        cliente.setNome(this.nome);
        cliente.setEmail(this.email);
        cliente.setSenha(this.senha);
        return cliente;
    }

    public void fromCliente(Cliente cliente) {
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        this.senha = cliente.getSenha();
    }

    @Override
    public String toString() {
        return "RequisicaoFormCliente{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
