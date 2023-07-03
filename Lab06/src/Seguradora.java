import java.util.ArrayList;

public class Seguradora {
    private final String cnpj ;
    private String nome ;
    private String telefone ;
    private String endereco ;
    private String email ;
    private ArrayList <Cliente> listaClientes;
    private ArrayList <Seguro> listaSeguros;

    //Construtor


    public Seguradora(String cnpj, String nome, String telefone, String endereco, String email, 
    ArrayList<Cliente> clientes, ArrayList<Seguro> seguros) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
        this.listaClientes = new ArrayList<>();
        this.listaSeguros = new ArrayList<>();
    }

    // Função toString

    public String toString() {
        String str = "Nome: " + getNome() +
                "\nCNPJ: " + getCnpj() +
                "\nEmail: " + getEmail() +
                "\nEndereço: " + getEndereco() +
                "\nTelefone: " + getTelefone() +
                "\nPossui " + getListaClientes().size() + " clientes(s)\n" + 
                "\nPossui " + getListaSeguros().size() + " seguros(s)\n";

        return str;
    }

    // Ler dados

    public Boolean lerDados(){
        try{
            ArrayList<Veiculo> listaVeiculos = ArquivoVeiculo.lerArquivo("lab06-seguradora_arquivos_v2/veiculos.csv");
            ArrayList<Frota> listaFrotas = ArquivoFrota.lerArquivo("lab06-seguradora_arquivos_v2/frotas.csv", listaVeiculos);

            ArrayList<ClientePJ> listaClientesPJ = ArquivoClientePJ.lerArquivo("lab06-seguradora_arquivos_v2/clientesPJ.csv", listaFrotas);
            ArrayList<ClientePF> listaClientesPF = ArquivoClientePF.lerArquivo("lab06-seguradora_arquivos_v2/clientesPF.csv", listaVeiculos);

            //ArrayList<Seguro> listaSeguros = ArquivoSeguro.lerArquivo("lab06-seguradora_arquivos_v2/seguro.csv", listaClientePFs, listaSeguradoras, listaSinistros, listaCondutores)
            for(ClientePF clientePF:listaClientesPF){
                cadastrarCliente(clientePF);
            }

            for(ClientePJ clientePJ:listaClientesPJ){
                cadastrarCliente(clientePJ);
            }

            return true;
        }catch(Exception e){
            System.out.println("Erro ao ler o arquivo " + e);
            return false;
        }
    }

    public Boolean gravarDados(){
        ArquivoVeiculo.gravarArquivo("lab06-saida/veiculos.csv",this);
        ArquivoFrota.gravarArquivo("lab06-saida/frotas.csv",this);
        ArquivoClientePF.gravarArquivo("lab06-saida/clientesPF.csv",this);
        ArquivoClientePJ.gravarArquivo("lab06-saida/clientesPJ.csv",this);
        ArquivoSeguro.gravarArquivo("lab06-saida/seguro.csv",this);
        return true;
    }

    // Funções de listagem

    public Boolean listarClientes(int operador){
        if(operador == 1){
            // PESSOA JURÍDICA
            
            int totalClientesPJ = 0;
            for(int i = 0; this.listaClientes.size() > i; ++i){
                if(listaClientes.get(i) instanceof ClientePJ) totalClientesPJ++;
            }

            System.out.println("\nHá "+totalClientesPJ+" pessoa(s) jurídica(s) com cadastro em "+this.nome);

            int idx = 1;
            for(int i = 0; this.listaClientes.size() > i; ++i){
                if(listaClientes.get(i) instanceof ClientePJ){
                    System.out.println("\nCLIENTE "+idx);idx++;
                    System.out.println(listaClientes.get(i).toString());
                }
            }
        }else{
            // PESSOA FÍSICA

            int totalClientesPF = 0;
            for(int i = 0; this.listaClientes.size() > i; ++i){
                if(listaClientes.get(i) instanceof ClientePF) totalClientesPF++;
            }

            System.out.println("\nHá "+totalClientesPF+" pessoa(s) física(s) com cadastro em "+this.nome);

            int idx = 1;
            for(int i = 0; this.listaClientes.size() > i; ++i){
                if(listaClientes.get(i) instanceof ClientePF){
                    System.out.println("\nCLIENTE "+idx); idx++;
                    System.out.println(listaClientes.get(i).toString());
                }
            }
        }
        return true;
    }


    public Boolean listarSeguros(int operador){
        if(operador == 1){
            // PESSOA JURÍDICA

            int totalSegurosPJ = 0;
            for(int i = 0; this.listaSeguros.size() > i; ++i){
                if(listaSeguros.get(i) instanceof SeguroPJ) totalSegurosPJ++;
            }

            System.out.println("\nHá "+totalSegurosPJ+" seguro(s) de pessoa jurídica com cadastro em "+this.nome);

            int idx = 1;
            for(int i = 0; this.listaSeguros.size() > i; ++i){
                if(listaSeguros.get(i) instanceof SeguroPJ){
                    System.out.println("\nSEGURO "+idx);idx++;
                    System.out.println(listaSeguros.get(i).toString());
                }
            }
        }else{
            // PESSOA FÍSICA

            int totalSegurosPF = 0;
            for(int i = 0; this.listaSeguros.size() > i; ++i){
                if(listaSeguros.get(i) instanceof SeguroPF) totalSegurosPF++;
            }

            System.out.println("\nHá "+totalSegurosPF+" seguro(s) de pessoa física com cadastro em "+this.nome);

            int idx = 1;
            for(int i = 0; this.listaSeguros.size() > i; ++i){
                if(listaSeguros.get(i) instanceof SeguroPF){
                    System.out.println("\nSEGURO "+idx); idx++;
                    System.out.println(listaSeguros.get(i).toString());
                }
            }
        }
        return true;
    }

    public Boolean listarSinistros(){
        int totalSinistros = 0;

        for(Seguro seguro:getListaSeguros()){
            totalSinistros += seguro.getListaSinistros().size();
        }

        System.out.println("\nHá "+totalSinistros+" sinistro(s) gerados em "+this.nome);

        int idx = 1;

        for(Seguro seguro:getListaSeguros()){
            for(Sinistro sinistro:seguro.getListaSinistros()){
                System.out.println("\nSINISTRO "+idx); idx++;
                System.out.println(sinistro.toString());
            }
        }

        return true;
    }

    public Boolean listarVeiculos(){
        int totalVeiculos = 0;

        for(Cliente cliente:getListaClientes()){
            totalVeiculos += cliente.getListaVeiculos().size();
        }

        System.out.println("\nHá "+totalVeiculos+" veículo(s) cadastrados em "+this.nome);

        for(Cliente cliente:getListaClientes()){
            System.out.println("\nVeículo(s) de "+cliente.getNome()+" (TOTAL DE "+cliente.getListaVeiculos().size()+"): ");
            // Função getListaVeiculos retorna uma lista com todos veículos, tanto para PF e PJ
            for(Veiculo veiculo:cliente.getListaVeiculos()){
                System.out.println(veiculo.toString());
            }
        }

        return true;
    }

    public Boolean listarFrotas(){
        int totalFrotas = 0;

        for(Cliente cliente:getListaClientes()){
            if(cliente instanceof ClientePJ){
                totalFrotas += ((ClientePJ)cliente).getListaFrotas().size();
            }
        }

        System.out.println("\nHá "+totalFrotas+" frotas(s) cadastradas em "+this.nome);

        for(Cliente cliente:getListaClientes()){
            if(cliente instanceof ClientePJ){
                ArrayList <Frota> listaFrotasCliente = ((ClientePJ)cliente).getListaFrotas();
                System.out.println("\nFrotas(s) de "+cliente.getNome()+" (TOTAL DE "+listaFrotasCliente.size()+"): ");
                for(Frota frota:listaFrotasCliente){
                    System.out.println("\n"+frota.toString());
                }
            }
        }

        return true;
    }

    public Boolean listarSinistrosPorCliente(String documentoCliente){
        int idx = Validacao.indiceDeClienteNaLista(documentoCliente, getListaClientes());

        if(idx == -1){
            // Caso não ache cliente
            System.out.println("\n>ERRO: Não há cliente com documento " + documentoCliente + " na seguradora "+ getNome() +".");
            return false;
        }

        Cliente cliente = getListaClientes().get(idx);
        ArrayList <Sinistro> sinistrosCliente = getSinistrosPorCliente(cliente);

        System.out.println("\nO cliente " + cliente.getNome() + " tem " + sinistrosCliente.size() + " sinistro(s) gerado(s).");
            
        idx = 1;
        for(Sinistro sinistro:sinistrosCliente){
            System.out.println("\nSINISTRO "+idx);idx++;
            System.out.println(sinistro.toString());
        }
    
        return true;
    }

    public Boolean listarSegurosPorCliente(String documentoCliente){
        int idx = Validacao.indiceDeClienteNaLista(documentoCliente, getListaClientes());

        if(idx == -1){
            // Caso não ache cliente
            System.out.println("\n>ERRO: Não há cliente com documento " + documentoCliente + " na seguradora "+ getNome() +".");
            return false;
        }

        Cliente cliente = getListaClientes().get(idx);
        ArrayList <Seguro> segurosCliente = getSegurosPorCliente(cliente);

        System.out.println("\nO cliente " + cliente.getNome() + " tem " + segurosCliente.size() + " seguro(s) gerado(s).");
            
        idx = 1;
        for(Seguro seguro:segurosCliente){
            System.out.println("\nSEGURO "+idx); idx++;
            System.out.println(seguro.toString());
        }
    
        return true;
    }

    public Boolean listarVeiculosPorCliente(String documentoCliente){
        int idx = Validacao.indiceDeClienteNaLista(documentoCliente, getListaClientes());

        if(idx == -1){
            // Caso não ache cliente
            System.out.println("\n>ERRO: Não há cliente com documento " + documentoCliente + " na seguradora "+ getNome() +".");
            return false;
        }

        Cliente cliente = getListaClientes().get(idx);
        ArrayList <Veiculo> listaVeiculos = cliente.getListaVeiculos();

        System.out.println("\nVeículo(s) do "+cliente.getNome()+" (TOTAL DE "+listaVeiculos.size()+"): ");
        for(Veiculo veiculo:listaVeiculos){
            System.out.println(veiculo.toString());
        }
    
        return true;
    }

    public Boolean listarFrotasPorCliente(String documentoCliente){
        int idx = Validacao.indiceDeClienteNaLista(documentoCliente, getListaClientes());

        if(idx == -1){
            // Caso não ache cliente
            System.out.println("\n>ERRO: Não há cliente com documento " + documentoCliente + " na seguradora "+ getNome() +".");
            return false;
        }else if(getListaClientes().get(idx) instanceof ClientePF){
            // Caso o cliente encontrado seja PF (sem frotas)
            System.out.println("\n>ERRO: O documento" + documentoCliente + " consta como um CPF.");
            return false;
        }

        Cliente cliente = getListaClientes().get(idx);
        ArrayList <Frota> listaFrotasCliente = ((ClientePJ)cliente).getListaFrotas();

        System.out.println("\nFrotas(s) de "+cliente.getNome()+" (TOTAL DE "+listaFrotasCliente.size()+"): ");
        for(Frota frota:listaFrotasCliente){
            System.out.println("\n"+frota.toString());
        }
    
        return true;
    }

    public Boolean listarSinistrosPorSeguro(int idSeguro){
        int idxSeguro = Validacao.indiceDeSeguroNaLista(idSeguro, getListaSeguros());
        
        if(idxSeguro == -1){
            // Caso não ache seguro
            System.out.println("\n>ERRO: Seguro de id" + idSeguro + " não foi encontrado.");
            return false;
        }else{
            Seguro seguro = getListaSeguros().get(idxSeguro);
            seguro.listarSinistros();
            return true;
        }
        
    }

    public Boolean listarCondutoresPorSeguro(int idSeguro){
        int idxSeguro = Validacao.indiceDeSeguroNaLista(idSeguro, getListaSeguros());
        
        if(idxSeguro == -1){
            // Caso não ache seguro
            System.out.println("\n>ERRO: Seguro de id" + idSeguro + " não foi encontrado.");
            return false;
        }else{
            Seguro seguro = getListaSeguros().get(idxSeguro);
            seguro.listarCondutores();
            return true;
        }
        
    }

    // Funções de geração
    
    public Boolean gerarSeguro(Seguro seguro){
        seguro.setSeguradora(this);
        this.getListaSeguros().add(seguro);
        return true;
    }

    public Boolean cadastrarCliente(Cliente cliente){
        this.getListaClientes().add(cliente);
        return true; 
    }

    // Funções de remoção

    public Boolean cancelarSeguro(int seguroID){
        int idx = Validacao.indiceDeSeguroNaLista(seguroID, getListaSeguros());
        if(idx == -1){
            // Caso não ache seguro
            System.out.println("\n>ERRO: Não há seguro de ID " + seguroID + " na seguradora "+ getNome() +".");
            return false;
        }

        this.getListaSeguros().remove(idx);
        System.out.println("\nSeguro de ID " + seguroID + " foi removido da seguradora " + getNome() + " com sucesso.");
        return true;
    }

    public Boolean removerCliente(String documentoCliente){
        int idx = Validacao.indiceDeClienteNaLista(documentoCliente, getListaClientes());
        if(idx == -1){
            // Caso não ache cliente
            System.out.println("\n>ERRO: Não há cliente com documento " + documentoCliente + " na seguradora "+ getNome() +".");
            return false;
        }

        Cliente cliente = getListaClientes().get(idx);

        if(cliente instanceof ClientePF){
            for(int i = getListaSeguros().size()-1; i >= 0; --i){
                Seguro seguro = getListaSeguros().get(i);
                if(seguro instanceof SeguroPF && ((SeguroPF)seguro).getClientePF().equals(cliente)){
                    cancelarSeguro(seguro.getId());
                }
            }
        }else if(cliente instanceof ClientePJ){
            for(int i = getListaSeguros().size()-1; i >= 0; --i){
                Seguro seguro = getListaSeguros().get(i);
                if(seguro instanceof SeguroPJ && ((SeguroPJ)seguro).getClientePJ().equals(cliente)){
                    cancelarSeguro(seguro.getId());
                }
            }
        }

        this.getListaClientes().remove(idx);

        System.out.println("\nCliente com documento " + documentoCliente + " foi removido da seguradora " + getNome() + " com sucesso.");
        return true;
    }

    public boolean removerCondutor(String cpfCondutor){
        // TODO
        int contradorDeRemovidos = 0;

        for(Seguro seguro:getListaSeguros()){
            int idxCondutor = Validacao.indiceDoCondutorNaLista(cpfCondutor, seguro.getListaCondutores());
            if(idxCondutor != -1){
                contradorDeRemovidos++;
                seguro.desautorizarCondutor(cpfCondutor);
            }
        }

        if(contradorDeRemovidos > 0){
            System.out.println("\nCondutor com documento " + cpfCondutor + " foi removido da seguradora " + getNome() + " com sucesso.");
            return true;
        }else{
            // Caso não ache condutor
            System.out.println("\n>ERRO: Não há condutor com cpf " + cpfCondutor + " na seguradora "+ getNome() +".");
            return false;
        }
    }

    public boolean removerVeiculo(Cliente cliente, String placaVeiculo){
        cliente.excluirVeiculo(placaVeiculo);

        if(cliente instanceof ClientePF){
            // Caso o cliente seja PF, é necessário cancelar também os seguros do veículo removido
            for(int i = getListaSeguros().size()-1; i >= 0; --i){
                Seguro seguro = getListaSeguros().get(i);
                if(seguro instanceof SeguroPF && ((SeguroPF)seguro).getVeiculo().getPlaca().equals(placaVeiculo)){
                    this.cancelarSeguro(seguro.getId());
                }
            }
        }

        return true;
    }

    
    public boolean removerFrota(Cliente cliente, int codeFrota){
        if(cliente instanceof ClientePF){
            // Caso o cliente seja PF (sem frota)
            return false;
        }
        
        ((ClientePJ)cliente).excluirFrota(codeFrota);

        for(int i = getListaSeguros().size()-1; i >= 0; --i){
            Seguro seguro = getListaSeguros().get(i);
            if(seguro instanceof SeguroPJ && ((SeguroPJ)seguro).getFrota().getCode() == codeFrota){
                this.cancelarSeguro(seguro.getId());
            }
        }
        
        return true;
    }


    // Funções de calcular receita

    public int calcularReceita(){
        int balancoTotal = 0;
        for(Seguro seguro:getListaSeguros()){
            balancoTotal += seguro.getValorMensal();
            // Lembrando que a função getValorMensal() atualiza o valor do seguro antes de retornar ele
        }
        return balancoTotal;
    }

    // Getters e setters

    public ArrayList<Seguro> getSegurosPorCliente(Cliente cliente){
        ArrayList<Seguro> segurosCliente = new ArrayList<>();

        if(cliente instanceof ClientePF){
            for(Seguro seguro:listaSeguros){
                if(seguro instanceof SeguroPF && ((SeguroPF)seguro).getClientePF().equals(cliente)) {
                    segurosCliente.add(seguro);
                }
            }
        }else{
            for(Seguro seguro:listaSeguros){
                if(seguro instanceof SeguroPJ && ((SeguroPJ)seguro).getClientePJ().equals(cliente)) {
                    segurosCliente.add(seguro);
                }
            }
        }
        
        return segurosCliente;
    }

    public ArrayList<Sinistro> getSinistrosPorCliente(Cliente cliente){
        ArrayList<Seguro> segurosCliente = getSegurosPorCliente(cliente);
        ArrayList<Sinistro> sinistrosCliente = new ArrayList<>();

        for(Seguro seguro:segurosCliente){
            for(Sinistro sinistro:seguro.getListaSinistros()){
                sinistrosCliente.add(sinistro);
            }
        }
        
        return sinistrosCliente;
    }

    public String getCnpj() {
        return this.cnpj;
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

    public ArrayList<Cliente> getListaClientes() {
        return this.listaClientes;
    }

    public void setListaClientes(ArrayList<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public ArrayList<Seguro> getListaSeguros() {
        return this.listaSeguros;
    }

    public void setListaSeguros(ArrayList<Seguro> listaSeguros) {
        this.listaSeguros = listaSeguros;
    }
    
}