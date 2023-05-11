public enum Excluir {
    CLIENTE (1) ,
    VEICULO (2) ,
    SINISTRO (3) ,
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