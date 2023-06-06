import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Validacao {

    // Pego dois documentos (CPF/CNPJ) e comparo os dois, independente das formação de caractéres, apenas dígitos
    public static boolean compararDocumentos(String documento1, String documento2){
        String docFatorado1 = documento1.replaceAll("[^0-9]", "");
        String docFatorado2 = documento2.replaceAll("[^0-9]", "");

        if(docFatorado1.equals(docFatorado2)){
            return true;
        }else{
            return false;
        }
    }

    // Retorno o LocalDate equivalente para uma string (é para economizar espaço nas linhas)
    public static LocalDate p(String stringData){
        return LocalDate.parse(stringData, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static boolean validarCpf(String cpf){
        // Retiro todos os caracteres não unitários
        String newcpf = cpf.replaceAll("[^0-9]", "");

        // Confiro se há 11 dígitos
        if(newcpf.length() != 11){
            return false;
        }

        // Confiro se todos os dígitos não são iguais

        boolean checkar_tudo_igual = true;
        for(int i = 1; newcpf.length() > i; ++i){
            checkar_tudo_igual &= (newcpf.charAt(0)==newcpf.charAt(i)); 
        }

        if(checkar_tudo_igual) return false;

        // Testo a validade dos últimos dois dígitos do cpf

        int digito1 = 0, digito2 = 0;

        for(int i = 0; 9 > i; ++i){
            digito1 += (10-i)*(newcpf.charAt(i)-'0');
            digito2 += (10-i)*(newcpf.charAt(i+1)-'0');
        }

        digito1 = (digito1%11==0||digito1%11==1)?0:11-digito1%11;
        digito2 = (digito2%11==0||digito2%11==1)?0:11-digito2%11;

        if(digito1 == newcpf.charAt(9)-'0' &&
        digito2 == newcpf.charAt(10)-'0'){
            return true;
        }

        return false;

    }

    public static boolean validarCnpj(String cnpj){
        // Retiro todos os caracteres não unitários
        String newCnpj = cnpj.replaceAll("[^0-9]", "");

        // Confiro se há 14 dígitos
        if(newCnpj.length() != 14){
            return false;
        }

        // Confiro se todos os dígitos não são iguais

        boolean checkar_tudo_igual = true;
        for(int i = 1; newCnpj.length() > i; ++i){
            checkar_tudo_igual &= (newCnpj.charAt(0)==newCnpj.charAt(i)); 
        }

        if(checkar_tudo_igual) return false;

        // Testo a validade dos últimos dois dígitos do cnpj

        int digito1 = 0, digito2 = 0;

        int []chave = { 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        
        digito2 = 6 * (newCnpj.charAt(0)-'0');

        for(int i = 0; 11 >= i; ++i){
            digito1 += chave[i]*(newCnpj.charAt(i)-'0');
            digito2 += chave[i]*(newCnpj.charAt(i+1)-'0');
        }

        digito1 = (digito1%11==0||digito1%11==1)?0:11-digito1%11;
        digito2 = (digito2%11==0||digito2%11==1)?0:11-digito2%11;

        if(digito1 == newCnpj.charAt(12)-'0' &&
        digito2 == newCnpj.charAt(13)-'0'){
            return true;
        }

        return false;

    }
    
    // Checka se apenas há letras ou espaço em uma string para a checagem de nomes
    public static boolean checkarNome(String nome){
        for(int i = 0; nome.length() > i; ++i){
            if(!Character.isLetter(nome.charAt(i)) && nome.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }


    /*  Retorna o índice de um objeto com tal dado

     *  FUNÇÃO IMPORTANTISSIMA, ATENÇÃO
     * 
     *  Cada classe tem um compoente o qual deve ser de valor diferente para cada objeto de um mesmo conjunto, 
     *  portanto, usando esse componente podemos tanto PROCUAR O ÍNDICE(1) desse objeto em uma lista ou
     *  CHECKAR SE UM OBJETO COM TAL COMPONENTE EXISTE(2)
     * 
     *  Eu destaco essa função pois ela é usada dezenas de vezes com o código abaixo pelo código, e se 
     *  comentasse a cada iteração dela os comentários ficariam repetitivos e esconderiam mudanças reais
     *  que eu fizesse ao uso padrão dessas funções
     *  
     *  EXEMPLO EM PSEUDOCÓDIGO:
     * 
     *  Veiculo encontrarVeiculo(ArrayList<Veiculo> listaVeiculo, string placaVeiculo)
     *          idx = indiceVeiculoNaLista(placa, listaVeiculo)
     *          if(idx == -1):
     *                  ->VEICULO DE PLACA placaVeiculo NÃO EXITE NA LISTA
     *          else:
     *                  ->VEICULO DE PLACA placaVeiculo EXISTE NA LISTA E TEM ÍNDICE idx
     * 
     */

    public static int indiceDoVeiculoNaLista(String placa, ArrayList <Veiculo> listaVeiculos){
        for(int i = 0; listaVeiculos.size() > i; ++i){
            if(listaVeiculos.get(i).getPlaca().equals(placa)){
                // retorno índice do objeto caso sua placa seja igual ao que estamos procurando
                return i;
            }
        }
        // retorno -1 caso não ache veículo na lista com placa igual ao que estamos procurando
        return -1;
    }

    public static int indiceDoCondutorNaLista(String cpf, ArrayList <Condutor> listaCondutor){

        for(int i = 0; listaCondutor.size() > i; ++i){
            if(compararDocumentos(listaCondutor.get(i).getCpf(), cpf)){
                return i;
            }
        }
        return -1;
    }

    public static int indiceDeSeguroNaLista(int id, ArrayList <Seguro> listaSeguro){
        for(int i = 0; listaSeguro.size() > i; ++i){
            if(listaSeguro.get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    public static int indiceDeSinistroNaLista(int id, ArrayList <Sinistro> listaSinistro){
        for(int i = 0; listaSinistro.size() > i; ++i){
            if(listaSinistro.get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    public static int indiceDaFrotaNaLista(int code, ArrayList <Frota> listaFrotas){
        for(int i = 0; listaFrotas.size() > i; ++i){
            if(listaFrotas.get(i).getCode() == code){
                return i;
            }
        }
        return -1;
    }

    public static int indiceDaSeguradoraNaLista(String cnpj, ArrayList <Seguradora> listaSeguradora){
        for(int i = 0; listaSeguradora.size() > i; ++i){
            if(listaSeguradora.get(i).getCnpj().equals(cnpj)){
                return i;
            }
        }
        return -1;
    }

    public static int indiceDeClienteNaLista(String documento, ArrayList <Cliente> listaClientes){
         for(int i = 0; listaClientes.size() > i; ++i){
            if(compararDocumentos(listaClientes.get(i).getDocumento(), documento)){
                return i;
            }
        } 
        return -1;
    }    
}
