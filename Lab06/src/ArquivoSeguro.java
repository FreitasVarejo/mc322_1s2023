import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.FileInputStream;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class ArquivoSeguro {

    public static ArrayList<Seguro> lerArquivo(String localArquivo, ArrayList<ClientePF> listaClientePFs, ArrayList<Seguradora> listaSeguradoras, ArrayList<Sinistro> listaSinistros, ArrayList<Condutor> listaCondutores){
        try{
            FileInputStream arquivo = new FileInputStream(localArquivo);
            InputStreamReader input = new InputStreamReader(arquivo);
            BufferedReader  br = new BufferedReader(input);

            ArrayList<Seguro> listaSeguro = new ArrayList<>();
            String linha = br.readLine();
            linha = br.readLine();

            //ID_SEGURO,DATA_INICIO,DATA_FIM,CNPJ_SEGURADORA,VALOR_MENSAL,CPF_CLIENTE,PLACA_VEICULO,LISTA_SINISTRO,LISTA_CONDUTORES

            while(linha != null){
                String[] listaTermos = linha.split(",");

                int id = Integer.parseInt(listaTermos[0]);
                LocalDate dataInicio = LocalDate.parse(listaTermos[1], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate dataFinal = LocalDate.parse(listaTermos[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                Seguradora seguradora = Validacao.retornarSeguradora(listaTermos[3], listaSeguradoras);
                //int valormensal = Integer.parseInt(listaTermos[4]);

                ArrayList<Sinistro> listaSinistrosSeguro = new ArrayList<>();
                for(String idSinistro:listaTermos[7].split(";")){
                    listaSinistrosSeguro.add(Validacao.retornarSinistro(Integer.parseInt(idSinistro), listaSinistros));
                }

                ArrayList<Condutor> listaCondutoresSeguro = new ArrayList<>();
                for(String cpfCondutor:listaTermos[8].split(";")){
                    listaCondutoresSeguro.add(Validacao.retornarCondutor(cpfCondutor, listaCondutores));
                }

                if(listaTermos[5].replaceAll("[^0-9]", "").length() != 11){
                    // SEGURO PF

                    ClientePF cliente =((ClientePF)Validacao.retornarCliente(listaTermos[5], seguradora.getListaClientes()));
                    Veiculo veiculo = Validacao.retornarVeiculo(listaTermos[6], cliente.getListaVeiculos());

                    SeguroPF seguro = new SeguroPF(id, dataInicio, dataFinal, seguradora, listaCondutoresSeguro, veiculo, cliente);
                    seguro.setListaSinistros(listaSinistrosSeguro);
                    
                    listaSeguro.add(seguro);

                }else{
                    // SEGURO PJ

                    ClientePJ cliente =((ClientePJ)Validacao.retornarCliente(listaTermos[5], seguradora.getListaClientes()));
                    Frota frota = Validacao.retornarFrota(Integer.parseInt(listaTermos[6]), cliente.getListaFrotas());

                    SeguroPJ seguro = new SeguroPJ(id, dataInicio, dataFinal, seguradora, listaCondutoresSeguro, frota,  cliente);
                    seguro.setListaSinistros(listaSinistrosSeguro);

                    listaSeguro.add(seguro);
                }
            }

            System.out.println("Dados dos seguros gravados com sucesso em "+localArquivo);

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
            System.out.println("Erro ao gravar o arquivo: \n" + e);
            return null;
        }
    }
}
