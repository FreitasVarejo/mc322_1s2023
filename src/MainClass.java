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

        System.out.println("ANTIGO CPF = "+cliente_1.getCpf());
        cliente_1.setCpf("803.504.303-06");
        System.out.println("NOVO CPF = "+cliente_1.getCpf()+"\n");

        // Testando classe seguradora

        Seguradora seguradora_1 = new Seguradora( 
            "Segur a dora" , 
            "(085) 95392-4234" , 
            "gokusupersayajindeusupersayajin@gmail.com" , 
            "Rua dos Amigos 123" );
        
        System.out.println("ANTIGO EMAIL = "+seguradora_1.getEmail());
        seguradora_1.setEmail("segur_a_dora@gmail.com");
        System.out.println("NOVO EMAIL = "+seguradora_1.getEmail()+'\n');

        // Testando classe Veículo

        Veiculo veiculo_1 = new Veiculo( 
            "GTK-9676" , 
            "Uno" , 
            "1995");
        
        System.out.println("ANTIGA PLACA = " +veiculo_1.getPlaca());
        veiculo_1.setPlaca("KQN-2643");
        System.out.println("NOVA PLACA = "+veiculo_1.getPlaca()+'\n');

        // Testando classe sinistro

        Sinistro sinistro_1 = new Sinistro(
            "01/01/2023", 
            "Rua Shiego Mori 123" );
        
        System.out.println("ANTIGO ENDEREÇO = "+sinistro_1.getEndereco());

        sinistro_1.setEndereco("Rua dos Bobos 100");

        System.out.println("NOVO ENDEREÇO = " + sinistro_1.getEndereco()+"\n");

        for(int i = 1; 5 >= i; ++i){
            System.out.println("ID DO " + i+  "-ÈSIMO SINISTRO : "+sinistro_1.getId());
            sinistro_1 = new Sinistro(
            "01/01/2023", 
            "Rua Shiego Mori 123" );
        }

    }

    
}