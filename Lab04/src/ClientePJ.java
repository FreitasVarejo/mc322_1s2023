import java.time.LocalDate;
import java.util.ArrayList;

public class ClientePJ extends Cliente {
    private String cnpj ;
    private LocalDate dataFundacao ;
    private int qtdeFuncionarios;

    public ClientePJ (String nome , String endereco , 
    ArrayList <Veiculo> veiculosDoCliente, String cnpj , LocalDate dataFundacao, int qtdeFuncionarios) 
        {
        super(nome, endereco, veiculosDoCliente, 0);
        this . cnpj = cnpj ;
        this . dataFundacao = dataFundacao ;
        this . qtdeFuncionarios = qtdeFuncionarios;
        this.setScore(calculaScore());
    }

    @Override
    public String toString() {
        String str =  "Nome: " + getNome() + 
            "\nCNPJ: " + getCnpj() + 
            "\nEndereço: " + getEndereco()+
            "\nPreço do seguro: " + calculaScore() +
            "\nData de fundação: " + getDataFundacao() + '\n';  //
            
        //itero a impressão dos veículos
        str += "\nPOSSUI O TOTAL DE " + getVeiculosDoCliente().size()+" CARRO(S)\n";
        for(int i = 0; getVeiculosDoCliente().size() > i; ++i){
            str += getVeiculosDoCliente().get(i).toString() + '\n';
        }
        return str;
    }

    @Override
    public double calculaScore(){
        // Calculo o score baseado na formula
        double quantidadeDeCarros = (double)getVeiculosDoCliente().size();
        
        double resposta = CalcSeguro.VALOR_BASE.getValor();
        resposta *= (1 + (double)(qtdeFuncionarios)/100);
        resposta *= quantidadeDeCarros;
        
        this.setScore(resposta); 
        
        // Atualizo no final e retorno o score
        setScore(resposta);
        return resposta;
    }



    public String getCnpj() {
        return this.cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public LocalDate getDataFundacao() {
        return this.dataFundacao;
    }

    public void setDataFundacao(LocalDate dataFundacao) {
        this.dataFundacao = dataFundacao;
    }

    public int getQtdeFuncionarios() {
        return this.qtdeFuncionarios;
    }

    public void setQtdeFuncionarios(int qtdeFuncionarios) {
        this.qtdeFuncionarios = qtdeFuncionarios;
    }
    
}