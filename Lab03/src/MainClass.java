import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainClass {
    //Scanner global que abro e fecho na main
    static Scanner scanner = new Scanner(System.in);

    /*
     * Main foir feita no formato de menu, toda vez que uma operação é encerrada é 
     * ido de volta ao menu e encerra a main ao inserir 7 no input do menu
     */
    public static void main(String[] args) {
        int comando = 99;

        // Antes de chegar no menu propramente dito é inicializada a seguradora a qual
        // será usada pela resto do código
        System.out.println("> INICIALIZANDO PROGRAMA");
        Seguradora seguradora = inserirSeguradora();

        // Menu contido na while:
        while (comando != 7) {
            System.out.print("\nPressione a tecla enter para continuar . . .");
            scanner.nextLine();

            System.out.println("\n-----------------MENU PRINCIPAL-----------------");
            System.out.println("\nDigite um dos números a seguir para realizar uma tarefa:");
            System.out.println("[1] Cadastrar um cliente");
            System.out.println("[2] Remover um cliente");
            System.out.println("[3] listar clientes");
            System.out.println("[4] gerar sinistro");
            System.out.println("[5] vizualizar um sinistro");
            System.out.println("[6] listar sinistros");
            System.out.println("[7] encerrar programa");
            comando = scanner.nextInt();
            scanner.nextLine();
            if (comando == 1) {
                seguradora.getClientes().add(inserirInfoCliente(seguradora));
            } else if (comando == 2) {
                System.out.print("\nInsira o documento do cliente: ");//
                String documento = scanner.nextLine();
                seguradora.removerCliente(documento);
            } else if (comando == 3) {
                int digito = pegarDigito();
                seguradora.listarClientes(digito);
            } else if (comando == 4) {
                seguradora.gerarSinistro(scanner);
            } else if (comando == 5) {
                System.out.print("\nInsira o documento do cliente envolvido no sinistro: ");
                String documento = scanner.nextLine();
                seguradora.vizualizarSinistro(documento);
            } else if (comando == 6) {
                seguradora.listarSinistros();
            } else if (comando == 7) {
                System.out.println("> ENCERRANDO PROGRAMA");
            } else {
                System.out.println("> COMANDO INVÁLIDO");
            }
        }

        // Fecho o scanner no final do programa
        scanner.close();
    }
    
    /*
     * Leio as informações da seguradora e retorno ela feita
     */
    public static Seguradora inserirSeguradora() {
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

        Seguradora seguradora = new Seguradora(nome, telefone, email, endereço, clientes, sinistros);

        return seguradora;
    }

    /*
     * Leio os veículos de um certo cliente e gero uma lista com eles contidos nela
     */
    static ArrayList<Veiculo> inserirVeiculos() {
        ArrayList<Veiculo> lv = new ArrayList<>();

        System.out.print("Quantos carros deseja adicionar?: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        String placa, marca, modelo;
        int anoFabricacao;

        for (int i = 1; n >= i; ++i) {
            System.out.println("\nVEÍCULO " + i + ": ");
            System.out.print("Placa: ");
            placa = scanner.nextLine();
            System.out.print("Marca: ");
            marca = scanner.nextLine();
            System.out.print("Modelo: ");
            modelo = scanner.nextLine();
            System.out.print("Ano de fabricação: ");
            anoFabricacao = scanner.nextInt();
            scanner.nextLine();
            lv.add(new Veiculo(placa, marca, modelo, anoFabricacao));
        }

        return lv;
    }

    /*
     * Pergunta ao usuário se deve lidar com pessoa física ou jurídica e retorna o resultado
     */
    public static int pegarDigito() {
        System.out.print("\nDigite F para pessoa física e J para pessoa jurídica: ");
        char p = scanner.next().charAt(0);
        scanner.nextLine();

        if (p == 'f' || p == 'F') {
            return 0;
        }

        if (p == 'j' || p == 'J') {
            return 1;
        }

        System.out.println("> ENTRADA INVÁLIDA, TENTE NOVAMENTE");

        return pegarDigito();
    }

    /*
     * Insiro as informaçoes do cliente  e retorno ele feito
     */
    public static Cliente inserirInfoCliente(Seguradora seguradora) {
        System.out.println("\n> INICIANDO CADASTRO");

        // Pergunto se o cliente é pessoa física o jurídica
        int p = pegarDigito();

        if (p == 0) { //PESSOA FÍSICA
            String nome, endereco, cpf, genero, classeEconomica, educacao;
            LocalDate dataNascimento, dataLicenca;

            //Insirio as informaçoes do cliente
            System.out.println("\nInsira as informações do cliente ");
            System.out.print("Nome: ");
            nome = scanner.nextLine();
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
            ClientePF clnt = new ClientePF(nome, endereco, veiculos, cpf, genero, dataLicenca,
                    dataNascimento, classeEconomica, educacao);

            // Caso o cpf dê inválido ou repetido eu sigo inserindo novos até dar um válido e disponível
            while (!clnt.validarCpf() || seguradora.encontrarCliente(clnt.getCpf()) != null) {
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
            return clnt;
        }

        //PESSOA JURÍDICA

        //Insirio as informaçoes do cliente
        String nome, endereco, cnpj;
        LocalDate dataFundacao;
        System.out.print("Nome: ");
        nome = scanner.nextLine();
        System.out.print("Endereço: ");
        endereco = scanner.nextLine();
        System.out.print("Data de fundação: ");
        dataFundacao = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.print("CNPJ: ");
        cnpj = scanner.nextLine();
        
        // Declaro o array de carros vazio
        ArrayList<Veiculo> veiculos = new ArrayList<>();

        // Instancio o cliente com todas as informações dadas e com a lista de carros vazia
        ClientePJ clnt = new ClientePJ(nome, endereco, veiculos, cnpj, dataFundacao);

        // Caso o cNPJ dê inválido ou repetido eu sigo inserindo novos até dar um válido e disponível
        while (!clnt.validarCnpj() || seguradora.encontrarCliente(clnt.getCnpj()) != null) {
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
        return clnt;
    }
}
