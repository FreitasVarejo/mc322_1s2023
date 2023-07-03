import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.FileInputStream;

public class ArquivoFrota {
    //"lab06-seguradora_arquivos_v2/frotas.csv"

    public static ArrayList<Frota> lerArquivo(String localArquivo, ArrayList<Veiculo> listaVeiculosEntrada){
        try{
            FileInputStream arquivo = new FileInputStream(localArquivo);
            InputStreamReader input = new InputStreamReader(arquivo);
            BufferedReader  br = new BufferedReader(input);

            ArrayList<Frota> listaFrotas = new ArrayList<>();
            String linha = br.readLine();
            linha = br.readLine();
            
            //ID_FROTA,PLACA_VEICULO1,PLACA_VEICULO2,PLACA_VEICULO3

            while(linha != null){
                
                //System.out.println(linha);
                String[] listaTermos = linha.split(",");
                ArrayList<Veiculo> listaVeiculosDaFrota = new ArrayList<>();
                int code = Integer.parseInt(listaTermos[0]);
                
                //System.out.println("\nTAMANHO:  "+listaTermos.length);
                for(int i = 1; listaTermos.length > i; ++i){
                    int idx = Validacao.indiceDoVeiculoNaLista(listaTermos[i], listaVeiculosEntrada);
                    listaVeiculosDaFrota.add(listaVeiculosEntrada.get(idx));
                }

                Frota frota = new Frota(code, listaVeiculosDaFrota);
                //Frota.setContador(code+1);

                listaFrotas.add(frota);
                linha = br.readLine();
            }

            br.close();
            input.close();
            arquivo.close();
            
            return listaFrotas;
        }catch(Exception e){
            System.out.println("Erro ao ler o arquivo: \n" + e);
            return null;
        }
    }

    public static Boolean gravarArquivo(String localArquivo, Seguradora seguradora){
        try{
            FileOutputStream arquivo = new FileOutputStream(localArquivo);
            PrintWriter pr = new PrintWriter(arquivo);

            pr.println("ID_FROTA,PLACA_VEICULO1,PLACA_VEICULO2,PLACA_VEICULO3");

            for(Cliente cliente:seguradora.getListaClientes()){
                if(cliente instanceof ClientePJ){
                    for(Frota frota:((ClientePJ)cliente).getListaFrotas()){
                        String str = frota.getCode()+"";
                        for(Veiculo veiculo:frota.getListaVeiculos()){
                            str += ","+veiculo.getPlaca();
                        }
                        pr.println(str);
                    }
                }
            }

            pr.close();
            arquivo.close();

            System.out.println("Dados das frotas gravados com sucesso em "+localArquivo);


            return true;
        }catch(Exception e){
            System.out.println("Erro ao gravar o arquivo: \n" + e);
            return null;
        }
    }
}

