//import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;


public abstract class Seguro {
    private final int id ;
    private LocalDate dataInicio ;
    private LocalDate dataFim ;
    private Seguradora seguradora;
    private ArrayList <Sinistro> listaSinistros;
    private ArrayList <Condutor> listaCondutores;
    private int valorMensal;

    private static int geradorId = 1;

    //Construtor

    public Seguro(LocalDate dataInicio, LocalDate dataFim, Seguradora seguradora, int valorMensal, 
    ArrayList <Condutor> listaCondutores) {
        this.id = geradorId; geradorId++;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.seguradora = seguradora;
        this.listaSinistros = new ArrayList<>();
        this.listaCondutores = listaCondutores;
        this.valorMensal = valorMensal;  
    }

    // Função toString

    public abstract String toString();

    // Editar condutores

    public Boolean autorizarCondutor(Condutor condutor){
        int idx = Validacao.indiceDoCondutorNaLista(condutor.getCpf(), getListaCondutores());
        if(idx != -1){
            System.out.println("\n>ERRO: Condutor do cpf " + condutor.getCpf() + " já foi autorizado nesse seguro.");
            return false;
        }else{
            this.listaCondutores.add(condutor);
            System.out.println("\nCondutor de cpf " + condutor.getCpf() + " foi autorizado no seguro de id " + this.id + " com sucesso.");
            return true;
        }
    }

    public Boolean listarCondutores(){
        int totalCondutores = getListaCondutores().size();

        System.out.println("\nHá "+totalCondutores+" condutores(s) registrados no seguro de ID"+getId());

        int contadorCondutor = 1;
        for(Condutor condutor:getListaCondutores()){
            System.out.println("\nCONDUTOR "+(contadorCondutor));
            contadorCondutor++;
            System.out.println(condutor.toString());
        }

        return true;
    }

    public Boolean desautorizarCondutor(String cpfCondutor){
        int idx = Validacao.indiceDoCondutorNaLista(cpfCondutor, getListaCondutores());

        if(idx == -1){
            System.out.println("\n>ERRO: Não há condutor do cpf " + cpfCondutor + " autorizado no seguro.");
            return false;
        }else{
            // Caso encontre o condutor, eu removo todos os sinistros associados a ele
            for(int j = getListaSinistros().size()-1; j >= 0; --j){
                Sinistro sinistro = getListaSinistros().get(j);
                if(Validacao.compararDocumentos(cpfCondutor,sinistro.getCondutor().getCpf())){
                    removerSinistro(sinistro.getId());
                }
            }

            // Enfim, removo o condutor
            getListaCondutores().remove(idx);
            System.out.println("\nCondutor do cpf " + cpfCondutor + " foi removido do seguro de ID " + this.id + " com sucesso.");
            return true;
        }
    }

    // Editar sinistros

    public Boolean gerarSinistro(String enderecoSinistro, String cpfCondutor){
        int idxCondutor = Validacao.indiceDoCondutorNaLista(cpfCondutor, getListaCondutores());

        if(idxCondutor != -1){
            Condutor condutor = getListaCondutores().get(idxCondutor);
            Sinistro sinistro = new Sinistro(enderecoSinistro, condutor, this);
            // Gero o sinistro com os dados fornecidos

            getListaSinistros().add(sinistro);
            // Adiciono sinistro na lista de sinistros da seguradora

            condutor.adicionarSinistro(sinistro);
            // Adiciono sinistro na lista de sinistros do condutor

            System.out.println("\nSinitro de ID " + sinistro.getId() + " foi gerado no seguro de ID " + this.id + " com sucesso.");
            return true;
        }else{
            System.out.println("\n>ERRO: Não há condutor do cpf " + cpfCondutor + " registrado no seguro.");
            return false;
        }
    }

    public Boolean listarSinistros(){
        int totalSinistros = getListaSinistros().size();

        System.out.println("\nHá "+totalSinistros+" sinistro(s) gerados no seguro de ID"+getId());

        int contadorSinistro = 1;
        for(Sinistro sinistro:getListaSinistros()){
            System.out.println("\nSINISTRO "+(contadorSinistro));
            contadorSinistro++;
            System.out.println(sinistro.toString());
        }

        return true;
    }

    public Boolean removerSinistro(int idSinistro){
        int idx = Validacao.indiceDeSinistroNaLista(idSinistro, this.listaSinistros);

        if(idx == -1){
            System.out.println("\n>ERRO: Não há sinitro de ID " + idSinistro + " gerado no seguro.");
            return false;
        }else{
            Sinistro sinistro = listaSinistros.get(idx);
            Condutor condutor = sinistro.getCondutor();

            condutor.removerSinistro(sinistro.getId());
            // Removo o sinistro removido da lista de sinistros do seu condutor associado

            System.out.println("\nSinistro de ID " + idSinistro + " foi excluido do seguro de ID " + this.id + " com sucesso.");
            this.listaSinistros.remove(idx);
            // Removo sinistro da lista de sinistros do seguro
            return true;
        }
    }

    // Método abstrato que retorna o valor do seguro
    public abstract int calcularValor();

    // Getters e setters

    public abstract Cliente getCliente();

    public int getId() {
        return this.id;
    }


    public LocalDate getDataInicio() {
        return this.dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return this.dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Seguradora getSeguradora() {
        return this.seguradora;
    }

    public void setSeguradora(Seguradora seguradora) {
        this.seguradora = seguradora;
    }

    public ArrayList<Sinistro> getListaSinistros() {
        return this.listaSinistros;
    }

    public void setListaSinistros(ArrayList<Sinistro> listaSinistros) {
        this.listaSinistros = listaSinistros;
    }

    public ArrayList<Condutor> getListaCondutores() {
        return this.listaCondutores;
    }

    public void setListaCondutores(ArrayList<Condutor> listaCondutores) {
        this.listaCondutores = listaCondutores;
    }

    public int getValorMensal(){
        this.valorMensal = calcularValor();
        return this.valorMensal;
    }

    public void setValorMensal(int valorMensal) {
        this.valorMensal = valorMensal;
    }
    
}