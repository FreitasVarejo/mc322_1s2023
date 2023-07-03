import java.time.LocalDate;
import java.util.ArrayList;

public class ClientePF extends Cliente {
    private final String cpf ;
    private String genero ;
    private String educacao ;
    private LocalDate dataNascimento;
    private ArrayList <Veiculo> listaVeiculos;
    

    public ClientePF(String nome, String telefone, String email, String endereco, String cpf,
    String genero, String educacao, LocalDate dataNascimento, ArrayList <Veiculo> listaVeiculos){
        super(nome, telefone, email, endereco);
        this.cpf = cpf;
        this.genero = genero;
        this.educacao = educacao;
        this.dataNascimento = dataNascimento;
        this.listaVeiculos = listaVeiculos;
    }

    // Função toString

    @Override
    public String toString() {
        String str= "Nome: " + getNome() + 
        "\nCPF: " + getCpf() + 
        "\nData de nascimento: " + getDataNascimento() +
        "\nEmail: " + getEmail() +
        "\nEndereço: " + getEndereco() +
        "\nTelefone: " + getTelefone() +
        "\nGênero: " + getGenero() +
        "\nEducação: " + getEducacao() +
        "\nPOSSUI O TOTAL DE " + getListaVeiculos().size()+" VEÍCULOS(S)\n";

        return str;
    }

    // Administrar veículos

    public Boolean adicionarVeiculo(Veiculo veiculo){
        if(Validacao.indiceDoVeiculoNaLista(veiculo.getPlaca(), this.listaVeiculos) != -1){
            System.out.println("\n>ERRO: O cliente " + this.getNome() + " já tem um veículo da placa " + veiculo.getPlaca() + ".");
            return false;
        }
        
        this.listaVeiculos.add(veiculo);
        System.out.println("\nVeículo de placa " + veiculo.getPlaca() + " foi cadastrado na conta com sucesso.");
        return true;
    }

    public Boolean excluirVeiculo(String placaCarro){
        int idx = Validacao.indiceDoVeiculoNaLista(placaCarro, this.listaVeiculos);

        if(idx == -1){
            System.out.println("\n>ERRO: O cliente não tem um veículo com a placa " + placaCarro + ".");
            return false;
        }else{
            this.listaVeiculos.remove(idx);
            System.out.println("\nVeículo de placa " + placaCarro + " foi removido da conta de  " + this.getNome() + " com sucesso.");
            return true;
        }
    }

    // getters e setters

    public String getDocumento(){
        return this.cpf;
    }


    public String getCpf() {
        return this.cpf;
    }


    public String getGenero() {
        return this.genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEducacao() {
        return this.educacao;
    }

    public void setEducacao(String educacao) {
        this.educacao = educacao;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public ArrayList<Veiculo> getListaVeiculos() {
        return this.listaVeiculos;
    }

    public void setListaVeiculos(ArrayList<Veiculo> listaVeiculos) {
        this.listaVeiculos = listaVeiculos;
    }
    
    
}