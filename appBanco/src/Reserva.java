import java.util.Scanner;

// OBS: o número da conta Corresponde ao contPf ou ContCnpj

public class Reserva {
    public static void main(String[] args) {

        Object[] contasPessoaFisica = new Object[5];   // Salvar contas Pessoas Físicas
        Object[] contasPessoaJuridicas = new Object[5];    // salvar contas pessoas jurídicas
        Object [] tipoConta = {contasPessoaFisica, contasPessoaJuridicas};


        // Informacão Cliente
        String nome;
        String cpfCnpj;

        Scanner input = new Scanner(System.in);
        int opcaoMenu;
        int contPf = 0;
        int contPj = 0;
        int subTipoConta;

        int numeroConta;
        double valor;
        String [] cliente;
        Object [] conta;
        int tipo;

        do {
            menu();
            opcaoMenu = Integer.parseInt(input.nextLine());

            if( opcaoMenu != 5){
                if( opcaoMenu == 0){

                    System.out.println("Informe Nome:");
                    nome = input.nextLine();
                    System.out.println("Informe CPF ou CNPJ");
                    cpfCnpj = input.nextLine();
                    System.out.println("Tipo de Conta PF: 0, PJ: 1");
                    subTipoConta = Integer.parseInt(input.nextLine());

                    String [] dadosCliente = cadastraInfoCliente(nome, cpfCnpj, String.valueOf(subTipoConta));

                    if(subTipoConta == 0){
                        if(abrirConta(dadosCliente, contasPessoaFisica, contPf)) contPf++;
                    } else if (subTipoConta == 1) {
                        if(abrirConta(dadosCliente, contasPessoaJuridicas, contPj)) contPj++;
                    }

                } else if (opcaoMenu == 1) {

                    System.out.println("Informe número da Conta:");
                    numeroConta = Integer.parseInt(input.nextLine());
                    System.out.println(" Informe tipo PF: 0, PJ: 1");
                    tipo = Integer.parseInt(input.nextLine());
                    System.out.println(" Digite o valor de saque:");
                    valor = Double.parseDouble(input.nextLine());

                    conta = (Object[]) tipoConta[tipo];
                    cliente = infoConta(conta[numeroConta]);
                    saca(cliente, valor);

                } else if (opcaoMenu == 2) {

                    System.out.println("Informe número da Conta:");
                    numeroConta = Integer.parseInt(input.nextLine());
                    System.out.println(" Informe tipo PF: 0, PJ: 1");
                    tipo = Integer.parseInt(input.nextLine());
                    System.out.println(" Digite o valor de deposito:");
                    valor = Double.parseDouble(input.nextLine());

                    conta = (Object[]) tipoConta[tipo];
                    depositar(conta[numeroConta], valor);
                } else if (opcaoMenu == 3) {

                    int numeroContaDestinataria;
                    Object [] contaDestinataria;
                    int tipoContaDestino;

                    System.out.println("Informe número da sua Conta:");
                    numeroConta = Integer.parseInt(input.nextLine());
                    System.out.println(" Informe tipo da sua conta PF: 0, PJ: 1");
                    tipo = Integer.parseInt(input.nextLine());
                    System.out.println(" Digite o valor da transferencia:");
                    valor = Double.parseDouble(input.nextLine());

                    System.out.println("Informe o número da Conta Destinatária:");
                    numeroContaDestinataria = Integer.parseInt(input.nextLine());
                    System.out.println(" Informe tipo da conta Destinatária PF: 0, PJ: 1");
                    tipoContaDestino = Integer.parseInt(input.nextLine());

                    conta = (Object[]) tipoConta[tipo];
                    cliente = infoConta(conta[numeroConta]);

                    contaDestinataria = (Object[]) tipoConta[tipoContaDestino];
                    transfere(cliente, valor, contaDestinataria[numeroContaDestinataria]);


                } else if (opcaoMenu == 4) {

                    System.out.println("Informe número da Conta:");
                    numeroConta = Integer.parseInt(input.nextLine());
                    System.out.println(" Informe tipo PF: 0, PJ: 1");
                    tipo = Integer.parseInt(input.nextLine());

                    conta = (Object[]) tipoConta[tipo];
                    cliente = infoConta(conta[numeroConta]);
                    System.out.println(getSaldo(cliente));;
                }
            }
        } while (opcaoMenu != 5);

    }

    public static boolean abrirConta(String[] cliente, Object[] contas, int index) {
        if (contas[index] == null) {
            contas[index] = cliente;
            return true;
        }
        return false;
    }

    private static String[] cadastraInfoCliente(String nome, String cpfOuCnpj, String tipoSubConta) {
        // Estrutura de informações bancária -> nome: 0, cpf ou cnpj: 1, saldo: 2
        return new String[]{nome, cpfOuCnpj, "00", tipoSubConta};

    }

    public static boolean saca(String[] cliente, double valor) {
        // Função saca conta pessoa física

        //String[] cliente = infoConta(conta);
        int tipoConta = Integer.parseInt(String.valueOf(cliente[3].charAt(0)));
        double saldo = getSaldo(cliente);

        if (saldo >= valor) {
            double novoSaldo = novoSaldo(tipoConta, saldo, valor);
            cliente[2] = String.valueOf(novoSaldo);
            return true;
        }
        return false;
    }

    public static void depositar(Object conta, double valor) {
        String[] cliente = infoConta(conta);
        if (valor > 0) {
            cliente[2] = String.valueOf((getSaldo(cliente) + valor));
        }
    }

    public static boolean transfere(String[] cliente, double valor, Object conta) {
        if (getSaldo(cliente) > valor) {
            saca(cliente, valor);
            depositar(conta, valor);
        }
        return true;
    }

    private static double novoSaldo(int tipoConta, double saldo, double valor) {
        double novoSaldo;
        if (tipoConta == 0) {
            novoSaldo = (saldo - valor);  // conta pessoa física
        } else {
            novoSaldo = (saldo - (valor + (valor * 0.05))); //  conta pessoa juridica, taxa de 0.05 sobre o valor de saque
        }
        return novoSaldo;
    }

    public static double getSaldo(String[] cliete) {
        return Double.parseDouble(cliete[2]);
    }

    private static String[] infoConta(Object conta) {
        String[] cliete = (String[]) conta;
        return cliete;
    }

    public static void menu() {
        System.out.println("\t MENU");
        System.out.println(
                "0 - Criar Conta \n" + "1 - sacar\n"
                        + "2 - Depositar\n" + "3 - Transferir \n"
                        + "4 - Consultar Saldo\n" + "5 - Sair"
        );
    }
}



//else if (opcaoMenu == 5) {
//        System.out.println("\tIr para\n");
//        printTipoConta(tipoConta);
//        int op = Integer.parseInt(input.nextLine());
//        if (contas[op] != null) {
//        index = op;
//        } else {
//        System.out.println("\nConta NÃO existe!");
//        }
//        }