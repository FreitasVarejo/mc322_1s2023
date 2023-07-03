import java.time.LocalDate;
import java.util.ArrayList;

public class ClientePJ extends Cliente {
    private final String cnpj;
    private LocalDate dataFundacao;
    private ArrayList<Frota> listaFrotas;

    public ClientePJ(String nome, String telefone, String email, String endereco, String cnpj,
            LocalDate dataFundacao, ArrayList<Frota> listaFrotas) {
        super(nome, telefone, email, endereco);
        this.cnpj = cnpj;
        this.dataFundacao = dataFundacao;
        this.listaFrotas = listaFrotas;
    }

    // Função toString

    @Override
    public String toString() {
        String str = "Nome: " + getNome() +
                "\nCNPJ: " + getCnpj() +
                "\nEmail: " + getEmail() +
                "\nEndereço: " + getEndereco() +
                "\nTelefone: " + getTelefone() +
                "\nData de fundação: " + getDataFundacao() +
                "\nPossui " + getListaFrotas().size() + " frotas(s)\n";

        return str;
    }

    // Administrar frotas

    public Boolean cadastrarFrota(Frota frota){
        this.getListaFrotas().add(frota);
        System.out.println("\nFrota de código " + frota.getCode() + " foi cadastrada ao cliente " + this.getNome() + " com sucesso.");
        return true;
    }

    public Boolean adicionarVeiculo(int codeFrota, Veiculo veiculo) {
        int idx = Validacao.indiceDaFrotaNaLista(codeFrota, this.getListaFrotas());

        if(idx == -1) {
            System.out.println("\n>ERRO: Não há frota de código " + codeFrota + " cadastrada no cliente.");
            return false;
        }

        this.getListaFrotas().get(idx).adicionarVeiculo(veiculo);
        return true;
    }

    public Boolean excluirVeiculo(String placaVeiculo) {
        for(Frota frota:getListaFrotas()){
            int idxVeiculo = Validacao.indiceDoVeiculoNaLista(placaVeiculo, frota.getListaVeiculos());
            if(idxVeiculo != -1){
                frota.removerVeiculo(placaVeiculo);
                System.out.println("Veículo de placa " + placaVeiculo + " foi excluído com sucesso.");
                return true;
            }
        }

        System.out.println("\n>ERRO: Não há veículo de placa " + placaVeiculo + " cadastrado no cliente.");
        return false;
    }

    public Boolean excluirFrota(int codeFrota){
        int idx = Validacao.indiceDaFrotaNaLista(codeFrota, this.getListaFrotas());

        if(idx == -1) {
            System.out.println("\n>ERRO: Não há frota de código " + codeFrota + " cadastrada no cliente.");
            return false;
        }

        this.getListaFrotas().remove(idx);
        System.out.println("Frota de código " + codeFrota + " foi excluída com sucesso.");
        return true;
    }

    // getters e setters

    public ArrayList<Veiculo> getListaVeiculos() {
        ArrayList<Veiculo> listaVeiculos = new ArrayList<>();

        for(Frota frota:getListaFrotas()){
            for(Veiculo veiculo:frota.getListaVeiculos()){
                listaVeiculos.add(veiculo);
            }
        }
        return listaVeiculos;
    }

    public String getDocumento(){
        return this.cnpj;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public LocalDate getDataFundacao() {
        return this.dataFundacao;
    }

    public void setDataFundacao(LocalDate dataFundacao) {
        this.dataFundacao = dataFundacao;
    }

    public ArrayList<Frota> getListaFrotas() {
        return this.listaFrotas;
    }

    public void setListaFrotas(ArrayList<Frota> listaFrotas) {
        this.listaFrotas = listaFrotas;
    }

}