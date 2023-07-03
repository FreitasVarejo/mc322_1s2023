public enum MenuOperacoes {
    CADASTROS (1) ,
    LISTAR (2) ,
    EXCLUIR (3) ,
    GERAR_SINISTRO (4) ,
    CALCULA_RECEITA_SEGURADORA (5) ,
    GRAVAR_DADOS_SEGURADORA(6),
    SAIR (0) ;

    public final int operacao ;

    MenuOperacoes ( int operacao ) {
        this . operacao = operacao ;
    }

    public static MenuOperacoes getReverso(int alvo){
        for(MenuOperacoes enums:MenuOperacoes.values()){
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