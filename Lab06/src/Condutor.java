//import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;


public class Condutor {
    private final String cpf ;
    private String nome ;   
    private String telefone ;
    private String endereco ;
    private String email ;
    private LocalDate dataNascimento ;
    private ArrayList <Sinistro> listaSinistros;

    //Construtor

    public Condutor(String cpf, String nome, String telefone, String endereco, String email, 
    LocalDate dataNascimento, ArrayList<Sinistro> listaSinistros) {
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.listaSinistros = listaSinistros;
    }
    

    // Função toString

    @Override
    public String toString() {
        String str =  "Nome: " + this.nome + 
        "\nCPF: " + this.cpf +
        "\nData nascimento: " + this.dataNascimento +
        "\nTeleone: " + this.telefone +
        "\nEndereço: " + this.endereco +
        "\nEmail: " + this.email+
        "\nTotal de sinistros: " + this.listaSinistros.size();

        return str;
    }

    // Funções de administrar sinistros

    public boolean adicionarSinistro(Sinistro sinistro){
        this.listaSinistros.add(sinistro);
        return true;
    }

    public boolean removerSinistro(int idSinistro){
        for(int i = 0; getListaSinistros().size() > i; ++i){
            if(getListaSinistros().get(i).getId() == idSinistro){
                getListaSinistros().remove(i);
                return true;
            }
        }
        return false;
    }

    // Getters e setters

    public String getCpf() {
        return this.cpf;
    }


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

    public String getEndereco() {
        return this.endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public ArrayList<Sinistro> getListaSinistros() {
        return this.listaSinistros;
    }

    public void setListaSinistros(ArrayList<Sinistro> listaSinistros) {
        this.listaSinistros = listaSinistros;
    }

}