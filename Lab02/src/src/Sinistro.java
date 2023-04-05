public class Sinistro {
    private int id ;
    private String data ;
    private String endereco ;

    public static int contador = 1;

    //Construtor

    public Sinistro (String data , String endereco ) {
        this.id = contador ;
        contador++;
        this.data = data ;
        this.endereco = endereco ;
    }

    // Getters e setters

    public int getId () {
        return id ;
    }

    public String getData () {
        return data ;
    }

    public String getEndereco () {
        return endereco ;
    }

    public void setId ( int id ) {
        this.id = id ;
    }
    
    public void setData ( String data ) {
        this.data = data ;
    }

    public void setEndereco( String endereco ) {
        this.endereco = endereco ;
    }

}