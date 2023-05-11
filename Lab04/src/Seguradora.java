import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Seguradora {
    private String nome ;
    private String telefone ;
    private String email ;
    private String endereco ;
    private ArrayList <Cliente> clientes;
    private ArrayList <Sinistro> sinistros;

    //Construtor

    public Seguradora (
    String nome , String telefone , String email , String endereco , 
    ArrayList <Cliente> clientes, ArrayList <Sinistro> sinistros) {
        this.nome = nome ;
        this.telefone = telefone ;
        this.email = email ;
        this.endereco = endereco ;
        this.clientes = clientes ;
        this.sinistros = sinistros ;
    }

    public void listarClientes(int operador){
        if(operador == 1){
            // PESSOA JURÍDICA

            // Pego o número de pessoas jurídicas cadastradas no total
            int totalClientesPF = 0;
            for(int i = 0; this.clientes.size() > i; ++i){
                if(clientes.get(i) instanceof ClientePJ) totalClientesPF++;
            }

            System.out.println("\nHá "+totalClientesPF+" pessoa(s) jurídica(s) com cadastro em "+this.nome);

            // Sigo na lista de clientes, caso for uma instância de uma pessoa jurídica 
            // eu imprimo seu toString()
            int idx = 1;
            for(int i = 0; this.clientes.size() > i; ++i){
                if(clientes.get(i) instanceof ClientePJ){
                    System.out.println("\n(*) CLIENTE "+idx+": ");idx++;
                    System.out.println(clientes.get(i).toString());
                }
            }
        }else{
            // PESSOA FÍSICA

            // Pego o número de pessoas jurídicas cadastradas no total e imprimo em seguida
            int totalClientesPF = 0;
            for(int i = 0; this.clientes.size() > i; ++i){
                if(clientes.get(i) instanceof ClientePF) totalClientesPF++;
            }

            System.out.println("\nHá "+totalClientesPF+" pessoa(s) física(s) com cadastro em "+this.nome);

            // Sigo na lista de clientes, caso for uma instância de uma pessoa jurídica 
            // eu imprimo seu toString()
            int idx = 1;
            for(int i = 0; this.clientes.size() > i; ++i){
                if(clientes.get(i) instanceof ClientePF){
                    System.out.println("\n(*)CLIENTE "+idx+": ");idx++;
                    System.out.println(clientes.get(i).toString());
                }
            }
        }
    }

    public Cliente encontrarCliente(String documento){
        // Crio uam string que contenha apenas os dígitos numéricos do documento inserido
        String documentoApenasNumeros = documento.replaceAll("[^0-9]", "");

        if(documentoApenasNumeros.length() == 11){
            // Caso tenha tamanho 11, o documento inserido é um CPF
            // Percorro todos os clientes que sejam pessoas físicas e procuro um com esse mesmo CPF
            for(int i = 0; this.clientes.size() > i; ++i){
                if(this.clientes.get(i) instanceof ClientePF){
                    String cpf = ((ClientePF)this.clientes.get(i)).getCpf();
                    String cpfApenasNumeros = cpf.replaceAll("[^0-9]", "");
                    // Caso tenha esse CPF, eu retorno o cliente encontrado
                    if(documentoApenasNumeros.compareTo(cpfApenasNumeros) == 0){
                        return this.clientes.get(i);
                    }
                }   
            }
        }else if(documentoApenasNumeros.length() == 14){
            // Caso tenha tamanho 14, o documento inserido é um CNPJ
            // Percorro todos os clientes que sejam pessoas jurídicas e procuro um com esse mesmo CNPJ
            for(int i = 0; this.clientes.size() > i; ++i){
                if(this.clientes.get(i) instanceof ClientePJ){
                    String cnpj = ((ClientePJ)this.clientes.get(i)).getCnpj();
                    String cpfApenasNumeros = cnpj.replaceAll("[^0-9]", "");
                    // Caso tenha esse CNPJ, eu retorno o cliente encontrado
                    if(documentoApenasNumeros.compareTo(cpfApenasNumeros) == 0){
                        return this.clientes.get(i);
                    }
                }   
            }
        }
        
        // Retorno nulo caso não encontrar cliente algum
        return null;
    }    
    
    public boolean cadastrarCliente(Cliente cliente){
        this.clientes.add(cliente);
        return true;
    }
    
    public boolean removerCliente(String documento){
        // Procuro o cliente na lista a partir do seu documento (CPF ou CNPJ)

        Cliente cliente = encontrarCliente(documento);
        if(cliente == null){
            //cliente não encontrado
            System.out.println("> CLIENTE NÃO ENCONTRADO");
            return false;
        }
         
        String toStringAlvo = cliente.toString();

        // Procuro todos os sinistros com o cliente envolvido e apago
        for(int i = 0; this.sinistros.size() > i; ++i){
            if(toStringAlvo.compareTo(this.sinistros.get(i).getCliente().toString()) == 0){
                // Deleto o sinistro caso os toString() forem iguais
                this.sinistros.remove(i);
            }
        }

        // Procuro o cliente na lista de cliente e deleto ele assim que encontrado, em seguida encerro a função
        for(int i = 0; this.clientes.size() > i; ++i){
            if(toStringAlvo.equals(this.clientes.get(i).toString())){
                if(cliente instanceof ClientePF){
                    System.out.println("\nO cliente "+cliente.getNome()+" do CPF "+((ClientePF)cliente).getCpf()+" foi removido.");
                }else if(cliente instanceof ClientePJ){
                    System.out.println("\nO cliente "+cliente.getNome()+" do CNPJ "+((ClientePJ)cliente).getCnpj()+" foi removido.");
                }
                // Apago os carros e o cliente
                cliente.getVeiculosDoCliente().clear();
                this.clientes.remove(i);
                return true;
            }
        }
        
        return false;
    }
 
    public boolean gerarSinistro(Scanner scanner){
        String endereco, documento, placaVeiculo;

        System.out.println("\nInsira as informaões do sinistro "); 

        System.out.print("Endereço: "); 
        endereco = scanner.nextLine();

        System.out.print("Documento do Cliente: "); 
        documento = scanner.nextLine();

        // Busco cliente
        Cliente cliente = encontrarCliente(documento);
        
        // Caso o cliente não exista eu encerro a função 
        if(cliente == null){
            System.out.println("> DOCUMENTO INVÁLIDO, ABORTANDO GERAÇÃO DE SINISTRO");
            return false;
        }

        // Busco veículo pela sua placa dente os veículos do cliente
        System.out.print("Placa do veículo do cliente: "); 
        placaVeiculo = scanner.nextLine();

        // Caso o veículo não exista eu encerro a função 
        Veiculo veiculo = cliente.encontrarVeiculo(placaVeiculo);
        if(veiculo == null){
            System.out.println("> PLACA INVÁLIDA, ABORTANDO GERAÇÃO DE SINISTRO");
            return false;
        }

        // Gero o sinistro com as informações dadas e imprimo seu id para o usuário
        this.sinistros.add(new Sinistro(endereco, LocalDate.now(), this, veiculo, cliente));

        int id = this.sinistros.get(this.sinistros.size()-1).getId();
        System.out.println("\nSinistro gerado com sucesso.");
        System.out.println("id associado ao sinsitro: " + id);

        return true;
    }
    
    public Boolean vizualizarSinistro(String documentoCliente, LocalDate data){
        // Procuro cliente que tenha o documento inserido
        Cliente cliente = encontrarCliente(documentoCliente);
        if(cliente == null){
            System.out.println("\n> CLIENTE NÃO ENCONTRADO");
            return false;
        }

        // Procuro um sinistro que contenha o cliente ao comparar o toString( so cliente)
        for(int i = 0; this.sinistros.size() > i; ++i){
            if(cliente.toString().compareTo(this.sinistros.get(i).getCliente().toString()) == 0 &&
            data.isEqual(sinistros.get(i).getData())){
                // Caso encontre, retorno o toString() do sinistro e encerro a função
                System.out.println("\n"+this.sinistros.get(i).toString());
                return true;
            }
        }

        // Caso não encontre encerro a função com mensagem de erro
        System.out.println("Sem sinistros envolvendo "+cliente.getNome()+" no dia "+ data+"\n");
        return true;
    }

    public Boolean removerSinistro(String documentoCliente, LocalDate data){
        // Procuro cliente que tenha o documento inserido
        Cliente cliente = encontrarCliente(documentoCliente);
        if(cliente == null){
            System.out.println("> CLIENTE NÃO ENCONTRADO");
            return false;
        }

        // Procuro um sinistro que contenha o cliente ao comparar o toString( so cliente)
        for(int i = 0; this.sinistros.size() > i; ++i){
            if(cliente.toString().compareTo(this.sinistros.get(i).getCliente().toString()) == 0 &&
            data.isEqual(sinistros.get(i).getData())){
                // Caso encontre, retorno o toString() do sinistro e encerro a função
                System.out.println("\n\nO sinistro de id "+sinistros.get(i).getId()+" foi removido");
                this.sinistros.remove(i);
                return true;
            }
        }

        // Caso não encontre encerro a função com mensagem de erro
        System.out.println("Sem sinistros envolvendo "+cliente.getNome()+"\n");
        return true;
    }

    public Boolean removerVeiculo(String documentoCliente, String placa){
        // Procuro cliente que tenha o documento inserido
        Cliente cliente = encontrarCliente(documentoCliente);
        if(cliente == null){
            System.out.println("> CLIENTE NÃO ENCONTRADO");
            return false;
        }

        // Procuro o carro na lista de veiculos no cliente
        for(int i = 0; cliente.getVeiculosDoCliente().size() > i; ++i){
            if(cliente.getVeiculosDoCliente().get(i).getPlaca().equals(placa)){
                // Caso encontre, retorno a placa do veiculo e encerro a função
                System.out.println("\n\nO carro de placa "+cliente.getVeiculosDoCliente().get(i).getPlaca()+" foi removido");
                for(int j = this.sinistros.size()-1; j >= 0; --j){
                    if(this.sinistros.get(j).getVeiculo().getPlaca().equals(placa)){
                        this.sinistros.remove(j);
                    }
                }
                cliente.getVeiculosDoCliente().remove(i);
                return true;
            }
        }

        // Caso não encontre encerro a função com mensagem de erro
        System.out.println("Sem veículo de placa "+placa+" no nome de "+cliente.getNome()+"\n");
        return true;
    }

    public void listarSinistros(){
        // Listo todos os sinistros da seguradora
        System.out.println("Há "+sinistros.size()+" sinistro(s) registrado(s)");
        for(int i = 0; this.sinistros.size() > i; ++i){
            System.out.println("\n-> Sinistro "+(i+1)+": \n"+this.sinistros.get(i).toString());
        }
    }

    public void listarVeiculos(){
        // Somo os veiculos totais dos clientes e imprimo o total de veiculos na seguradora
        int totalDeVeiculos = 0;
        for(int i = 0; getClientes().size() > i; ++i){
            Cliente cliente = getClientes().get(i);
            totalDeVeiculos += cliente.getVeiculosDoCliente().size();
        }
        System.out.println("Há "+totalDeVeiculos+" veículo(s) registrado(s) em "+this.nome+"\n");

        // Imprimo as informações dos veículos junto o nome do dono
        for(int i = 0; getClientes().size() > i; ++i){
            Cliente cliente = getClientes().get(i);
            for(int j = 0; cliente.getVeiculosDoCliente().size() > j; ++j){
                System.out.println(cliente.getVeiculosDoCliente().get(j).toString()+" | DONO : "+cliente.getNome());
            }
        }
    }
    
    public double calcularPrecoSeguroCliente(String documentoCliente){
        // Procuro o cliente na lista a partir do seu documento (CPF ou CNPJ)
        Cliente cliente = encontrarCliente(documentoCliente);

        if(cliente == null){
            //cliente não encontrado
            System.out.println("> CLIENTE NÃO ENCONTRADO");
            return -1;
        }

        double resposta;

        // Calculo sue score 
        if(cliente instanceof ClientePF){
            resposta = ((ClientePF)cliente).calculaScore();
        }else{
            resposta = ((ClientePJ)cliente).calculaScore();
        }
        
        cliente.setScore(resposta);

        // Procuro o total de sinistros para multiplicar em seguida pelos scores
        double k = 1;

        for(int i = 0; this.sinistros.size() > i; ++i){
            if(sinistros.get(i).getCliente().toString().equals(cliente.toString())){
                k++;
            }
        }


        return k*resposta;
    }

    public double calcularReceita(){
        double resposta = 0;

        // Somo todos os preços dos valores dos seguros dos clientes e somo em seguida para calcular a receita
        for(int i = 0; this.getClientes().size() > i; ++i){
            if(this.getClientes().get(i) instanceof ClientePF){
                resposta += this.calcularPrecoSeguroCliente(((ClientePF)this.getClientes().get(i)).getCpf());
            }else{
                resposta += this.calcularPrecoSeguroCliente(((ClientePJ)this.getClientes().get(i)).getCnpj());
            }
            
        }

        return resposta;
    }

    public boolean transferirSeguro(Cliente clienteDAR, Cliente clienteRECEBE){
        // Adiciono todos os carros do cliente que quer dar os carros para o que recebe
        for(int i = 0; clienteDAR.getVeiculosDoCliente().size() > i; i++){
            clienteRECEBE.getVeiculosDoCliente().add(clienteDAR.getVeiculosDoCliente().get(i));
        }

        // Removo todos os carros do cliente que dar o seguro
        clienteDAR.getVeiculosDoCliente().clear();

       

        // Atualizo os preços dos seguros de ambos clientes

        if(clienteDAR instanceof ClientePF){
            this.calcularPrecoSeguroCliente(((ClientePF)clienteDAR).getCpf());
        }else{
            this.calcularPrecoSeguroCliente(((ClientePJ)clienteDAR).getCnpj());
        }

        if(clienteRECEBE instanceof ClientePF){
            this.calcularPrecoSeguroCliente(((ClientePF)clienteRECEBE).getCpf());
        }else{
            this.calcularPrecoSeguroCliente(((ClientePJ)clienteRECEBE).getCnpj());
        }
        
        return true;
    }
    
    // Getters e setters

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

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public ArrayList<Cliente> getClientes() {
        return this.clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public ArrayList<Sinistro> getSinistros() {
        return this.sinistros;
    }

    public void setSinistros(ArrayList<Sinistro> sinistros) {
        this.sinistros = sinistros;
    }

}