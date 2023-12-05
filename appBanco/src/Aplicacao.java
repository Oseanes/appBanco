import java.util.Scanner;

public class Aplicacao {
    public static void main(String[] args) {

        Object[] contas = new Object[1];   // salvar conta

        int contaInvestimento = 1;
        String nome;
        String cpfCnpj;
        int subTipoConta;
        int tipoConta = 0;
        double valor;
        int opcaoMenu = 0;
        String[] cliente;
        int index = 0;

        Scanner input = new Scanner(System.in);

        do {
            try {
                if (contas[0] == null) {
                    System.out.println("Qual o tipo de Conta  deseja abrir? PF: 0 ou PJ: 1");
                    tipoConta = Integer.parseInt(input.nextLine());
                    System.out.println("Informe Nome:");
                    nome = input.nextLine();
                    System.out.println("Informe CPF ou CNPJ");
                    cpfCnpj = input.nextLine();
                    System.out.println("Qual conta deseja Abrir?");
                    printTipoConta(tipoConta);
                    subTipoConta = Integer.parseInt(input.nextLine());

                    String[] dadosCliente;
                    if (subTipoConta == contaInvestimento) {

                        dadosCliente = cadastraInfoCliente(
                                nome, cpfCnpj, String.valueOf(tipoConta),
                                String.valueOf(subTipoConta), "00"
                        );
                    } else {

                        dadosCliente = cadastraInfoCliente(
                                nome, cpfCnpj, String.valueOf(tipoConta),
                                String.valueOf(subTipoConta)
                        );
                    }

                    if (abrirConta(dadosCliente, contas)) {
                        System.out.println("Conta Criada com sucesso!");
                    } else {
                        System.out.println(" Você já possui uma conta!");
                    }
                }
                menu();
                opcaoMenu = Integer.parseInt(input.nextLine());

                if (opcaoMenu != 8) {
                    if (opcaoMenu == 1) {
                        System.out.println(" Digite o valor de saque:");
                        valor = Double.parseDouble(input.nextLine());

                        cliente = infoConta(contas[index]);
                        saca(cliente, valor);

                    } else if (opcaoMenu == 2) {
                        System.out.println(" Digite o valor de deposito:");
                        valor = Double.parseDouble(input.nextLine());

                        depositar(contas[index], valor);
                    } else if (opcaoMenu == 3) {
                        int contaDestino;

                        System.out.println(" Digite o valor da transferencia:");
                        valor = Double.parseDouble(input.nextLine());

                        System.out.println("Informe o número da Conta Destinatária:");
                        contaDestino = Integer.parseInt(input.nextLine());

                        cliente = infoConta(contas[index]);

                        if (transfere(cliente, valor, contaDestino)) {
                            System.out.println("Transferência concluída ;)");
                        } else {
                            System.out.println("Saldo Insuficiente!!");
                        }

                    } else if (opcaoMenu == 4) {
                        cliente = infoConta(contas[index]);
                        System.out.println(" >> SALDO: " + getSaldo(cliente));

                    } else if (opcaoMenu == 5) {
                        double montante;
                        cliente = infoConta(contas[index]);

                        if (Integer.parseInt(cliente[3]) == contaInvestimento) {

                            System.out.println(" Quanto deseja invertir?");
                            montante = Double.parseDouble(input.nextLine());

                            if (invertir(contas[index], montante)) {
                                System.out.println(" Invertimento realizado com sucesso!");
                            } else {
                                System.out.println(" Algo deu errado!");
                            }

                        } else {
                            System.out.println("Sua Conta não é Conta-Investimento");
                        }
                    } else if (opcaoMenu == 6) {
                        System.out.println(
                                "Valor Investido: " + getValorInvestido(contas[index]) +
                                        " Rendimento: " + rendimento(contas[index])
                        );

                    } else if (opcaoMenu == 7) {
                        cliente = infoConta(contas[index]);

                        System.out.println("Nome: " + cliente[0] + "\nCPF/CNPJ: " + cliente[1]
                                + "\nTipo de Conta: " + cliente[3] + "\nSaldo: " + cliente[4]);

                    }
                    contaAcessada(getSubConta(contas[index]));
                }
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            }

        } while (opcaoMenu != 8);

    }

    public static boolean abrirConta(String[] cliente, Object[] contas) {
        if (contas[0] == null) {
            contas[0] = cliente;
            return true;
        }
        return false;
    }

