//import java.util.Scanner;

public class MainClass{
    public static void main(String[] args){
        // Testando classe cliente 

        Cliente cliente_1 = new Cliente(
            "Gabriel Freitas", 
            "807.666.923-53", 
            "16/02/1993", 
            30, 
            "Rua dos Amigos N 100");

        if(cliente_1.validarCPF()){
            System.out.println("CPF VÀLIDO");
            System.out.println(cliente_1.toString());
        }else{
            System.out.println("CPF INVÀLIDO");
        }

        System.out.print("ANTIGO CPF = ");
        System.out.println(cliente_1.getCpf());

        cliente_1.setCpf("803.504.303-06");

        System.out.print("NOVO CPF = ");
        System.out.println(cliente_1.getCpf());
        
        System.out.println("");
        // Testando classe seguradora

        Seguradora seguradora_1 = new Seguradora( 
            "Segur a dora" , 
            "(085) 95392-4234" , 
            "gokusupersaiajindeusupersyajin@gmail.com" , 
            "Rua dos Amigos N100" );
        
        System.out.print("ANTIGO EMAIL = ");
        System.out.println(seguradora_1.getEmail());

        seguradora_1.setEmail("segur_a_dora@gmail.com");

        System.out.print("NOVO EMAIL = ");
        System.out.println(seguradora_1.getEmail());

        System.out.println("");
        // Testando classe sinistro

        Sinistro sinistro_1 = new Sinistro(
            "01/01/2023", 
            "Rua Shiego Mori 123" );
        
        System.out.print("ANTIGO ENDEREÇO = ");
        System.out.println(sinistro_1.getEndereco());

        sinistro_1.setEndereco("Rua dos Bobos 100");

        System.out.print("NOVO ENDEREÇO = ");
        System.out.println(sinistro_1.getEndereco());


        System.out.println("");
        // Testando classe Veículo

        Veiculo veiculo_1 = new Veiculo( 
            "GTK-9676" , 
            "Uno" , 
            "1995");
        
        System.out.print("ANTIGA PLACA = ");
        System.out.println(veiculo_1.getPlaca());

        veiculo_1.setPlaca("KQN-2643");

        System.out.print("NOVA PLACA = ");
        System.out.println(veiculo_1.getPlaca());

    }

    
}