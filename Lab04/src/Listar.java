public enum Listar {
    CLIENTE_P_SEGURADORA (1) ,
    SINISTRO_P_SEGURADORA (2) ,
    SINISTRO_P_CLIENTE (3) ,
    VEICULO_P_CLIENTE (4) ,
    VEICULO_P_SEGURADORA (5) ,
    SAIR (0) ;

    public final int operacao ;

    Listar ( int operacao ) {
        this . operacao = operacao ;
    }

    public static Listar getReverso(int alvo){
        for(Listar enums:Listar.values()){
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