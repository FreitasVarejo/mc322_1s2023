import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainClass {
    //Scanner global que abro e fecho na main
    static Scanner scanner = new Scanner(System.in);
    //Lista de seguradoras que uso na função toda
    static ArrayList <Seguradora> listaSeguradoras = new ArrayList<>();

    // Funções de cadastro

    // Função que separa pessoa casos de entidades físicas de jurídicas
    public static int separaPFePJ(){
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

    // Insiro as informações de um clie nte e retorno ele feito
    public static Cliente inserirCliente(Seguradora seguradora) {
        System.out.println("\n> INICIANDO CADASTRO");

        // Pergunto se o cliente é pessoa física o jurídica
        int p = separaPFePJ();

        if (p == 0) { //PESSOA FÍSICA
            String nome, telefone, email, endereco, cpf, genero, educacao;
            LocalDate dataNascimento;
            ArrayList <Veiculo> listaVeiculos;

            System.out.println("\nInsira as informações do cliente ");

            // Insiro nomes até ser um nome válido, composto apenas de letras e espaço
            System.out.print("Nome: ");
            nome = scanner.nextLine();
            while(!Validacao.checkarNome(nome)){
                System.out.print("\n>ERRO: Nome inválido, tente novamente: ");
                System.out.print("\nNome: ");
                nome = scanner.nextLine();
            }

            // Insiro CPFs até ser um válido e que nenhum outro cliente PF da seguradora tenha usado
            System.out.print("CPF: ");
            cpf = scanner.nextLine();
            while (!Validacao.validarCpf(cpf) || Validacao.indiceDaSeguradoraNaLista(cpf, listaSeguradoras) != -1){
                if (!Validacao.validarCpf(cpf)){
                    System.out.println("> ERRO: CPF inválido, tente novamente");
                } else {
                    System.out.println("> ERRO: Este CPF já foi cadastrado no sistema, tente novamente");
                }
                System.out.print("CPF: ");
                cpf = scanner.nextLine();
            }

            // Insiro o resto dos dados
            System.out.print("Telefone: ");
            telefone = scanner.nextLine();
            System.out.print("Email: ");
            email = scanner.nextLine();
            System.out.print("Endereço: ");
            endereco = scanner.nextLine();
            System.out.print("Gênero: ");
            genero = scanner.nextLine();
            System.out.print("Educação: ");
            educacao = scanner.nextLine();
            System.out.print("Data de nascimento: ");
            dataNascimento = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            // Insiro a lista de veículos
            listaVeiculos = inserirListaVeiculos();

            ClientePF cliente = new ClientePF(nome, telefone, email, endereco, cpf, 
            genero, educacao, dataNascimento, listaVeiculos);
            return cliente;
        }else{//PESSOA JURÍDICA
            String nome, telefone, email, endereco, cnpj;
            LocalDate dataFundacao;
            ArrayList<Frota> listaFrotas;

            // Insiro nomes até ser um nome válido, composto apenas de letras e espaço
            System.out.print("Nome: ");
            nome = scanner.nextLine();
            while(!Validacao.checkarNome(nome)){
                System.out.print("\n>ERRO: No,e inválido, tente novamente: ");
                System.out.print("\nNome: ");
                nome = scanner.nextLine();
            }

            // Insiro CNPJs até ser um válido e que nenhum outro cliente PJ da seguradora tenha usado
            System.out.print("CNPJ: ");
            cnpj = scanner.nextLine();
            while (!Validacao.validarCnpj(cnpj) || Validacao.indiceDaSeguradoraNaLista(cnpj, listaSeguradoras) != -1){
                if (!Validacao.validarCnpj(cnpj)){
                    System.out.println("> ERRO: CNPJ inválido, tente novamente");
                } else {
                    System.out.println("> ERRO: Este CNPJ já foi cadastrado no sistema, tente novamente");
                }
                System.out.print("CNPJ: ");
                cnpj = scanner.nextLine();
            }

            // Insiro o resto dos dados
            System.out.print("Telefone: ");
            telefone = scanner.nextLine();
            System.out.print("Email: ");
            email = scanner.nextLine();
            System.out.print("Endereço: ");
            endereco = scanner.nextLine();
            System.out.print("Data de fundação: ");
            dataFundacao = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            // Insiro a lista de frotas
            listaFrotas = inserirListaFrota();
            
            ClientePJ cliente = new ClientePJ(nome, telefone, email, endereco, 
            cnpj, dataFundacao, listaFrotas);
            return cliente;
        }
    }

    static Seguro inserirSeguro(Seguradora seguradora){
        // Pergunto se o seguro é de pessoa física o jurídica
        int p = separaPFePJ();

        if (p == 0) { 
            // SEGURO DE PESSOA FÍSICA
            LocalDate dataInicio , dataFim ;
            Veiculo veiculo;
            ClientePF clientePF;
            ArrayList <Condutor> listaCondutores;
            
            // Insiro dados do seguro
            System.out.print("Data de início do seguro: ");
            dataInicio = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            System.out.print("Data do fim do seguro: ");
            dataFim = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            
            // Insiro CPFs até ser um compatível com de um cliente PF da seguradora
            System.out.print("CPF do cliente: ");
            String cpf = scanner.nextLine();    
            int idxDoCliente = Validacao.indiceDeClienteNaLista(cpf, seguradora.getListaClientes());
            while(idxDoCliente == -1){
                System.out.println("ERRO: cliente do CPF " + cpf + " não encontrado na seguradora "+ seguradora.getNome() + ", tente novamente");
                System.out.print("CPF do cliente: ");
                cpf = scanner.nextLine();  
            }
            clientePF = ((ClientePF)seguradora.getListaClientes().get(idxDoCliente));

            // Procuro um veículo do cliente para registrar no seguro
            veiculo = encontrarVeiculo(clientePF.getListaVeiculos());

            // Insiro uma lista de condutores para serem cadastrado no seguro a ser criado
            listaCondutores = inserirListaCondutores(new ArrayList<>());

            SeguroPF seguroPF = new SeguroPF(dataInicio, dataFim, seguradora, listaCondutores, veiculo, clientePF);
            return seguroPF;
        }else{
            // SEGURO DE PESSOA JURÍDICA
            LocalDate dataInicio , dataFim ;
            Frota frota;
            ClientePJ clientePJ;
            ArrayList <Condutor> listaCondutores;

            // Insiro os dados do seguro
            System.out.print("Data de início do seguro: ");
            dataInicio = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            System.out.print("Data do fim do seguro: ");
            dataFim = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            
            // Insiro CNPJs até ser um compatível com de um cliente PJ da seguradora
            System.out.print("CNPJ do cliente: ");
            String cnpj = scanner.nextLine();    
            int idxDoCliente = Validacao.indiceDeClienteNaLista(cnpj, seguradora.getListaClientes());
            while(idxDoCliente == -1){
                System.out.println("ERRO: cliente do CNPJ " + cnpj + " não encontrado na seguradora "+ seguradora.getNome() + ", tente novamente");
                System.out.print("CNPJ do cliente: ");
                cnpj = scanner.nextLine();  
            }
            clientePJ = ((ClientePJ)seguradora.getListaClientes().get(idxDoCliente));
            
            // Procuro uma frota do cliente PJ para ser registrada no seguro
            frota = encontrarFrota(clientePJ.getListaFrotas());

            // Insiro uma lista de condutor es para serem registrados no seguro que vai ser criado
            listaCondutores = inserirListaCondutores(new ArrayList<>());

            SeguroPJ seguroPJ = new SeguroPJ(dataInicio, dataFim, seguradora, listaCondutores, frota, clientePJ);
            return seguroPJ;
        }
    }

    public static Condutor inserirCondutor(ArrayList<Condutor> listaCondutoresDoSeguro){
        String cpf, nome , telefone, endereco, email ;
        LocalDate dataNascimento ;
        ArrayList <Sinistro> listaSinistros = new ArrayList<>();

        // Insiro nomes até ser um nome válido, composto apenas de letras e espaço
        System.out.print("Nome: ");
        nome = scanner.nextLine();
        while(!Validacao.checkarNome(nome)){
            System.out.print("\n>ERRO: NOME inválido, tente novamente: ");
            System.out.print("\nNome: ");
            nome = scanner.nextLine();
        }

        // Insiro CPFs até ser um válido e que nenhum outro condutor do seguro tenha usado
        System.out.print("CPF: ");
        cpf = scanner.nextLine();
        while (!Validacao.validarCpf(cpf) || Validacao.indiceDoCondutorNaLista(cpf, listaCondutoresDoSeguro) != -1){
            if (!Validacao.validarCpf(cpf)){
                System.out.println("> ERRO: CPF inválido, tente novamente");
            } else {
                System.out.println("> ERRO: Este CPF já foi cadastrado nesse seguro, tente novamente");
            }
            System.out.print("CPF: ");
            cpf = scanner.nextLine();
        }

        // Insiro o resto dos dados do condutor
        System.out.print("Telefone: ");
        telefone = scanner.nextLine();
        System.out.print("Email: ");
        email = scanner.nextLine();
        System.out.print("Endereço: ");
        endereco = scanner.nextLine();
        System.out.print("Data de nascimento: ");
        dataNascimento = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        
        
        Condutor condutor = new Condutor(cpf, nome, telefone, endereco, email, dataNascimento, listaSinistros);  
        return condutor;
    }

    public static ArrayList<Condutor>inserirListaCondutores(ArrayList<Condutor> listaCondutorSeguro) {
        // Pergunto quantos condutores desejo adicionar 
        System.out.print("\nQuantos condutores deseja adicionar? ");
        int n = Integer.parseInt(scanner.nextLine());

        // Adiciono os N condutores na lista
        // A função inserirCondutor() não permite adicionar condutores com CPF repetidos
        for(int i = 1; n >= i; ++i) {
            listaCondutorSeguro.add(inserirCondutor(listaCondutorSeguro));
        }

        // Retorno a lista
        return listaCondutorSeguro;
    }

    public static Seguradora inserirSeguradora() {
        String cnpj, nome, telefone, email, endereco;

        System.out.println("\nInsira as informações da seguradora ");

        // Insiro CNPJs até ser um válido e que nenhuma outra seguradora tenha usado
        System.out.print("CNPJ: ");
        cnpj = scanner.nextLine();
        while (!Validacao.validarCnpj(cnpj) || Validacao.indiceDaSeguradoraNaLista(cnpj, listaSeguradoras) != -1) {
            if (!Validacao.validarCnpj(cnpj)){
                System.out.println("> ERRO: CNPJ inválido, tente novamente");
            } else {
                System.out.println("> ERRO: Este CPNJ já foi usado por outra seguradora, tente novamente");
            }
            System.out.print("CNPJ: ");
            cnpj = scanner.nextLine();
        }
        
        // Insiro nomes até ser um nome válido, composto apenas de letras e espaço
        System.out.print("Nome: ");
        nome = scanner.nextLine();
        while(!Validacao.checkarNome(nome)){
            System.out.print("\n>ERRO: Nome inválido, tente novamente");
            System.out.print("\nNome: ");
            nome = scanner.nextLine();
        }
        
        // Insiro o restante dos dados da seguradora
        System.out.print("Telefone: ");
        telefone = scanner.nextLine();
        System.out.print("Email: ");
        email = scanner.nextLine();
        System.out.print("Endereço: ");
        endereco = scanner.nextLine();

        // Insiro listas vazias de seguros e clientes
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        ArrayList<Seguro> listaSeguro = new ArrayList<>();

        Seguradora seguradora = new Seguradora(cnpj, nome, telefone, endereco, email, listaClientes, listaSeguro);

        return seguradora;
    }

    
    static Veiculo inserirVeiculo(ArrayList <Veiculo> listaVeiculos){
        System.out.println("\nInsira as informações do veículo: ");
        String placa, marca, modelo;
        int anoFabricacao;  

        // Insiro placas até ser uma a qual não tenha sido usada antes
        System.out.print("Placa: ");
        placa = scanner.nextLine();
        while(Validacao.indiceDoVeiculoNaLista(placa, listaVeiculos) != -1){
            System.out.println("> ERRO: Esta placa já foi registrada, tente novamente");
            System.out.print("Placa: ");
            placa = scanner.nextLine();
        }

        // Insiro o resto dos dados do veículo
        System.out.print("Marca: ");
        marca = scanner.nextLine();
        System.out.print("Modelo: ");
        modelo = scanner.nextLine();
        System.out.print("Ano de fabricação: ");
        anoFabricacao = Integer.parseInt(scanner.nextLine());
        
        Veiculo veiculo = new Veiculo(placa, marca, modelo, anoFabricacao);
        return veiculo;
    }

    static ArrayList<Veiculo> inserirListaVeiculos(){
        ArrayList<Veiculo> listaVeiculos = new ArrayList<>();

        // Pergunto quantos carros desejo adicionar 
        System.out.print("\nQuantos carros deseja adicionar? ");
        int n = Integer.parseInt(scanner.nextLine());

        // Adiciono os carros na lista
        for(int i = 1; n >= i; ++i) {
            listaVeiculos.add(inserirVeiculo(listaVeiculos));
        }

        // Retorno a lista
        return listaVeiculos;
    }

    static Frota inserirFrota(){
        // Insiro lista de veículos e crio a frota em seguida
        Frota frota = new Frota(inserirListaVeiculos());
        System.out.print("Criada frota de código " + frota.getCode() +".");
        return frota;
    }

    static ArrayList<Frota> inserirListaFrota(){
        ArrayList<Frota> listaFrotas = new ArrayList<>();

        // Pergunto quantas frotas desejo adicionar 
        System.out.print("\nQuantas frotas deseja adicionar? ");
        int n = Integer.parseInt(scanner.nextLine());

        // Adiciono as N frotas na lista
        for(int i = 1; n >= i; ++i){
            listaFrotas.add(inserirFrota());
        }

        // Retorno a lista
        return listaFrotas;
    }

    // Funções de encontrar objetos em listas

    public static Seguradora encontrarSeguradora(){
        // Caso tenhaapenas uma seguradora eu escolho ela
        if(listaSeguradoras.size() == 1){
            return listaSeguradoras.get(0);
        }

        // Pergunto qual seguradora seja utilizar baseado em seus índices de 1 a N (tamanho da lista)
        System.out.println("\nQual seguradora deseja usar?");
        for(int i = 0; listaSeguradoras.size() > i; ++i){
            System.out.println("["+(i+1)+"] + "+listaSeguradoras.get(i).getNome());
        }
        int operator = Integer.parseInt(scanner.nextLine());

        if(listaSeguradoras.size() >= operator && operator >= 1){
            return listaSeguradoras.get(operator);
        }

        // Caso a entrada for inválida é reiniciada a função
        System.out.println("\n>ERRO: Índice inválido, tente novamente");
        return encontrarSeguradora();
    }

    public static Cliente encontrarCliente(ArrayList <Cliente> listaClientes){
        // Caso a seguradora não tenha clientes eu retorno NULL
        if(listaClientes.size() == 0){
            System.out.println("\nNão há clientes na seguradora");
            return null;
        }

        System.out.print("Documento do cliente (CPF/CNPJ): ");
        String documentoCliente = scanner.nextLine();
        int idxCliente = Validacao.indiceDeClienteNaLista(documentoCliente, listaClientes);

        if(idxCliente != -1){
            // Caso tenha cliente com tal documento eu retorno ele 
            return listaClientes.get(idxCliente);
        }else{
            // Caso NÃO tenha cliente com tal documento eu  retorno NULL 
            System.out.println("ERRO: Cliente de documento " + documentoCliente + " não foi encontrado, tente novamente");
            return null;
        }
    }

    public static Seguro encontrarSeguro(ArrayList<Seguro> listaSeguros){
        // Caso a seguradora não tenha seguros eu retorno NULL
        if(listaSeguros.size() == 0){
            System.out.println("\nNão há seguros na seguradora");
            return null;
        }

        System.out.println("\n Insira o ID do seguro:");
        int idSeguro = Integer.parseInt(scanner.nextLine());
        int idxSeguro = Validacao.indiceDeSeguroNaLista(idSeguro, listaSeguros);

        if(idxSeguro == -1){
        // Caso NÃO tenha seguro com tal ID eu NULL             
            System.out.println("ERRO: Seguro de ID " + idSeguro + " não foi encontrado, tente novamente");
            return null;
        }else{
            // Caso tenha seguro com tal ID eu retorno ele 
            Seguro seguro = listaSeguros.get(idxSeguro);
            return seguro;
        }        
    }

    public static Veiculo encontrarVeiculo(ArrayList<Veiculo> listaVeiculos){
        // Caso a lista não tenha veículos eu retorno NULL
        if(listaVeiculos.size() == 0){
            System.out.println("\nNão há veículos registrados nessa lista");
            return null;
        }

        System.out.println("\n Insira a placa do veículo:");
        String placaVeiuclo= scanner.nextLine();

        int idxSeguro = Validacao.indiceDoVeiculoNaLista(placaVeiuclo, listaVeiculos);

        if(idxSeguro == -1){
            // Caso NÃO tenha veículo com tal placa eu retorno NULL  
            System.out.println("ERRO: Veículo de placa " + idxSeguro + " não foi encontrado, tente novamente");
            return null;
        }else{
            // Caso tenha veículo com tal placa eu retorno ele 
            Veiculo veiculo = listaVeiculos.get(idxSeguro);
            return veiculo;
        }        
    }

    public static Frota encontrarFrota(ArrayList<Frota> listaFrotas){
        // Caso a lista não tenha frotas eu retorno NULL
        if(listaFrotas.size() == 0){
            System.out.println("\nNão há frotas registradas nesse cliente");
            return null;
        }

        System.out.println("\n Insira o código da frota:");
        int codeFrota = Integer.parseInt(scanner.nextLine());

        int idxSeguro = Validacao.indiceDaFrotaNaLista(codeFrota, listaFrotas);

        if(idxSeguro == -1){
            // Caso NÃO tenha frota com tal código eu retorno NULL  
            System.out.println("ERRO: Frota de código " + idxSeguro + " não foi encontrada, tente novamente");
            return null;
        }else{
            // Caso tenha frota com tal código eu retorno ela 
            Frota frota = listaFrotas.get(idxSeguro);
            return frota;
        }        
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
        System.out.println("[5] Calcular reiceita seguradora");
        System.out.println("[6] Gravar dados seguradora");
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
                Seguradora seguradora4 = encontrarSeguradora();
                Seguro seguro4 = encontrarSeguro(seguradora4.getListaSeguros());
                if(seguro4 != null){
                    System.out.print("Documento do condutor (CPF): ");
                    String documentoCondutor4 = scanner.nextLine();
                    System.out.print("Endereço do sinistro: ");
                    String enderecoSinistro4 = scanner.nextLine();
                    seguro4.gerarSinistro(enderecoSinistro4, documentoCondutor4);
                }
                break;
            case CALCULA_RECEITA_SEGURADORA:
                Seguradora seguradora6 = encontrarSeguradora();
                System.out.println("\nA receita da seguradora é de "+seguradora6.calcularReceita()+".00 R$");
                break;
            case GRAVAR_DADOS_SEGURADORA:
                Seguradora seguradora7= encontrarSeguradora();
               seguradora7.gravarDados();
                break;
            case SAIR:
                System.out.println("\nEncerrando programa . . .");
                return false;
            default:
                System.out.println("\n>ERRO: Entrada Inválida");
        }
        return true;
    }

    public static boolean menuCadastro(){

        // SUB MENU DE CADASTRO
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("[1] Cadastrar cliente (PF/PJ)");
        System.out.println("[2] Cadastrar seguradora");
        System.out.println("[3] Cadastrar veículo");
        System.out.println("[4] Cadastrar frota");
        System.out.println("[5] Cadastrar seguro");
        System.out.println("[6] Cadastrar condutor");
        System.out.println("[0] Voltar");

        int operator = Integer.parseInt(scanner.nextLine());
        Cadastrar enumOperator = Cadastrar.getReverso(operator);

        switch(enumOperator){
            case CLIENTE:
                Seguradora seguradora1 = encontrarSeguradora();
                Cliente cliente1 = inserirCliente(seguradora1);
                seguradora1.cadastrarCliente(cliente1);
                break;
            case SEGURADORA:
                Seguradora seguradora2 = inserirSeguradora();
                listaSeguradoras.add(seguradora2);
                break;
            case VEICULO:
                Seguradora seguradora3 = encontrarSeguradora();
                Cliente cliente3 = (Cliente)encontrarCliente(seguradora3.getListaClientes());
                if(cliente3 != null && cliente3 instanceof ClientePF){
                    Veiculo veiculo3 = inserirVeiculo(((ClientePF)cliente3).getListaVeiculos());
                    ((ClientePF)cliente3).adicionarVeiculo(veiculo3);
                }else if(cliente3 != null && cliente3 instanceof ClientePJ){ 
                    Frota frota3 = encontrarFrota(((ClientePJ)cliente3).getListaFrotas());
                    Veiculo veiculo3 = inserirVeiculo(frota3.getListaVeiculos());
                    ((ClientePJ)cliente3).adicionarVeiculo(frota3.getCode(), veiculo3);
                }
                break;
            case FROTA:
                Seguradora seguradora4 = encontrarSeguradora();
                Cliente cliente4 = (Cliente)encontrarCliente(seguradora4.getListaClientes());
                if(cliente4 != null && cliente4 instanceof ClientePF){
                    System.out.println("\n> ERRO: O documento inserido foi relativo a uma pessoa física, tente novamente.");
                }else if(cliente4 != null && cliente4 instanceof ClientePJ){
                    Frota frota = inserirFrota();
                    ((ClientePJ)cliente4).cadastrarFrota(frota);
                }
                break;
            case SEGURO:
                Seguradora seguradora5 = encontrarSeguradora(); 
                Seguro seguro5 = inserirSeguro(seguradora5);
                seguradora5.gerarSeguro(seguro5);
            case CONDUTOR:
                Seguradora seguradora6 = encontrarSeguradora();
                Seguro seguro6 = encontrarSeguro(seguradora6.getListaSeguros());
                if(seguro6 != null){
                    Condutor condutor6 = inserirCondutor(seguro6.getListaCondutores());
                    seguro6.autorizarCondutor(condutor6);
                }
            case SAIR:
                // Vazio
                break;
            default:
                System.out.println("\n> ERRO: Entrada Inválida");
                return false;

        }
        return true;
    }

    public static boolean menuListar(){
        // SUB MENU DE LISTAR
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("[1] Cientes(PF/PJ) por seguradora");
        System.out.println("[2] Seguro(PF/PJ) por seguradora");
        System.out.println("[3] Sinistro por seguradora");
        System.out.println("[4] Veiculo por seguradora");
        System.out.println("[5] Frota por seguradora");
       
        System.out.println("\n[6] Sinistro por cliente");
        System.out.println("[7] Seguro por cliente");
        System.out.println("[8] Frota por cliente");
        System.out.println("[9] Veiculo por cliente");

        System.out.println("\n[10] Sinistro por seguro");
        System.out.println("[11] Condutor por seguro");

        System.out.println("\n[0] Voltar");

        int operator = Integer.parseInt(scanner.nextLine());
        Listar enumOperator = Listar.getReverso(operator);

        switch(enumOperator){
            case CLIENTE_P_SEGURADORA:
                Seguradora seguradora1 = encontrarSeguradora();
                int operador1 = separaPFePJ();
                seguradora1.listarClientes(operador1);
                break;
            case SEGURO_P_SEGURADORA:
                Seguradora seguradora2 = encontrarSeguradora();
                int operador2 = separaPFePJ();
                seguradora2.listarSeguros(operador2);
                break;
            case SINISTRO_P_SEGURADORA:
                Seguradora seguradora3 = encontrarSeguradora();
                seguradora3.listarSinistros();
                break;
            case VEICULO_P_SEGURADORA:
                Seguradora seguradora4 = encontrarSeguradora();
                seguradora4.listarVeiculos();
                break;
            case FROTA_P_SEGURADORA:
                Seguradora seguradora5 = encontrarSeguradora();
                seguradora5.listarFrotas();
                break;
            case SINISTRO_P_CLIENTE:
                Seguradora seguradora6 = encontrarSeguradora();
                System.out.print("Documento do cliente (CPF/CNPJ): ");
                String documentoCliente6 = scanner.nextLine();
                seguradora6.listarVeiculosPorCliente(documentoCliente6);
                break;
            case SEGURO_P_CLIENTE:
                Seguradora seguradora7 = encontrarSeguradora();
                System.out.print("Documento do cliente (CPF/CNPJ): ");
                String documentoCliente7 = scanner.nextLine();
                seguradora7.listarSegurosPorCliente(documentoCliente7);
                break;
            case FROTA_P_CLIENTE:
                Seguradora seguradora8 = encontrarSeguradora();
                System.out.print("Documento do cliente (CPF/CNPJ): ");
                String documentoCliente8 = scanner.nextLine();
                seguradora8.listarFrotasPorCliente(documentoCliente8);
                break;
            case VEICULO_P_CLIENTE:
                Seguradora seguradora9 = encontrarSeguradora();
                System.out.print("Documento do cliente (CPF/CNPJ): ");
                String documentoCliente9 = scanner.nextLine();
                seguradora9.listarVeiculosPorCliente(documentoCliente9);
                break;
            case SINISTRO_P_SEGURO:
                Seguradora seguradora10 = encontrarSeguradora();
                System.out.println("\n Insira o ID do seguro:");
                int idSeguro10 = Integer.parseInt(scanner.nextLine());
                seguradora10.listarSinistrosPorSeguro(idSeguro10);
                break;
            case CONDUTOR_P_SEGURO:
                Seguradora seguradora11 = encontrarSeguradora();
                System.out.println("\n Insira o ID do seguro:");
                int idSeguro11 = Integer.parseInt(scanner.nextLine());
                seguradora11.listarCondutoresPorSeguro(idSeguro11);
                break;
            case SAIR:
                //vazio
                break;
            default:
                System.out.println("\n>ERRO: Entrada Inválida");
                return false;
        }
        return true;
    }

    public static boolean menuExcluir(){
        // SUB MENU DE EXCLUIR
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("[1] Excluir cliente (PF/PJ)");
        System.out.println("[2] Excluir seguradora");
        System.out.println("[3] Excluir veículo (PF/PJ)");
        System.out.println("[4] Excluir frota");
        System.out.println("[5] Excluir seguro");
        System.out.println("[6] Excluir condutor");
        System.out.println("[7] Excluir sinistro");
        System.out.println("[0] Voltar");

        int operator = Integer.parseInt(scanner.nextLine());
        Excluir enumOperador = Excluir.getReverso(operator);
        switch(enumOperador){
            case CLIENTE:
                Seguradora seguradora1 = encontrarSeguradora();
                System.out.print("Documento do cliente (CPF/CNPJ): ");
                String documentoCliente = scanner.nextLine();
                seguradora1.removerCliente(documentoCliente);
                break;
            case SEGURADORA:
                if(listaSeguradoras.size() == 1){
                    listaSeguradoras.remove(0);
                    System.out.println("\nFoi removida a única seguradora do sistema.");
                    System.out.print("Pressione a tecla enter para continuar . . .");
                    scanner.nextLine();

                    Seguradora seguradora2 = inserirSeguradora();
                    listaSeguradoras.add(seguradora2);                                  
                }else{
                    Seguradora seguradora2 = encontrarSeguradora();
                    int idxSeguradora2 = Validacao.indiceDaSeguradoraNaLista(seguradora2.getCnpj(), listaSeguradoras);
                    listaSeguradoras.remove(idxSeguradora2);
                }
                
                break;
            case VEICULO:
                Seguradora seguradora3 = encontrarSeguradora();
                Cliente cliente3 = encontrarCliente(seguradora3.getListaClientes());
                if(cliente3 != null){
                    System.out.print("Placa do veículo: ");
                    String placaVeiculo3 = scanner.nextLine();
                    seguradora3.removerVeiculo(cliente3, placaVeiculo3);
                }
                break;
            case FROTA:
                Seguradora seguradora4 = encontrarSeguradora();
                Cliente cliente4 = encontrarCliente(seguradora4.getListaClientes());
                if(cliente4 != null && cliente4 instanceof ClientePJ){
                    System.out.print("ID da frota: ");
                    int idFrota4 = Integer.parseInt(scanner.nextLine());
                    seguradora4.removerFrota(cliente4, idFrota4);
                }
                break;
            case SEGURO:
                Seguradora seguradora5 = encontrarSeguradora();
                Seguro seguro5 = encontrarSeguro(seguradora5.getListaSeguros());
                if(seguro5 != null){
                    seguradora5.cancelarSeguro(seguro5.getId());
                }
                break;
            case CONDUTOR:
                Seguradora seguradora6 = encontrarSeguradora();
                Seguro seguro6 = encontrarSeguro(seguradora6.getListaSeguros());
                if(seguro6 != null){
                    System.out.println("\n Insira o CPF do condutor:");
                    String cpfCondutor = scanner.nextLine();
                    seguro6.desautorizarCondutor(cpfCondutor);
                }
                break;
            case SINISTRO:
                Seguradora seguradora7 = encontrarSeguradora();
                Seguro seguro7 = encontrarSeguro(seguradora7.getListaSeguros());
                if(seguro7 != null){
                    System.out.println("\n Insira o ID do sinistro:");
                    int idSinistro = Integer.parseInt(scanner.nextLine());
                    seguro7.removerSinistro(idSinistro);
                }
                break;
            case SAIR:
                //vazio
                break;
            default:
                System.out.println("\n>ERRO: Entrada Inválida");
                return false;
        }
        return true;
    }

    public static Boolean inserirInfoNaSeguradora(Seguradora seguradoraMain){
        ArrayList<Veiculo> listaVeiculo1 = new ArrayList<>();
        ArrayList<Veiculo> listaVeiculo2 = new ArrayList<>();
        ArrayList<Veiculo> listaVeiculo3 = new ArrayList<>();
        ArrayList<Veiculo> listaVeiculo4 = new ArrayList<>();


        Veiculo carro1 = new Veiculo("DAW-7825", "Ford", "Ecosport", 2013);
        Veiculo carro2 = new Veiculo("BKX-3170", "Renault", "Logan", 2017);
        Veiculo carro3 = new Veiculo("BHN-1507", "Fiat", "Uno", 2023);
        listaVeiculo1.add(carro1);
        listaVeiculo1.add(carro2);
        listaVeiculo1.add(carro3);

        Veiculo carro4 = new Veiculo("BSE-53", "Chevrolet", "Onix", 2021);
        Veiculo carro5 = new Veiculo("CAE-69", "Volkswagen", "Fox", 2009);
        Veiculo carro6 = new Veiculo("GAQ-9765", "Volkswagen", "Gol", 2001);
        listaVeiculo2.add(carro4);
        listaVeiculo2.add(carro5);
        listaVeiculo2.add(carro6);

        Veiculo carro7 = new Veiculo("EQB-4118", "Ford", "KA", 2013);
        Veiculo carro8 = new Veiculo("DTR-6116-", "Audi", "A3", 2017);
        Veiculo carro9 = new Veiculo("FKW-5855", "Renault", "Clio", 2023);
        listaVeiculo3.add(carro7);
        listaVeiculo3.add(carro8);
        listaVeiculo3.add(carro9);
        
        Veiculo carro10 = new Veiculo("GDS-163", "Hyundai", "HB20", 2018);
        Veiculo carro11 = new Veiculo("FRK-442", "Toyota", "Etios", 2014);
        Veiculo carro12 = new Veiculo("EGH-0883", "Chevrolet", "Prisma", 2007);
        listaVeiculo4.add(carro10);
        listaVeiculo4.add(carro11);
        listaVeiculo4.add(carro12);
        
        Frota frota1 = new Frota(listaVeiculo2);
        Frota frota2 = new Frota(listaVeiculo3);
        Frota frota3 = new Frota(listaVeiculo4);
        ArrayList<Frota> listaFrotas = new ArrayList<>();
        listaFrotas.add(frota1);
        listaFrotas.add(frota2);
        listaFrotas.add(frota3);

        ClientePF cliente1 = new ClientePF("Pedro da Silva", "(019)92314-1242", "oieoieoie@gmail.com","Rua Fisica 87", "847.850.640-30", "Masculino", "Superior completo", Validacao.p("25/12/2003"), listaVeiculo1);
        ClientePJ cliente2 = new ClientePJ("Empresa muito massa", "(082) 97869-4928", "empresamassa@gmail.com", "Rua massa 2023", "95.791.239/0001-33", Validacao.p("25/12/1992"), listaFrotas);

        seguradoraMain.cadastrarCliente(cliente1);
        seguradoraMain.cadastrarCliente(cliente2);

        SeguroPF seguropf1 = new SeguroPF(Validacao.p("20/10/2013"), Validacao.p("20/10/2033"), seguradoraMain, new ArrayList<>(), carro1, cliente1);
        SeguroPF seguropf2 = new SeguroPF(Validacao.p("06/09/2016"), Validacao.p("06/09/2036"), seguradoraMain, new ArrayList<>(), carro2, cliente1);
        SeguroPF seguropf3 = new SeguroPF(Validacao.p("12/03/2011"), Validacao.p("12/03/2031"), seguradoraMain, new ArrayList<>(), carro3, cliente1);

        SeguroPJ seguropj1 = new SeguroPJ(Validacao.p("09/09/2010"), Validacao.p("09/09/2030"), seguradoraMain, new ArrayList<>(), frota1, cliente2);
        SeguroPJ seguropj2 = new SeguroPJ(Validacao.p("06/04/2010"), Validacao.p("06/04/2030"), seguradoraMain, new ArrayList<>(), frota2, cliente2);
        SeguroPJ seguropj3 = new SeguroPJ(Validacao.p("05/10/2017"), Validacao.p("05/10/2037"), seguradoraMain, new ArrayList<>(), frota3, cliente2);

        seguradoraMain.gerarSeguro(seguropf1);
        seguradoraMain.gerarSeguro(seguropf2);
        seguradoraMain.gerarSeguro(seguropf3);
        seguradoraMain.gerarSeguro(seguropj1);
        seguradoraMain.gerarSeguro(seguropj2);
        seguradoraMain.gerarSeguro(seguropj3);

        Condutor condutor1 = new Condutor("964.461.760-66", "Julian Cavalcanti Cunha", "(14)92028-2462", "Rua Pereira Estéfano 222", "juluan222@gmail.com", Validacao.p("28/10/1980"), new ArrayList<>());
        Condutor condutor2 = new Condutor("427.907.150-09", "Guilherme Martins Almeida", "(11)92410-5554", "Rua Isolina 102", "guilherme102@gmail.com", Validacao.p("22/10/1983"), new ArrayList<>());
        Condutor condutor3 = new Condutor("654.073.120-50", "Nicolas Lima Martins", "(17)92876-5222", "Rua Porfírio Prado 67", "nicolas67@gmail.com", Validacao.p("09/05/2002"), new ArrayList<>());
        Condutor condutor4 = new Condutor("383.758.560-37", "Camila Sousa Castro", "(18)93580-1167", "Rua Emiliano Cardoso de Mello 536", "camila538@gmail.com", Validacao.p("18/07/1993"), new ArrayList<>());
        Condutor condutor5 = new Condutor("825.672.360-23", "Gabrielly Pinto Cunha", "(19)92410-0597", "Rua Mariano Galvão Bueno Trigueirinho 92", "gabrielly92@gmail.com", Validacao.p("23/02/1990"), new ArrayList<>());
        Condutor condutor6 = new Condutor("949.274.160-11", "Marcos Carvalho Oliveira", "(14)93836-5574", "Rua Gil Eanes 195", "marcos195@gmail.com", Validacao.p("17/07/1983"), new ArrayList<>());
        Condutor condutor7 = new Condutor("696.713.870-71", "Davi Oliveira Pereira", "(11)95443-3900", "Rua Isabella Canali 1003", "davi1003@gmail.com", Validacao.p("01/12/1972"), new ArrayList<>());
        Condutor condutor8 = new Condutor("355.395.430-25", "Alex Pinto Carvalho", "(19)98171-9424", "Rua dos Cristais 607", "alex607@gmail.com", Validacao.p("27/06/1982"), new ArrayList<>());
        Condutor condutor9 = new Condutor("255.460.930-64", "Luan Costa Ribeiro", "(31)92052-2071", "Rua Capitão Tenente 278", "luan278@gmail.com", Validacao.p("31/09/1958"), new ArrayList<>());
        Condutor condutor10 = new Condutor("840.422.660-10", "Igor Rocha Dias", "(24)98718-3366", "Rua Servidão Josépha Maria 107", "igor107@gmail.com", Validacao.p("27/09/1996"), new ArrayList<>());
        Condutor condutor11 = new Condutor("032.282.390-04", "Rafaela Cavalcanti Pereira", "(21)95615-4062", "Rua Levindo Lópes 1778", "rafaela1778@gmail.com", Validacao.p("17/09/1962"), new ArrayList<>());
        Condutor condutor12 = new Condutor("709.296.340-04", "Nicolas Dias Melo", "(17)94934-2461", "Rua Genoefa Ayres Dosualdo 123", "nicolas123@gmail.com", Validacao.p("01/11/1937"), new ArrayList<>());

        seguropf1.autorizarCondutor(condutor1);
        seguropf1.autorizarCondutor(condutor2);
        seguropf1.gerarSinistro("Rua Changai 947", "964.461.760-66");
        seguropf1.gerarSinistro("Rua Jurupité 491", "427.907.150-09");


        seguropf2.autorizarCondutor(condutor3);
        seguropf2.autorizarCondutor(condutor4);
        seguropf2.gerarSinistro("Rua Tulia Aragona 601 ", "654.073.120-50");
        seguropf2.gerarSinistro("Rua Marco Rutini 375", "383.758.560-37");


        seguropf3.autorizarCondutor(condutor5);
        seguropf3.autorizarCondutor(condutor6);
        seguropf3.gerarSinistro("Rua Anhaia de Lemos 863", "825.672.360-23");
        seguropf3.gerarSinistro("Rua Natividade da Serra 921", "949.274.160-11");

        seguropj1.autorizarCondutor(condutor7);
        seguropj1.autorizarCondutor(condutor8);
        seguropj1.gerarSinistro("Rua General Evandro 678 ", "696.713.870-71");
        seguropj1.gerarSinistro("Rua Conceição do Almeida 334", "355.395.430-25");

        seguropj2.autorizarCondutor(condutor9);
        seguropj2.autorizarCondutor(condutor10);
        seguropj2.gerarSinistro("Rua Managua 249", "255.460.930-64");
        seguropj2.gerarSinistro("Rua Cristo Operário 846", "840.422.660-10");

        seguropj3.autorizarCondutor(condutor11);
        seguropj3.autorizarCondutor(condutor12);  
        seguropj3.gerarSinistro("Rua Armando Pacheco Alves 23", "032.282.390-04");
        seguropj3.gerarSinistro("Rua Vito Chiarella 723", "709.296.340-04");
           
        // LISTAR POR SEGURADORA
        seguradoraMain.listarClientes(0);
        seguradoraMain.listarClientes(1);
        seguradoraMain.listarSeguros(0);
        seguradoraMain.listarSeguros(1);
        seguradoraMain.listarSinistros();
        seguradoraMain.listarVeiculos();
        seguradoraMain.listarFrotas();

        // LISTAR POR CLIENTE PF
        seguradoraMain.listarSinistrosPorCliente(cliente1.getCpf());
        seguradoraMain.listarSegurosPorCliente(cliente1.getCpf());
        seguradoraMain.listarVeiculosPorCliente(cliente1.getCpf());
        
        // LISTAR POR CLIENTE PJ

        seguradoraMain.listarSinistrosPorCliente(cliente2.getCnpj());
        seguradoraMain.listarSegurosPorCliente(cliente2.getCnpj());
        seguradoraMain.listarFrotasPorCliente(cliente2.getCnpj());
        seguradoraMain.listarVeiculosPorCliente(cliente2.getCnpj());
        
        // LISTAR POR SEGURO PF

        seguradoraMain.listarSinistrosPorSeguro(seguropf1.getId());
        seguradoraMain.listarCondutoresPorSeguro(seguropf1.getId());

        // LISTAR POR SEGURO PJ

        seguradoraMain.listarSinistrosPorSeguro(seguropj1.getId());
        seguradoraMain.listarCondutoresPorSeguro(seguropj1.getId());

        // RETIRO A RECEITA DA SEGURADORA
        System.out.println("\nA receita da seguradora é de "+seguradoraMain.calcularReceita()+".00 R$");
        
        return true;
    }

    public static void main(String[] args){

        listaSeguradoras.add(new Seguradora("17.694.201/0001-39", "Seguradora Amigos", "987654321", "Rua amigos 123", "seguamigos@gmail.com", new ArrayList<>(), new ArrayList<>()));
        Seguradora seguradoraMain = listaSeguradoras.get(0);

        seguradoraMain.lerDados();

        inserirInfoNaSeguradora(seguradoraMain);

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
