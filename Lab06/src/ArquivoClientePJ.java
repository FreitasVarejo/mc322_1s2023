import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class ArquivoClientePJ {

    public static ArrayList<ClientePJ> lerArquivo(String localArquivo, ArrayList<Frota> listaFrotasEntrada){
        try{
            FileInputStream arquivo = new FileInputStream(localArquivo);
            InputStreamReader input = new InputStreamReader(arquivo);
            BufferedReader  br = new BufferedReader(input);

            ArrayList<ClientePJ> listaClientePJ = new ArrayList<>();
            String linha = br.readLine();
            linha = br.readLine();

            //CNPJ_CLIENTE,NOME_CLIENTE,TELEFONE,ENDERECO,EMAIL_CLIENTE,DATA_FUND,CODE_FROTA

            while(linha != null){
                String[] listaTermos = linha.split(",");
                String cnpj = listaTermos[0];
                String nome = listaTermos[1];
                String telefone = listaTermos[2];
                String endereco = listaTermos[3];
                String email = listaTermos[4];
                LocalDate dataFundacao = LocalDate.parse(listaTermos[5], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                
                ArrayList<Frota> listaFrotasCliente = new ArrayList<>(); 
                for(int i = 6; listaTermos.length > i; ++i){
                    int idx = Validacao.indiceDaFrotaNaLista(Integer.parseInt(listaTermos[i]), listaFrotasEntrada);
                    listaFrotasCliente.add(listaFrotasEntrada.get(idx));
                }

                listaClientePJ.add(new ClientePJ(nome, telefone, email, endereco, 
                cnpj, dataFundacao, listaFrotasCliente));

                linha = br.readLine();
            }

            br.close();
            input.close();
            arquivo.close();

            return listaClientePJ;
        }catch(Exception e){
            System.out.println("Erro ao ler o arquivo " + e);
            return null;
        }
    }

    public static Boolean gravarArquivo(String localArquivo, Seguradora seguradora){
        try{
            FileOutputStream arquivo = new FileOutputStream(localArquivo);
            PrintWriter pr = new PrintWriter(arquivo);

            pr.println("CNPJ_CLIENTE,NOME_CLIENTE,TELEFONE,ENDERECO,EMAIL_CLIENTE,DATA_FUND,CODE_FROTA");

            for(Cliente cliente:seguradora.getListaClientes()){
                if(cliente instanceof ClientePJ){
                    String str = ((ClientePJ)cliente).getCnpj();
                    str += ","+((ClientePJ)cliente).getNome();
                    str += ","+((ClientePJ)cliente).getTelefone();
                    str += ","+((ClientePJ)cliente).getEndereco();
                    str += ","+((ClientePJ)cliente).getEmail();
                    str += ","+((ClientePJ)cliente).getDataFundacao();
                    for(Frota frota:((ClientePJ)cliente).getListaFrotas()){
                        str += ","+frota.getCode();
                    }
                    pr.println(str);
                }
            }

            pr.close();
            arquivo.close();
            System.out.println("Dados dos clientes PJ gravados com sucesso em "+localArquivo);

            return true;
        }catch(Exception e){
            System.out.println("Erro ao ler o arquivo: \n" + e);
            return null;
        }
    }
}
