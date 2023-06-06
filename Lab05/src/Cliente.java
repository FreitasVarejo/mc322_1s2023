import java.util.ArrayList;

public abstract class Cliente {
    private String nome ;
    private String telefone ;
    private String email ;
    private String endereco ;

    //Construtor

    public Cliente(String nome, String telefone, String email, String endereco) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }    

    // Função toString

    public abstract String toString();

    // Excluir veiculo

    public abstract Boolean excluirVeiculo(String placaVeiculo);

    // Getters e setters

    // CPF para cliente PF e CNPJ para cliente PJ
    public abstract String getDocumento();

    public abstract ArrayList<Veiculo> getListaVeiculos();


    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
}