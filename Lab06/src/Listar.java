public enum Listar {
    CLIENTE_P_SEGURADORA (1) ,
    SEGURO_P_SEGURADORA (2) ,
    SINISTRO_P_SEGURADORA (3) ,
    VEICULO_P_SEGURADORA (4) ,
    FROTA_P_SEGURADORA (5) ,

    SINISTRO_P_CLIENTE (6) ,
    SEGURO_P_CLIENTE (7) ,
    FROTA_P_CLIENTE (8) ,
    VEICULO_P_CLIENTE (9) ,
    

    SINISTRO_P_SEGURO (10) ,
    CONDUTOR_P_SEGURO(11) ,
    
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