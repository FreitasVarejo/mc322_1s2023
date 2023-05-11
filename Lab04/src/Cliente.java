//import java.util.Date;
import java.util.ArrayList;


public class Cliente {
    private String nome ;
    private String endereco ;
    private ArrayList <Veiculo> veiculosDoCliente;
    private double score;

    //Construtor


    public Cliente(String nome, String endereco, ArrayList<Veiculo> veiculosDoCliente, double score) {
        this.nome = nome;
        this.endereco = endereco;
        this.veiculosDoCliente = veiculosDoCliente;
        this.score = score;
    }    

    // Função toString

    @Override
    public String toString() {
        String str =  "Nome: " + getNome() + 
        "\nEndereço:'" + getEndereco()+ "\n";

        for(int i = 0; getVeiculosDoCliente().size() > i; ++i){
            str += getVeiculosDoCliente().get(i).toString();
        }

        return str;
    }

    public String toStringVeiculos(){
        String str = "";
        
        for(int i = 0; getVeiculosDoCliente().size() > i; ++i){
            str += getVeiculosDoCliente().get(i).toString()+"\n";
        }

        return str;
    }

    public double calculaScore(){
        return CalcSeguro.VALOR_BASE.getValor();
    }

    /*
     * Busco um veículo na lista de veículos do cliente a partir de sua placa de carro
     */
    public Veiculo encontrarVeiculo(String placaAlvo){
        for(int i = 0; this.veiculosDoCliente.size() > i; ++i){
            if(placaAlvo.compareTo(this.veiculosDoCliente.get(i).getPlaca())==0){
                return this.veiculosDoCliente.get(i);
            }
        }
        return null;
    }  

    // Getters e setters


    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public ArrayList<Veiculo> getVeiculosDoCliente() {
        return this.veiculosDoCliente;
    }

    public void setVeiculosDoCliente(ArrayList<Veiculo> veiculosDoCliente) {
        this.veiculosDoCliente = veiculosDoCliente;
    }

    public double getScore() {
        return this.score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}