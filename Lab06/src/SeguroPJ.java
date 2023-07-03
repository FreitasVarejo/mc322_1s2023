import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;


public class SeguroPJ extends Seguro {
    private Frota frota;
    private ClientePJ clientePJ;

    //Construtor

    public SeguroPJ(LocalDate dataInicio, LocalDate dataFim, Seguradora seguradora, 
    ArrayList <Condutor> listaCondutores, Frota frota, ClientePJ clientePJ) {
        super(dataInicio, dataFim, seguradora, 0, listaCondutores);
        this.frota = frota;
        this.clientePJ = clientePJ;
    }

    public SeguroPJ(int id, LocalDate dataInicio, LocalDate dataFim, Seguradora seguradora, 
    ArrayList <Condutor> listaCondutores, Frota frota, ClientePJ clientePJ) {
        super(id, dataInicio, dataFim, seguradora, 0, listaCondutores);
        this.frota = frota;
        this.clientePJ = clientePJ;
    }

    // Função toString

    @Override
    public String toString() {
        String str =  "ID: " + this.getId() +
        "\nCliente: " + this.clientePJ.getNome() +
        "\nFrota (código): " + this.frota.getCode() +
        "\nValor mensal: " + getValorMensal() + " R$" +// TODO
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
        double quantidadeFunc = getListaCondutores().size();
        double quantidadeVeiculos = getFrota().getListaVeiculos().size();
        double anosPosFundacao = (double)Period.between(clientePJ.getDataFundacao(), LocalDate.now()).getYears();
        double quantidadeSinistrosCliente = getSeguradora().getSinistrosPorCliente(getClientePJ()).size();
        double quantidadeSinistrosCondutor = getListaSinistros().size();

        double resposta = CalcSeguro.VALOR_BASE.getValor();
        resposta *= (10.0 + quantidadeFunc/10.0);
        resposta *= (1.0 + 1.0/(quantidadeVeiculos + 2.0));
        resposta *= (1.0 + 1.0/(anosPosFundacao + 2.0));
        resposta *= (2.0 + quantidadeSinistrosCliente/10.0);
        resposta *= (5.0 + quantidadeSinistrosCondutor/10.0);

        return (int)resposta;
    }

    // Getters e setters

    public Cliente getCliente(){
        return this.clientePJ;
    }

    public Frota getFrota() {
        return this.frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    public ClientePJ getClientePJ() {
        return this.clientePJ;
    }

    public void setClientePJ(ClientePJ clientePJ) {
        this.clientePJ = clientePJ;
    }
    
}