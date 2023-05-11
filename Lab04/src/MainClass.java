import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainClass {
    //Scanner global que abro e fecho na main
    static Scanner scanner = new Scanner(System.in);
    static ArrayList <Seguradora> seguradoras = new ArrayList<>();

    public static boolean inserirSeguradora() {
        //Insiro as informações da seguradora e adiciono ela na lista de seguradoras
        String nome, telefone, email, endereço;

        System.out.println("\nInsira as informações da seguradora ");
        System.out.print("Nome: ");
        nome = scanner.nextLine();
        System.out.print("Telefone: ");
        telefone = scanner.nextLine();
        System.out.print("Email: ");
        email = scanner.nextLine();
        System.out.print("Endereço: ");
        endereço = scanner.nextLine();

        ArrayList<Cliente> clientes = new ArrayList<>();
        ArrayList<Sinistro> sinistros = new ArrayList<>();

        seguradoras.add(new Seguradora(nome, telefone, email, endereço, clientes, sinistros));

        return true;
    }


    static Seguradora escolherSeguradora(){
        // Caso tenha mais de uma seguradora eu escolho entre as seguradoras disponiveis
        if(seguradoras.size() == 1){
            return seguradoras.get(0);
        }

        System.out.println("\nQual seguradora deseja usar?");
        for(int i = 0; seguradoras.size() > i; ++i){
            System.out.println("["+(i+1)+"] + "+seguradoras.get(i).getNome());
        }
        int operator = Integer.parseInt(scanner.nextLine());

        if(seguradoras.size() >= operator && operator >= 1){
            return seguradoras.get(operator);
        }

        // Caso for inválido eu solicito denovo
        System.out.println("\n Índice inválido, tente novamente");
        return escolherSeguradora();
    }

    static Cliente escolherCliente(Seguradora seguradora){
        if(seguradora.getClientes().size() == 0){
            // Caso não tenha clientes
            System.out.println("\nNão há clientes na seguradora");
            return null;
        }

        // Leio documento (CPF ou CNPJ) do cliente
        System.out.println("\nInsira o documento do cliente:");
        String documento = scanner.nextLine();

        Cliente cliente = seguradora.encontrarCliente(documento);

        if(cliente != null){
            return cliente;
        }
        
        // Caso não encontre o cliente
        System.out.println("\n Documento inválido, tente novamente");
        return escolherCliente(seguradora);
    }

    static boolean inserirVeiculoParaCliente(){
        Seguradora seguradora = escolherSeguradora();
        Cliente cliente = escolherCliente(seguradora);

        if(cliente == null){
            return false;
        }

        System.out.println("\nInsira as informações do veículo: ");
        String placa, marca, modelo;
        int anoFabricacao;  
        System.out.print("Placa: ");
        placa = scanner.nextLine();
        System.out.print("Marca: ");
        marca = scanner.nextLine();
        System.out.print("Modelo: ");
        modelo = scanner.nextLine();
        System.out.print("Ano de fabricação: ");
        anoFabricacao = Integer.parseInt(scanner.nextLine());
        cliente.getVeiculosDoCliente().add(new Veiculo(placa, marca, modelo, anoFabricacao));

        return true;
            
    }

    static ArrayList<Veiculo> inserirVeiculos() {
        ArrayList<Veiculo> lv = new ArrayList<>();

        // Pergunto quantos carros desejo adicionar 
        System.out.print("Quantos carros deseja adicionar?: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        String placa, marca, modelo;
        int anoFabricacao;

        // Adiciono os carros na lista
        for (int i = 1; n >= i; ++i) {
            System.out.println("\nVEÍCULO " + i + ": ");
            System.out.print("Placa: ");
            placa = scanner.nextLine();
            System.out.print("Marca: ");
            marca = scanner.nextLine();
            System.out.print("Modelo: ");
            modelo = scanner.nextLine();
            System.out.print("Ano de fabricação: ");
            anoFabricacao = Integer.parseInt(scanner.nextLine());
            lv.add(new Veiculo(placa, marca, modelo, anoFabricacao));
        }

        // Retorno a lista
        return lv;
    }

    public static int separaPFePJ() {
        // Função que separa pessoa física de jurídica
        System.out.print("\nDigite F para pessoa física e J para pessoa jurídica: ");
        char p = scanner.next().charAt(0);
        scanner.nextLine();

        if (p == 'f' || p == 'F') {
            return 0;
        }

        if (p == 'j' || p == 'J') {
            return 1;
        }

        //Caso a entrada for inválida eu tento novamente
        System.out.println("> ENTRADA INVÁLIDA, TENTE NOVAMENTE");

        return separaPFePJ();
    }

    /*
     * Insiro as informaçoes do cliente  e retorno ele feito
     */
    public static boolean inserirCliente() {
        System.out.println("\n> INICIANDO CADASTRO");

        // Pergunto se o cliente é pessoa física o jurídica
        int p = separaPFePJ();
        Seguradora seguradora = escolherSeguradora(); 

        if (p == 0) { //PESSOA FÍSICA
            String nome, endereco, cpf, genero, classeEconomica, educacao;
            LocalDate dataNascimento, dataLicenca;

            //Insirio as informaçoes do cliente
            System.out.println("\nInsira as informações do cliente ");
            System.out.print("Nome: ");
            nome = scanner.nextLine();
            while(!Validacao.checkarNome(nome)){
                System.out.print("\n>NOME INVÁLIDO, TENTE NOVAMENTE: ");
                System.out.print("\nNome: ");
                nome = scanner.nextLine();
            }
            System.out.print("Endereço: ");
            endereco = scanner.nextLine();
            System.out.print("Gênero: ");
            genero = scanner.nextLine();
            System.out.print("Classe econômica: ");
            classeEconomica = scanner.nextLine();
            System.out.print("Educação: ");
            educacao = scanner.nextLine();
            System.out.print("Data de nascimento: ");
            dataNascimento = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            System.out.print("Data de licença: ");
            dataLicenca = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            System.out.print("CPF: ");
            cpf = scanner.nextLine();
            // Declaro o array de carros vazio
            ArrayList<Veiculo> veiculos = new ArrayList<>();

            // Instancio o cliente com todas as informações dadas e com a lista de carros vazia
            ClientePF clnt = new ClientePF(nome, endereco, veiculos, cpf, genero, dataLicenca, dataNascimento, classeEconomica, educacao);

            // Caso o cpf dê inválido ou repetido eu sigo inserindo novos até dar um válido e disponível
            while (!Validacao.validarCpf(clnt.getCpf()) || seguradora.encontrarCliente(clnt.getCpf()) != null) {
                if (seguradora.encontrarCliente(clnt.getCpf()) != null) {
                    System.out.println("> ESTE CPF JÁ FOI CADASTRADO NO SISTEMA");
                } else {
                    System.out.println("> CPF INVÁLIDO, TENTE NOVAMENTE");
                }
                System.out.print("CPF: ");
                clnt.setCpf(scanner.nextLine());
            }

            // Insiro os veículos e retorno o cliente
            clnt.setVeiculosDoCliente(inserirVeiculos());
            seguradora.cadastrarCliente(clnt);
            return true;
        }

        //PESSOA JURÍDICA

        //Insirio as informaçoes do cliente
        String nome, endereco, cnpj;
        int qtdeFuncionarios;
        LocalDate dataFundacao;
        System.out.print("Nome: ");
        nome = scanner.nextLine();
        while(!Validacao.checkarNome(nome)){
            System.out.print("\n>NOME INVÁLIDO, TENTE NOVAMENTE: ");
            System.out.print("\nNome: ");
            nome = scanner.nextLine();
        }
        System.out.print("Endereço: ");
        endereco = scanner.nextLine();
        System.out.print("Data de fundação: ");
        dataFundacao = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.print("CNPJ: ");
        cnpj = scanner.nextLine();
        System.out.print("Quantidade de funcionários: ");
        qtdeFuncionarios = Integer.parseInt(scanner.nextLine());
        
        // Declaro o array de carros vazio
        ArrayList<Veiculo> veiculos = new ArrayList<>();

        // Instancio o cliente com todas as informações dadas e com a lista de carros vazia
        ClientePJ clnt = new ClientePJ(nome, endereco, veiculos, cnpj, dataFundacao, qtdeFuncionarios);

        // Caso o cNPJ dê inválido ou repetido eu sigo inserindo novos até dar um válido e disponível
        while (!Validacao.validarCpf(clnt.getCnpj()) || seguradora.encontrarCliente(clnt.getCnpj()) != null) {
            if (seguradora.encontrarCliente(clnt.getCnpj()) != null) {
                System.out.println("> ESTE CNPJ JÁ FOI CADASTRADO NO SISTEMA");
            } else {
                System.out.println("> CNPJ INVÁLIDO, TENTE NOVAMENTE");
            }
            System.out.print("CNPJ: ");
            clnt.setCnpj(scanner.nextLine());
        }
        // Insiro os veículos e retorno o cliente
        clnt.setVeiculosDoCliente(inserirVeiculos());
        seguradora.cadastrarCliente(clnt);
        return true;
    }

    public static boolean removerSeguradora(Seguradora seguradora){
        for(int i = 0; seguradoras.size() > i; ++i){
            // Caso for a seguradora que procuro, eu deleto
            if(seguradora == seguradoras.get(i)){
                seguradoras.remove(i);
                return true;
            }
        }
        return false;
    }

    public static boolean menu(){
        // MENU PRINCIPAL, RETORNA FALSE CASO TENHA QUE FECHAR O PROGRAMA E TRUE PARA VOLTAR AO MENU
        System.out.print("\nPressione a tecla enter para continuar . . .");
        scanner.nextLine();
        System.out.println("\n-----------------MENU PRINCIPAL-----------------");
        System.out.println("\nDigite um dos inputs a seguir para realizar uma tarefa:");
        System.out.println("[1] Cadastrar");
        System.out.println("[2] Listar");
        System.out.println("[3] Remover");
        System.out.println("[4] Gerar sinistro");
        System.out.println("[5] Transferir Seguro");
        System.out.println("[6] Calcular reiceita seguradora");
        System.out.println("[0] Sair");

        int operator = Integer.parseInt(scanner.nextLine());
        MenuOperacoes enumOperator = MenuOperacoes.getReverso(operator);

        switch(enumOperator){
            case CADASTROS:
                menuCadastro();
                break;
            case LISTAR:
                menuListar();
                break;
            case EXCLUIR:
                menuExcluir();
                break;
            case GERAR_SINISTRO:
                Seguradora seguradora4 = escolherSeguradora();
                seguradora4.gerarSinistro(scanner);
                break;
            case TRANSFERIR_SEGURO:
                Seguradora seguradora5 = escolherSeguradora();
                System.out.println("\nInsira o documento do cliente o qual quer dar seu seguro:");
                String documentoClienteA = scanner.nextLine();
                Cliente clienteA = seguradora5.encontrarCliente(documentoClienteA);
                if(clienteA != null){
                    System.out.println("\nInsira o documento do cliente o qual quer receber:");
                    String documentoClienteB = scanner.nextLine();
                    Cliente clienteB = seguradora5.encontrarCliente(documentoClienteB);
                    if(clienteA.toString().equals(clienteB.toString())){
                        System.out.println("\n >ESSES DOCUMENTOS SÃO IGUAIS, ENTRADA INVÁLIDA:");
                    }else{
                        seguradora5.transferirSeguro(clienteA, clienteB);
                    }
                    break;
                }
            case CALCULA_RECEITA_SEGURADORA:
                Seguradora seguradora6 = escolherSeguradora();
                System.out.println(seguradora6.calcularReceita());
                break;
            case SAIR:
                System.out.println("\n Encerrando programa . . .");
                return false;
            default:
                System.out.println("\n> ENTRADA INVÀLIDA");
        }
        return true;
    }

    public static boolean menuCadastro(){
        // SUB MENU DE CADASTRO
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("[1] Cadastrar cliente (PF/PJ)");
        System.out.println("[2] Cadastrar seguradora");
        System.out.println("[3] Cadastrar veículo");
        System.out.println("[0] Voltar");

        int operator = Integer.parseInt(scanner.nextLine());
        Cadastrar enumOperator = Cadastrar.getReverso(operator);

        switch(enumOperator){
            case CLIENTE:
                inserirCliente();
                break;
            case SEGURADORA:
                inserirSeguradora();
                break;
            case VEICULO:
                inserirVeiculoParaCliente();
                break;
            case SAIR:
                // Vazio
                break;
            default:
                System.out.println("\n> ENTRADA INVÀLIDA");
                return false;

        }
        return true;
    }

    public static boolean menuListar(){
        // SUB MENU DE LISTAR
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("[1] Listar cliente (PF/PJ) por seguradora");
        System.out.println("[2] Listar sinistros por seguradora");
        System.out.println("[3] Listar sinistro por cliente");
        System.out.println("[4] Listar Veiculo por cliente");
        System.out.println("[5] Listar Veiculo por seguradora");
        System.out.println("[0] Voltar");

        int operator = Integer.parseInt(scanner.nextLine());
        Listar enumOperator = Listar.getReverso(operator);

        switch(enumOperator){
            case CLIENTE_P_SEGURADORA:
                Seguradora seguradora = escolherSeguradora();
                int operador = separaPFePJ();
                seguradora.listarClientes(operador);
                break;
            case SINISTRO_P_SEGURADORA:
                Seguradora seguradora2 = escolherSeguradora();
                seguradora2.listarSinistros();
                break;
            case SINISTRO_P_CLIENTE:
                Seguradora seguradora3 = escolherSeguradora();
                System.out.println("\nInsira o documento do cliente envolvido:");
                String documentoCliente3 = scanner.nextLine();
                System.out.println("\nInsira a data do sinistro:");
                LocalDate dataSinistro3 = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                seguradora3.vizualizarSinistro(documentoCliente3, dataSinistro3);
                break;
            case VEICULO_P_CLIENTE:
                Seguradora seguradora4 = escolherSeguradora();
                Cliente cliente4 = escolherCliente(seguradora4);
                if(cliente4 != null){
                    if(cliente4.getVeiculosDoCliente().size()>0){
                        System.out.println("\n" + cliente4.toStringVeiculos());
                    }else{
                        System.out.println("\nO cliente "+cliente4.getNome()+" não tem veiculos");
                    }
                    
                }
                break;
            case VEICULO_P_SEGURADORA:
                Seguradora seguradora5 = escolherSeguradora();
                seguradora5.listarVeiculos();
                break;
            case SAIR:
                //vazio
                break;
            default:
                System.out.println("\n> ENTRADA INVÀLIDA");
                return false;
        }
        return true;
    }

    public static boolean menuExcluir(){
        // SUB MENU DE EXCLUIR
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("[1] Excluir Cliente");
        System.out.println("[2] Excluir Veiculo");
        System.out.println("[3] Excluir Sinistro");
        System.out.println("[0] Voltar");

        int operator = Integer.parseInt(scanner.nextLine());
        Excluir enumOperador = Excluir.getReverso(operator);
        switch(enumOperador){
            case CLIENTE:
                Seguradora seguradora = escolherSeguradora();
                System.out.println("\nInsira o documento do cliente:");
                String documentoCliente = scanner.nextLine();
                seguradora.removerCliente(documentoCliente);
                break;
            case VEICULO:
                Seguradora seguradora2 = escolherSeguradora();
                System.out.println("\nInsira o documento do cliente envolvido:");
                String documentoCliente2 = scanner.nextLine();
                System.out.println("\nInsira a placa do veículo:");
                String placaVeiculo2 = scanner.nextLine();
                seguradora2.removerVeiculo(documentoCliente2, placaVeiculo2);
                break;
            case SINISTRO:
                Seguradora seguradora3 = escolherSeguradora();
                System.out.println("\nInsira o documento do cliente envolvido:");
                String documentoCliente3 = scanner.nextLine();
                System.out.println("\nInsira a data do sinistro:");
                LocalDate dataSinistro3 = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                seguradora3.removerSinistro(documentoCliente3, dataSinistro3);
                break;
            case SAIR:
                //vazio
                break;
            default:
                System.out.println("\n> ENTRADA INVÀLIDA");
                return false;
        }
        return true;
    }
    public static void main(String[] args){
        //DECLARO CLIENTES (PF E PJ) E CARROS
        ArrayList<Cliente> clientes = new ArrayList<>();
        ArrayList<Sinistro> sinistros = new ArrayList<>();

        seguradoras.add(new Seguradora("Seguradora Amigos", "987654321", "seguamigos@gmail.com", "Rua amigos 123", clientes, sinistros));
        
        ArrayList<Veiculo> veiculos1 = new ArrayList<>();
        ArrayList<Veiculo> veiculos2 = new ArrayList<>();
        ClientePF cliente1 = new ClientePF("Pedro da Silva", "Rua Fisica 87", veiculos1, "847.850.640-30", "Masculino", LocalDate.parse("25/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalDate.parse("25/12/2003", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "Rico", "Nível superior incompleto");
        ClientePJ cliente2 = new ClientePJ("Empresa Muito do Massa", "Rua Jurídica 51", veiculos2, "27.118.503/0001-12", LocalDate.parse("13/12/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")), 41);
        
        Veiculo carro1 = new Veiculo("ABCD123", "Camaro", "RTX", 2013);
        Veiculo carro2 = new Veiculo("HIJK876", "HB20", "MDS", 2017);
        cliente1.getVeiculosDoCliente().add(carro1);
        cliente2.getVeiculosDoCliente().add(carro2);

        // CADASTRO ELES 

        seguradoras.get(0).cadastrarCliente(cliente1);
        seguradoras.get(0).cadastrarCliente(cliente2);

        // REGISTRO OS DOIS SINISTROS
        Sinistro sinistro1 = new Sinistro("Rua do desastre 666", LocalDate.parse("13/12/2012", DateTimeFormatter.ofPattern("dd/MM/yyyy")), seguradoras.get(0), carro1, cliente1);
        Sinistro sinistro2 = new Sinistro("Rua da batida 88", LocalDate.parse("14/11/2013", DateTimeFormatter.ofPattern("dd/MM/yyyy")), seguradoras.get(0), carro2, cliente2);
        seguradoras.get(0).getSinistros().add(sinistro1);
        seguradoras.get(0).getSinistros().add(sinistro2);

        // LISTO OS DOIS CLIENTES
        seguradoras.get(0).listarClientes(0);
        seguradoras.get(0).listarClientes(1);

        // VIZUALIZO SINSTRO
        seguradoras.get(0).vizualizarSinistro("847.850.640-30", LocalDate.parse("13/12/2012", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        seguradoras.get(0).calcularReceita();
        
        // CALCULO PREÇO DO SEGURO DE CADA
        for(int i = 0; seguradoras.get(0).getClientes().size() > i; ++i){
            if(seguradoras.get(0).getClientes().get(i) instanceof ClientePF){
                seguradoras.get(0).calcularPrecoSeguroCliente(((ClientePF)seguradoras.get(0).getClientes().get(i)).getCpf());
            }else{
                seguradoras.get(0).calcularPrecoSeguroCliente(((ClientePJ)seguradoras.get(0).getClientes().get(i)).getCnpj());
            }
        }

        // RETIRO A RECEITA DA SEGURADORA
        System.out.println("\nA receita da seguradora é de "+seguradoras.get(0).calcularReceita()+" reais");

        System.out.println("\nINICIANDO O MENU DE OPRERAÇÕES. . . \n");

        // Iteração do menu principal que ocorre até retornar false (no caso quando ele é encerrado pelo usuário)
        for(;;){
            if(menu() == false){
                break;
            }
        }

        // Fecho o scanner no final do programa
        scanner.close();
    }
}
