import java.time.LocalDate;
import java.util.ArrayList;

public class ClientePJ extends Cliente {
    private String cnpj ;
    private LocalDate dataFundacao ;

    public ClientePJ (String nome , String endereco , 
    ArrayList <Veiculo> veiculosDoCliente,  String cnpj , LocalDate dataFundacao) 
        {
        super(nome, endereco, veiculosDoCliente);
        this . cnpj = cnpj ;
        this . dataFundacao = dataFundacao ;
    }

    @Override
    public String toString() {
        String str =  "Nome: " + getNome() + 
            "\nCNPJ: " + getCnpj() + 
            "\nEndereço: " + getEndereco()+
            "\nData de fundação: " + getDataFundacao() + '\n';  //
            
        //itero a impressão dos veículos
        str += "\nPOSSUI O TOTAL DE " + getVeiculosDoCliente().size()+" CARRO(S)\n";
        for(int i = 0; getVeiculosDoCliente().size() > i; ++i){
            str += getVeiculosDoCliente().get(i).toString() + '\n';
        }
        return str;
    }

    public boolean validarCnpj(){
        // Retiro todos os caracteres não unitários
        String newCnpj = cnpj.replaceAll("[^0-9]", "");

        // Confiro se há 14 dígitos
        if(newCnpj.length() != 14){
            return false;
        }

        // Confiro se todos os dígitos não são iguais

        boolean checkar_tudo_igual = true;
        for(int i = 1; newCnpj.length() > i; ++i){
            checkar_tudo_igual &= (newCnpj.charAt(0)==newCnpj.charAt(i)); 
        }

        if(checkar_tudo_igual) return false;

        // Testo a validade dos últimos dois dígitos do cnpj

        int digito1 = 0, digito2 = 0;

        int []chave = { 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        
        digito2 = 6 * (newCnpj.charAt(0)-'0');

        for(int i = 0; 11 >= i; ++i){
            digito1 += chave[i]*(newCnpj.charAt(i)-'0');
            digito2 += chave[i]*(newCnpj.charAt(i+1)-'0');
        }

        digito1 = (digito1%11==0||digito1%11==1)?0:11-digito1%11;
        digito2 = (digito2%11==0||digito2%11==1)?0:11-digito2%11;

        if(digito1 == newCnpj.charAt(12)-'0' &&
        digito2 == newCnpj.charAt(13)-'0'){
            return true;
        }

        return false;

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

}