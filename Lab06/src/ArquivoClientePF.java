import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.FileInputStream;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

//public class ArquivoClientePF implements ArquivoInterface {
public class ArquivoClientePF {
    public static ArrayList<ClientePF> lerArquivo(String localArquivo, ArrayList<?> listaVeiculosEntrada){
        try{
            FileInputStream arquivo = new FileInputStream(localArquivo);
            InputStreamReader input = new InputStreamReader(arquivo);
            BufferedReader  br = new BufferedReader(input);

            ArrayList<ClientePF> listaClientePF = new ArrayList<>();
            String linha = br.readLine();
            linha = br.readLine();

            // CPF_CLIENTE,NOME_CLIENTE,TELEFONE_CLIENTE,ENDERECO_CLIENTE,EMAIL_CLIENTE,SEXO,ENSINO,DATA_NASCIMENTO,PLACA_VEICULO_CLIENTE1

            while(linha != null){
                String[] listaTermos = linha.split(",");
                String cpf = listaTermos[0];
                String nome = listaTermos[1];
                String telefone = listaTermos[2];
                String endereco = listaTermos[3];
                String email = listaTermos[4];
                String genero = listaTermos[5];
                String educacao = listaTermos[6];
                LocalDate dataNascimento = LocalDate.parse(listaTermos[7], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                
                ArrayList<Veiculo> listaVeiculoCliente = new ArrayList<>(); 
                for(int i = 8; listaTermos.length > i; ++i){
                    int idx = Validacao.indiceDoVeiculoNaLista(listaTermos[i], listaVeiculosEntrada);
                    listaVeiculoCliente.add(listaVeiculosEntrada.get(idx));
                }

                listaClientePF.add(new ClientePF(nome, telefone, email, endereco, cpf, genero, educacao, dataNascimento, listaVeiculoCliente));

                linha = br.readLine();
            }

            br.close();
            input.close();
            arquivo.close();

            System.out.println("Dados dos clientes PF gravados com sucesso em "+localArquivo);

            return listaClientePF;
        }catch(Exception e){
            System.out.println("Erro ao ler o arquivo " + e);
            return null;
        }
    }

    public static Boolean gravarArquivo(String nomeArquivo, Seguradora seguradora){
        try{
            FileOutputStream arquivo = new FileOutputStream(nomeArquivo);
            PrintWriter pr = new PrintWriter(arquivo);

            pr.println("CPF_CLIENTE,NOME_CLIENTE,TELEFONE_CLIENTE,ENDERECO_CLIENTE,EMAIL_CLIENTE,SEXO,ENSINO,DATA_NASCIMENTO,PLACA_VEICULO_CLIENTE1");

            for(Cliente cliente:seguradora.getListaClientes()){
                if(cliente instanceof ClientePF){
                    String str = ((ClientePF)cliente).getCpf();
                    str += ","+((ClientePF)cliente).getNome();
                    str += ","+((ClientePF)cliente).getTelefone();
                    str += ","+((ClientePF)cliente).getEndereco();
                    str += ","+((ClientePF)cliente).getEmail();
                    str += ","+((ClientePF)cliente).getGenero();
                    str += ","+((ClientePF)cliente).getEducacao();
                    str += ","+((ClientePF)cliente).getDataNascimento();
                    for(Veiculo veiculo:((ClientePF)cliente).getListaVeiculos()){
                        str += ","+veiculo.getPlaca();
                    }
                    pr.println(str);
                }
            }

            pr.close();
            arquivo.close();

            return true;
        }catch(Exception e){
            System.out.println("Erro ao gravar o arquivo: \n" + e);
            return null;
        }
    }
}
