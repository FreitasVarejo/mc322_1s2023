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

    /*
     * Imprime todos os clientes do mesmo tipo
     */
    public void listarClientes(int operador){
        if(operador == 1){
            // PESSOA JURÍDICA

            // Pego o número de pessoas jurídicas cadastradas no total
            int totalClientesPF = 0;
            for(int i = 0; this.clientes.size() > i; ++i){
                if(clientes.get(i) instanceof ClientePJ) totalClientesPF++;
            }

            System.out.println("\nHá "+totalClientesPF+" pessoa(s) jurídica(s) com cadastro no sistema. \n");

            // Sigo na lista de clientes, caso for uma instância de uma pessoa jurídica 
            // eu imprimo seu toString()
            for(int i = 0; this.clientes.size() > i; ++i){
                if(clientes.get(i) instanceof ClientePJ){
                    System.out.println("CLIENTE "+(i+1)+": ");
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

            System.out.println("\nHá "+totalClientesPF+" pessoa(s) física(s) com cadastro no sistema. ");

            // Sigo na lista de clientes, caso for uma instância de uma pessoa jurídica 
            // eu imprimo seu toString()
            for(int i = 0; this.clientes.size() > i; ++i){
                if(clientes.get(i) instanceof ClientePF){
                    System.out.println("\n\nCLIENTE "+(i+1)+": ");
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
    
    /*
     * Insiro cliente na lista de clientes da seguradora
     */
    public boolean cadastrarCliente(Cliente cliente){
        this.clientes.add(cliente);
        return true;
    }
    
    /*
     * Removo um cliente e seus sinistros da seguradora
     */
    public boolean removerCliente(String documento){
        // Procuro o cliente na lista a partir do seu documento (CPF ou CNPJ)
        Cliente cliente = encontrarCliente(documento);
        if(cliente != null){
            // Caso que o cliente exista
            
            // Pego o toString() do cliente encontrado 
            String toStringAlvo = cliente.toString();

            // Comparo com os dos clientes associados a cada sinistro da seguradora
            for(int i = 0; this.sinistros.size() > i; ++i){
                if(toStringAlvo.compareTo(this.sinistros.get(i).getCliente().toString()) == 0){
                    // Deleto o sinistro caso os toString() forem iguais
                    this.sinistros.remove(i);
                }
            }

            // Procuro o cliente na lista de cliente e deleto ele assim que encontrado, em seguida encerro a função
            for(int i = 0; this.clientes.size() > i; ++i){
                if(toStringAlvo.compareTo(this.clientes.get(i).toString()) == 0){
                    if(cliente instanceof ClientePF){
                        System.out.println("\nO cliente "+cliente.getNome()+" do CPF "+((ClientePF)cliente).getCpf()+" foi removido.");
                    }else if(cliente instanceof ClientePJ){
                        System.out.println("\nO cliente "+cliente.getNome()+" do CNPJ "+((ClientePJ)cliente).getCnpj()+" foi removido.");
                    }
                    this.clientes.remove(i);
                }
            }
            
            return true;
        }

        //Caso cliente não encontrado
        System.out.println("> CLIENTE NÃO ENCONTRADO");
        return false;
    }

    /*
     * Gero sinistro a partir das informações lidas
     */
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
    
    public Boolean vizualizarSinistro(String documentoCliente){
        // Procuro cliente que tenha o documento inserido
        Cliente cliente = encontrarCliente(documentoCliente);
        if(cliente == null){
            System.out.println("> CLIENTE NÃO ENCONTRADO");
            return false;
        }

        // Procuro um sinistro que contenha o cliente ao comparar o toString( so cliente)
        for(int i = 0; this.sinistros.size() > i; ++i){
            if(cliente.toString().compareTo(this.sinistros.get(i).getCliente().toString()) == 0){
                // Caso encontre, retorno o toString() do sinistro e encerro a função
                System.out.println("\n"+this.sinistros.get(i).toString());
                return true;
            }
        }

        // Caso não encontre encerro a função com mensagem de erro
        System.out.println("Sem sinistros envolvendo "+cliente.getNome()+"\n");
        return false;
    }

    /*
     * Imprimo todos os sinistors contidos na lista de sinistros da seguradora
     */
    public void listarSinistros(){
        System.out.println("Há "+sinistros.size()+" sinistro(s) registrado(s)");
        for(int i = 0; this.sinistros.size() > i; ++i){
            System.out.println("\n-> Sinistro "+(i+1)+": \n"+this.sinistros.get(i).toString());
        }
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