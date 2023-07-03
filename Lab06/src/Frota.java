import java.util.ArrayList;

public class Frota {
    private final int code ;
    private ArrayList <Veiculo> listaVeiculos;

    private static int contador_de_code = 0;

    //Construtor

    public Frota(ArrayList<Veiculo> listaVeiculos){
        // Código de frota começa em 1 e vai indo de um em um conforme adicionamos novas
        this.code = contador_de_code; contador_de_code++;
        this.listaVeiculos = listaVeiculos;
    }

    public Frota(int codeEntrada,ArrayList<Veiculo> listaVeiculos){
        // Código de frota começa em 1 e vai indo de um em um conforme adicionamos novas
        this.code = codeEntrada; contador_de_code = Math.max(codeEntrada+1, contador_de_code+1);
        this.listaVeiculos = listaVeiculos;
    }
    

    // Função toString

    public String toString() {
        String retornar = "Código de frota: "+this.code + " ("+this.listaVeiculos.size() +" VEÍCULOS)";

        for(Veiculo v:listaVeiculos){
            retornar += "\n" + v.toString();
        }

        return retornar;
    }

    // Adicionar e nremover veículos

    public Boolean adicionarVeiculo(Veiculo veiculo){
        if(Validacao.indiceDoVeiculoNaLista(veiculo.getPlaca(), this.listaVeiculos) != -1){
            System.out.println("\n>ERRO: Veículo da placa " + veiculo.getPlaca() + " já foi adicionado nessa frota.");
            return false;
        }        
        
        this.listaVeiculos.add(veiculo);
        System.out.println("\nVeículo de placa " + veiculo.getPlaca() + " foi adicionado na frota " + this.code + " com sucesso.");
        return true;
    }

    public Boolean removerVeiculo(String placaCarro){
        int idx = Validacao.indiceDoVeiculoNaLista(placaCarro, this.listaVeiculos);

        if(idx == -1){
            System.out.println("\n>ERRO: Não há veículo da placa " + placaCarro + " nessa frota.");
            return false;
        }

        this.listaVeiculos.remove(idx);
        System.out.println("\nVeículo de placa " + placaCarro + " foi removido da frota " + this.code + " com sucesso.");
        return true;
    }

    // Getters e setters

    public static void setContador(int x){
        if(x >= contador_de_code){
            contador_de_code = x+1;
        }
    }

    public static void setCode(int x){
        contador_de_code = x;
    }
    
    public int getCode() {
        return this.code;
    }

    public ArrayList<Veiculo> getListaVeiculos() {
        return this.listaVeiculos;
    }

    public void setListaVeiculos(ArrayList<Veiculo> listaVeiculos) {
        this.listaVeiculos = listaVeiculos;
    }
    
}