import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.FileInputStream;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class ArquivoCondutor {

    public static ArrayList<Condutor> lerArquivo(String localArquivo, Seguradora seguradora){
        try{
            FileInputStream arquivo = new FileInputStream(localArquivo);
            InputStreamReader input = new InputStreamReader(arquivo);
            BufferedReader  br = new BufferedReader(input);

            ArrayList<Condutor> listaSeguro = new ArrayList<>();
            String linha = br.readLine();
            linha = br.readLine();

            //CPF,NOME,TELEFONE,ENDERECO,EMAIL,DATA_NASCIMENTO,LISTA_SINISTRO
            while(linha != null){
                String[] listaTermos = linha.split(",");

                String cpf = listaTermos[0];
                String nome = listaTermos[1];
                String telefone = listaTermos[2];
                String endereco = listaTermos[3];
                String email = listaTermos[4];
                LocalDate dataNascimento = LocalDate.parse(listaTermos[5], DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                for()

            }

            br.close();
            input.close();
            arquivo.close();

            return listaSeguro;
        }catch(Exception e){
            System.out.println("Erro ao ler o arquivo " + e);
            return null;
        }
    }

    public static Boolean gravarArquivo(String nomeArquivo, Seguradora seguradora){
        try{
            FileOutputStream arquivo = new FileOutputStream(nomeArquivo);
            PrintWriter pr = new PrintWriter(arquivo);

            //ID_SEGURO,DATA_INICIO,DATA_FIM,CNPJ_SEGURADORA,VALOR_MENSAL,CPF_CLIENTE,PLACA_VEICULO,LISTA_SINISTRO,LISTA_CONDUTORES

            pr.println("ID_SEGURO,DATA_INICIO,DATA_FIM,CNPJ_SEGURADORA,VALOR_MENSAL,CPF_CLIENTE,PLACA_VEICULO,LISTA_SINISTRO_ID,LISTA_CONDUTORES_CPF");

            for(Seguro seguro:seguradora.getListaSeguros()){
                String str = seguro.getId()+"";
                str += ","+seguro.getDataInicio();
                str += ","+seguro.getDataFim();
                str += ","+seguro.getSeguradora().getCnpj();
                str += ","+seguro.getValorMensal();
                if(seguro instanceof SeguroPF){
                    str += ","+((SeguroPF)seguro).getClientePF().getCpf();
                    str += ","+((SeguroPF)seguro).getVeiculo().getPlaca();
                }else if(seguro instanceof SeguroPJ){
                    str += ","+((SeguroPJ)seguro).getClientePJ().getCnpj();
                    str += ","+((SeguroPJ)seguro).getFrota().getCode();
                }
                

                str += ",";
                for(int i = 0; seguro.getListaSinistros().size() > i; ++i){
                    if(i > 0){
                        str += ";";
                    }
                    str += seguro.getListaSinistros().get(i).getId();
                }

                str += ",";
                for(int i = 0; seguro.getListaCondutores().size() > i; ++i){
                    if(i > 0){
                        str += ";";
                    }
                    str += seguro.getListaCondutores().get(i).getCpf();
                }

                pr.println(str);
            }

            pr.close();
            arquivo.close();

            return true;
        }catch(Exception e){
            System.out.println("Erro ao ler o arquivo: \n" + e);
            return null;
        }
    }
}
