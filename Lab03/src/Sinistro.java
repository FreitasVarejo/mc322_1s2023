import java.time.LocalDate;

public class Sinistro {
    private int id ;
    private LocalDate data ;
    private String endereco ;
    private Seguradora seguradora;
    private Veiculo veiculo;
    private Cliente cliente;

    public static int contador_de_id = 1;

    //Construtor

    public Sinistro (String endereco , LocalDate data , Seguradora seguradora , Veiculo veiculo, Cliente cliente){
        this.id = contador_de_id ;
        // todo novo sinistro eu coloco seu id como sendo o posterior do ultim,o sinistro registrado
        contador_de_id++;
        this.data =  data ;
        this.endereco = endereco ;
        this.seguradora = seguradora ;
        this.veiculo = veiculo ;
        this.cliente = cliente ;
    }

    // Função toString

    public String toString() {
        return "Cliente: " + cliente.getNome() +
            "\nData: " + getData() + //  
            "\nEndereço: " + getEndereco() +
            "\nID: " + getId() + 
            "\nSeguradora: " + seguradora.getNome() +
            "\nVeículo envolvido: " +veiculo.toString();
    }

    // Getters e setters


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return this.data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Seguradora getSeguradora() {
        return this.seguradora;
    }

    public void setSeguradora(Seguradora seguradora) {
        this.seguradora = seguradora;
    }

    public Veiculo getVeiculo() {
        return this.veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }    

}