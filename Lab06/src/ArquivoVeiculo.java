import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.FileInputStream;

public class ArquivoVeiculo {
    //"lab06-seguradora_arquivos_v2/veiculos.csv"

    public static ArrayList<Veiculo> lerArquivo(String localArquivo){
        try{
            FileInputStream arquivo = new FileInputStream(localArquivo);
            InputStreamReader input = new InputStreamReader(arquivo);
            BufferedReader  br = new BufferedReader(input);

            ArrayList<Veiculo> listaVeiculos = new ArrayList<>();
            String linha = br.readLine();
            linha = br.readLine();
            
            //PLACA,MARCA,MODELO,ANO_FAB

            while(linha != null){
                String[] listaTermos = linha.split(",");
                String placa = listaTermos[0];
                String marca = listaTermos[1];
                String moodelo = listaTermos[2];
                int anoFabricacao =Integer.parseInt(listaTermos[3]);

                listaVeiculos.add(new Veiculo(placa,marca,moodelo,anoFabricacao));
                linha = br.readLine();
            }

            br.close();
            input.close();
            arquivo.close();
            
            return listaVeiculos;
        }catch(Exception e){
            System.out.println("Erro ao ler o arquivo: \n" + e);
            return null;
        }
    }

    public static Boolean gravarArquivo(String localArquivo, Seguradora seguradora){
        try{
            FileOutputStream arquivo = new FileOutputStream(localArquivo);
            PrintWriter pr = new PrintWriter(arquivo);

            pr.println("PLACA,MARCA,MODELO,ANO_FAB");

            for(Cliente cliente:seguradora.getListaClientes()){
                for(Veiculo veiculo:cliente.getListaVeiculos()){
                    pr.println(veiculo.getPlaca()+","+veiculo.getMarca()+","+veiculo.getModelo()+","+veiculo.getAnoFabricacao());
                }
            }

            pr.close();
            arquivo.close();

            System.out.println("Dados dos veiculos gravados com sucesso em "+localArquivo);


            return true;
        }catch(Exception e){
            System.out.println("Erro ao ler o arquivo: \n" + e);
            return null;
        }
    }
}
