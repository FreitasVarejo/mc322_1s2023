//import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.Period;


public class SeguroPF extends Seguro {
    private Veiculo veiculo;
    private ClientePF clientePF;

    //Construtor


    public SeguroPF(LocalDate dataInicio, LocalDate dataFim, Seguradora seguradora, 
    ArrayList <Condutor> listaCondutores, Veiculo veiculo, ClientePF clientePF) {
        super(dataInicio, dataFim, seguradora, 0, listaCondutores);
        this.veiculo = veiculo;
        this.clientePF = clientePF;
    }

    public SeguroPF(int id, LocalDate dataInicio, LocalDate dataFim, Seguradora seguradora, 
    ArrayList <Condutor> listaCondutores, Veiculo veiculo, ClientePF clientePF) {
        super(id, dataInicio, dataFim, seguradora, 0, listaCondutores);
        this.veiculo = veiculo;
        this.clientePF = clientePF;
    }

    // Função toString

    @Override
    public String toString() {
        String str =  "ID: " + this.getId() +
        "\nCliente: " + this.clientePF.getNome() +
        "\nVeículo: " + this.veiculo.toString() +
        "\nValor mensal: " + getValorMensal() + "R$"+// TODO
        "\nInicio do seguro: " + this.getDataInicio() +
        "\nExpiração do seguro: " + this.getDataFim() +
        "\nSeguradora: " + this.getSeguradora().getNome() +
        "\nTotal de sinistros: " + this.getListaSinistros().size() +
        "\nTotal de condutores: " + this.getListaCondutores().size();

        return str;
    }

    // Calcula valor do seguro

    public int calcularValor(){
        // Calculo o score baseado na formula dada no enunciado
        double idade = (double)Period.between(clientePF.getDataNascimento(), LocalDate.now()).getYears();
        double fatorIdade;

        if(30 >= idade){
            fatorIdade =  CalcSeguro.FATOR_18_30.getValor();
        }else if(60 >= idade){
            fatorIdade =  CalcSeguro.FATOR_30_60.getValor();
        }else{
            fatorIdade =  CalcSeguro.FATOR_60_90.getValor();
        }

        double quantidadeVeiculos = getClientePF().getListaVeiculos().size();
        double quantidadeSinistrosCliente = getSeguradora().getSinistrosPorCliente(getClientePF()).size();
        double quantidadeSinistrosCondutor = getListaSinistros().size();

        double resposta = CalcSeguro.VALOR_BASE.getValor() * fatorIdade;
        resposta *= (1.0 + 1.0/(2.0 + quantidadeVeiculos));
        resposta *= (2.0 + quantidadeSinistrosCliente/10.0);
        resposta *= (5.0 + quantidadeSinistrosCondutor/10.0);
        
        return (int)resposta;
    }

    // Getters e setters

    public Cliente getCliente(){
        return this.clientePF;
    }

    public Veiculo getVeiculo() {
        return this.veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public ClientePF getClientePF() {
        return this.clientePF;
    }

    public void setClientePF(ClientePF clientePF) {
        this.clientePF = clientePF;
    }
    
}