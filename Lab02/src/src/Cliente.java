public class Cliente {
    private String nome ;
    private String cpf ;
    private String dataNascimento ;
    private int idade ;
    private String endereco ;

    //Construtor
    
    public Cliente ( String nome , String cpf , String dataNascimento , int idade, String endereco ) {
        this . nome = nome ;
        this . cpf = cpf ;
        this . dataNascimento = dataNascimento ;
        this . idade = idade;
        this . endereco = endereco ;
    }

    // Checkar cpf

    public boolean validarCPF(){
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

    // Função toString

    public String toString(){
        // Concateno as várias linhas em uma string vazia
        String retornar = "\n";
        retornar += "NOME: " + nome + "\n";
        retornar += "CPF: " + cpf + "\n";
        retornar += "DATA NASCIMENTO: " + dataNascimento + "\n";
        retornar += "IDADE: " + idade + "\n";
        retornar += "ENDEREÇO: " + endereco + "\n";
        return retornar;
    }

    // Getters e setters

    public String getNome () {
        return nome ;
    }

    public String getCpf () {
        return cpf ;
    }

    public String getDataNascimento () {
        return dataNascimento ;
    }

    public int getIdade () {
        return idade ;
    }

     public String getEndereco () {
        return endereco ;
    }

    public void setNome ( String nome ) {
        this.nome = nome ;
    }
    
    public void setCpf ( String cpf ) {
        this.cpf = cpf ;
    }

    public void setDataNascimento ( String dataNascimento ) {
        this.dataNascimento = dataNascimento ;
    }

    public void setIdade ( int idade ) {
        this.idade = idade ;
    }

    public void setEndereco ( String endereco ) {
        this.endereco = endereco ;
    }
}