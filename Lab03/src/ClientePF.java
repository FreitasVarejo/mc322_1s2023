import java.time.LocalDate;
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
        super(nome, endereco, veiculosDoCliente);
        this.cpf = cpf;
        this.genero = genero;
        this.dataLicenca = dataLicenca;
        this.dataNascimento = dataNascimento;
        this.classeEconomica = classeEconomica;
        this.educacao = educacao;
    }


    @Override
    public String toString() {
        String str= "Nome : " + getNome() + 
        "\nData de nascimento: " + getDataNascimento() +
        "\nCPF: " + getCpf() +  //
        "\nEndereço: " + getEndereco() +
        "\nData da licença: " + getDataLicenca() +
        "\nGênero: " + getGenero() +
        "\nEducação: " + getEducacao() +
        
        "\nClasse econômica: " + getClasseEconomica() +
        //itero a impressão dos veículos
        "\nPOSSUI O TOTAL DE " + getVeiculosDoCliente().size()+" CARRO(S)\n";
        for(int i = 0; getVeiculosDoCliente().size() > i; ++i){
            str += getVeiculosDoCliente().get(i).toString() + '\n';
        }

        return str;
    }

    public boolean validarCpf(){
        // Retiro todos os caracteres não unitários
        String newcpf = cpf.replaceAll("[^0-9]", "");

        // Confiro se há 11 dígitos
        if(newcpf.length() != 11){
            return false;
        }

        // Confiro se todos os dígitos não são iguais

        boolean checkar_tudo_igual = true;
        for(int i = 1; newcpf.length() > i; ++i){
            checkar_tudo_igual &= (newcpf.charAt(0)==newcpf.charAt(i)); 
        }

        if(checkar_tudo_igual) return false;

        // Testo a validade dos últimos dois dígitos do cpf

        int digito1 = 0, digito2 = 0;

        for(int i = 0; 9 > i; ++i){
            digito1 += (10-i)*(newcpf.charAt(i)-'0');
            digito2 += (10-i)*(newcpf.charAt(i+1)-'0');
        }

        digito1 = (digito1%11==0||digito1%11==1)?0:11-digito1%11;
        digito2 = (digito2%11==0||digito2%11==1)?0:11-digito2%11;

        if(digito1 == newcpf.charAt(9)-'0' &&
        digito2 == newcpf.charAt(10)-'0'){
            return true;
        }

        return false;

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