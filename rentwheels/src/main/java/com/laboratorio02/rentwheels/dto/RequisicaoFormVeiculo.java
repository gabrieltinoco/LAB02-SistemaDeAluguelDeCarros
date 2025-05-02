package com.laboratorio02.rentwheels.dto;

import com.laboratorio02.rentwheels.models.Veiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RequisicaoFormVeiculo {

    @NotBlank
    @NotNull
    private String modelo;
    @NotBlank
    private String marca;
    @NotBlank
    private String ano;
    @NotBlank
    private String placa;

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Veiculo toVeiculo() {
        Veiculo veiculo = new Veiculo();
        veiculo.setModelo(this.modelo);
        veiculo.setMarca(this.marca);
        veiculo.setAno(this.ano);
        veiculo.setPlaca(this.placa);

        return veiculo;
    }

    public Veiculo toVeiculo(Veiculo veiculo) {

        veiculo.setModelo(this.modelo);
        veiculo.setMarca(this.marca);
        veiculo.setAno(this.ano);
        veiculo.setPlaca(this.placa);

        return veiculo;
    }

    public void fromVeiculo(Veiculo veiculo) {
        this.modelo = veiculo.getModelo();
        this.marca = veiculo.getMarca();
        this.ano = veiculo.getAno();
        this.placa = veiculo.getPlaca();
    }

    public String toString() {
        return "RequisicaoFormVeiculo{" +
                "modelo='" + modelo + '\'' +
                ", marca='" + marca + '\'' +
                ", ano='" + ano + '\'' +
                ", placa='" + placa + '\'' +
                '}';
    }
}
