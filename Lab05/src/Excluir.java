public enum Excluir {
    CLIENTE (1) ,
    SEGURADORA (2) ,
    VEICULO (3) ,
    FROTA (4) ,
    SEGURO (5),
    CONDUTOR (6),
    SINISTRO (7),
    SAIR (0) ;


    public final int operacao ;

    Excluir ( int operacao ) {
        this . operacao = operacao ;
    }

    public static Excluir getReverso(int alvo){
        for(Excluir enums:Excluir.values()){
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