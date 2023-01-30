package src.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;

public abstract class ContaBancaria {

    //#region Atributos
    protected String agencia;

    protected String conta;

    protected Integer digito;

    protected Double saldo;

    protected Date dataAbertura;

    protected ArrayList <Movimentacao> movimentacoes;

    protected Double VALOR_MINIMO_DEPOSITO = 10.0;
    //#endregion
    
    //#region Construtores
    public ContaBancaria(String agencia, String conta, Integer digito, Double saldoInicial) {
        this.agencia = agencia;
        this.conta = conta;
        this.digito = digito;
        this.saldo = saldoInicial;
        this.dataAbertura = new Date ();

        //Se não instanciar, vai dar uma exception de nullPointerException.
        this.movimentacoes = new ArrayList<Movimentacao>();

        // Primeira movimentação
        Movimentacao movimentacao = new Movimentacao("Abertura de conta", saldoInicial);

        // Salva movimentações dentro do Array
        // Inicia o extrato
        this.movimentacoes.add(movimentacao);

    }
    //#endregion

    //#region Getters e Setters
    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public Integer getDigito() {
        return digito;
    }

    public void setDigito(Integer digito) {
        this.digito = digito;
    }

    public Double getSaldo() {
        return saldo;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }
    //#endregion
 
    //#region Metodos

    public void depositar(Double valor) {
        
        //Verifica se o valor de deposito é menor que o valor minimo
        //Se for não pode acontecer deposito
        if (valor < VALOR_MINIMO_DEPOSITO) {
            throw new InputMismatchException("O valor mínimo de deposito é R$" + VALOR_MINIMO_DEPOSITO);
        }

        // Efetua o deposito somando o valor ao saldo.
        this.saldo += valor;

        // Aqui faço uma movimentação no extrato
        Movimentacao movimentacao = new Movimentacao("Deposito", valor);
        this.movimentacoes.add(movimentacao);
    }

    public Double sacar (Double valor) {

        // Verifica se o valor é maiorr que o saldo da conta.
        // Se for manda mensagem de saldo insuficiente.
        if (valor > this.saldo) {
            throw new InputMismatchException("O saldo é insuficiente");
        }

        // Aqui removemos o valor de saque do saldo atual.
        this.saldo -= valor;

        // Aqui faço uma movimentação no extrato
        Movimentacao movimentacao = new Movimentacao("Retira de valor", valor);
        this.movimentacoes.add(movimentacao);

        // Retorna o valor sacado ao usuário.
        return valor;
    }

    public void transferir (Double valor, ContaBancaria contaDestino) {

        // Efetua um saque na conta atual
        this.sacar(valor);

        // Efetua deposito na conta destino.
        contaDestino.depositar(valor);
    }
    //#endregion


    // Metodo abstrato obriga as classes que estão herdando de implementarem este método.
    public abstract void imprimirExtrato();
}