    private static String[] cadastraInfoCliente(String nome, String cpfCnpj, String tipoConta, String subTipoConta) {
        return new String[]{nome, cpfCnpj, tipoConta, subTipoConta, "00"};
    }

    private static String[] cadastraInfoCliente(String nome, String cpfCnpj, String tipoConta, String subTipoConta, String valorInvestido) {
        return new String[]{nome, cpfCnpj, tipoConta, subTipoConta, "00", valorInvestido};
    }

    public static boolean saca(Object conta, double valor) {

        String[] cliente = infoConta(conta);
        int tipoConta = Integer.parseInt(cliente[2]);
        double saldo = getSaldo(cliente);

        if (saldo >= valor) {
            double novoSaldo = novoSaldo(tipoConta, saldo, valor);
            cliente[4] = String.valueOf(novoSaldo);
            return true;
        }
        return false;
    }

    public static void depositar(Object conta, double valor) {
        String[] cliente = infoConta(conta);
        if (valor > 0) {
            cliente[4] = String.valueOf((getSaldo(cliente) + valor));
        }
    }

    public static boolean transfere(Object[] conta, double valor, int contaDestino) {
        String[] cliente = infoConta(conta);
        int tipoConta = Integer.parseInt(cliente[2]);
        if (getSaldo(cliente) > valor) {
            saca(cliente, valor);
            //depositar(contaDestino, valor);
            return true;
        }
        return false;
    }

    private static double novoSaldo(int tipoConta, double saldo, double valor) {
        double novoSaldo;
        if (tipoConta == 0) {
            novoSaldo = (saldo - valor);  // conta pessoa física
        } else {
            novoSaldo = (saldo - (valor + (valor * 0.05))); //  conta pessoa juridica, taxa de 0.05 sobre o valor de saque
        }
        return Math.round(novoSaldo * 100.00)/100.00;
    }

    public static double getSaldo(Object[] conta) {
        String[] cliete = infoConta(conta);
        return Double.parseDouble(cliete[4]);
    }

    private static String[] infoConta(Object conta) {
        String[] cliete = (String[]) conta;
        return cliete;
    }

    public static boolean invertir(Object conta, double valor) {
        String[] cliente = infoConta(conta);
        double valorInvestido;
        valorInvestido = Double.parseDouble(cliente[5]);

        if (saca(cliente, valor)) {
            valorInvestido += valor;
            cliente[5] = String.valueOf(valorInvestido);
            return true;
        }
        return false;
    }

    public static double rendimento(Object conta) {
        String[] cliente = infoConta(conta);
        double valorInvestido = Double.parseDouble(cliente[5]);
        double redimento = 0.08;

        if (Integer.parseInt(cliente[2]) == 1) {
            redimento += 0.02;
        }
        return Math.round(valorInvestido * redimento * 100.00)/100.00;
    }

    public static double getValorInvestido(Object conta) {
        String[] cliente = infoConta(conta);
        return Double.parseDouble(cliente[5]);
    }

    private static int getSubConta(Object conta) {
        String[] cliente = infoConta(conta);
        return Integer.parseInt(cliente[3]);
    }

    public static void menu() {
        System.out.println();
        System.out.println("\t MENU");
        System.out.println(
                "1 - sacar\n"
                        + "2 - Depositar\n" + "3 - Transferir \n"
                        + "4 - Consultar Saldo\n" + "5 - Investir\n"
                        + "6 - Consultar Investimento\n" + "7 - Informação Conta\n" + "8 - Sair\n"
        );
    }

    private static void printTipoConta(int tipoConta) {
        System.out.println();
        if (tipoConta == 0) {
            System.out.println(
                    "0 - conta-corrente\n" +
                            "1 - conta-investimento\n" +
                            "2 - conta-poupança");
        } else {
            System.out.println(
                    "0 - conta-corrente\n"
                            + "1 - conta-investimento\n");
        }
    }

    private static void contaAcessada(int tipoConta) {
        System.out.println();
        if (tipoConta == 0) {
            System.out.println("CONTA-CORRENTE");
        } else if (tipoConta == 1) {
            System.out.println("CONTA-INVESTIMENTO");
        } else {
            System.out.println("CONTA-POUPANÇA");
        }
    }
}
