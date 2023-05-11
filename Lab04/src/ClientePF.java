import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class ClientePF extends Cliente {
    private String cpf ;
    private String genero ;
    private LocalDate dataLicenca;
    private LocalDate dataNascimento;
    private String classeEconomica ;
    private String educacao ;


    public ClientePF(
    String nome, String endereco, ArrayList<Veiculo> veiculosDoCliente , String cpf, 
    String genero, LocalDate dataLicenca, LocalDate dataNascimento, String classeEconomica, 
    String educacao) {  
        super(nome, endereco, veiculosDoCliente, 0);
        this.cpf = cpf;
        this.genero = genero;
        this.dataLicenca = dataLicenca;
        this.dataNascimento = dataNascimento;
        this.classeEconomica = classeEconomica;
        this.educacao = educacao;
        this.setScore(calculaScore());
    }


    @Override
    public String toString() {
        String str= "Nome: " + getNome() + 
        "\nData de nascimento: " + getDataNascimento() +
        "\nCPF: " + getCpf() +  //
        "\nEndereço: " + getEndereco() +
        "\nData da licença: " + getDataLicenca() +
        "\nGênero: " + getGenero() +
        "\nEducação: " + getEducacao() +
        "\nPreço do seguro: " + calculaScore() +
        
        "\nClasse econômica: " + getClasseEconomica() +
        //itero a impressão dos veículos
        "\n\nPOSSUI O TOTAL DE " + getVeiculosDoCliente().size()+" CARRO(S)\n";
        for(int i = 0; getVeiculosDoCliente().size() > i; ++i){
            str += getVeiculosDoCliente().get(i).toString() + '\n';
        }

        return str;
    }

    @Override
    public double calculaScore(){
        // Calculo o score baseado na formula
        double idade = (double)Period.between(dataNascimento , LocalDate.now()).getYears();
        double quantidadeDeCarros = (double)getVeiculosDoCliente().size();
        double fatorIdade;

        if(30 >= idade){
            fatorIdade =  CalcSeguro.FATOR_18_30.getValor();
        }else if(60 >= idade){
            fatorIdade =  CalcSeguro.FATOR_30_60.getValor();
        }else{
            fatorIdade =  CalcSeguro.FATOR_60_90.getValor();
        }

        double resposta = fatorIdade;
        resposta *= CalcSeguro.VALOR_BASE.getValor();
        resposta *= quantidadeDeCarros;
        
        // Atualizo no final e retorno o score
        setScore(resposta);
        return resposta;
    }


    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getGenero() {
        return this.genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getDataLicenca() {
        return this.dataLicenca;
    }

    public void setDataLicenca(LocalDate dataLicenca) {
        this.dataLicenca = dataLicenca;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getClasseEconomica() {
        return this.classeEconomica;
    }

    public void setClasseEconomica(String classeEconomica) {
        this.classeEconomica = classeEconomica;
    }

    public String getEducacao() {
        return this.educacao;
    }

    public void setEducacao(String educacao) {
        this.educacao = educacao;
    }
}