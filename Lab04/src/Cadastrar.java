public enum Cadastrar {
    CLIENTE (1) ,
    SEGURADORA (2) ,
    VEICULO (3) ,
    SAIR (0) ;

    public final int operacao ;

    Cadastrar ( int operacao ) {
        this . operacao = operacao ;
    }

    public static Cadastrar getReverso(int alvo){
        for(Cadastrar enums:Cadastrar.values()){
            if(alvo == enums.getOperacao()){
                return enums;
            }
        }
        return null;
    }

    public int getOperacao () {
        return operacao ;
    } 
}
