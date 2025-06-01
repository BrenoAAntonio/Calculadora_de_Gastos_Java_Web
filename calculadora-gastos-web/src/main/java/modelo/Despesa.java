package modelo;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Despesa {
    private Integer id;
    private Usuario usuario;
    private Categoria categoria;
    private String descricao;
    private BigDecimal valor;
    private Date dataDespesa;
    private Timestamp dataCadastro;

    public Despesa() {
    }

    public Despesa(Integer id, Usuario usuario, Categoria categoria, String descricao, BigDecimal valor, Date dataDespesa, Timestamp dataCadastro) {
        this.id = id;
        this.usuario = usuario;
        this.categoria = categoria;
        this.descricao = descricao;
        this.valor = valor;
        this.dataDespesa = dataDespesa;
        this.dataCadastro = dataCadastro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getDataDespesa() {
        return dataDespesa;
    }

    public void setDataDespesa(Date dataDespesa) {
        this.dataDespesa = dataDespesa;
    }

    public Timestamp getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Timestamp dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}