import java.time.LocalDate;

public class Sinistro {
    private int id ;
    private LocalDate data ;
    private String endereco ;
    private Condutor condutor ;
    private Seguro seguro;

    public static int contador_de_id = 1;

    //Construtor


    public Sinistro(String endereco, Condutor condutor, Seguro seguro){
        // ID do sinistro começa em 1 e vai indo de um em um conforme adicionamos novos
        this.id = contador_de_id; contador_de_id++;

        // Data registrada vai ser a do momento quando o sinistro foi registrado
        this.data = LocalDate.now();
        
        this.endereco = endereco;
        this.condutor = condutor;
        this.seguro = seguro;
    }
    

    // Função toString

    public String toString() {
        String str =  "ID: " + getId() +
        "\nCondutor (NOME): " + this.condutor.getNome() +
        "\nCondutor (CPF): " + this.condutor.getCpf() +
        "\nData: " + this.data +  
        "\nEndereço: " + this.endereco +
        "\nSeguro (ID): " + this.seguro.getId();

        return str;
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

    public Condutor getCondutor() {
        return this.condutor;
    }

    public void setCondutor(Condutor condutor) {
        this.condutor = condutor;
    }

    public Seguro getSeguro() {
        return this.seguro;
    }

    public void setSeguro(Seguro seguro) {
        this.seguro = seguro;
    }

}