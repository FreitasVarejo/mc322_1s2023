public class Validacao {

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
    
    public static boolean checkarNome(String nome){
        for(int i = 0; nome.length() > i; ++i){
            if(!Character.isLetter(nome.charAt(i)) && nome.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }
}
