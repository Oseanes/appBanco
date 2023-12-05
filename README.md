# appBanco
Uma pequena aplicação que simule uma app bancária. Obs:. Esse projeto aplica uma lógica estruturada.

## Exercício propósto:
Crie uma aplicação que simule uma app bancária. Os clientes podem ser pessoa física ou jurídica,
sendo que para PJ existe a cobrança de uma taxa de 0.5% para cada saque ou transferência. 
Além do produto conta-corrente, os clientes PF podem abrir uma conta-poupança e conta-investimento. 
Clientes PJ não abrem poupança, mas seus rendimentos em conta-investimento rendem 2% a mais.

* Crie as funcionalidades: abrir conta, sacar, depositar, transferência, investir, consultar saldo.

* Use a classe "Aplicacao" para criar seu método "main" e demonstrar o funcionamento do seu código.

* O projeto pode ser entregue via arquivo zip diretamente na plataforma ou via link de repositório Github.


# Informação para uso do AppBanco
Só é possivel abrir um tipo de conta!
### Tipo de Contas
0 - Conta-corrente
1 - Conta-investimento
2 - Conta-poupança

### Taxa e Rendimento 
* Conta **Pessoa jurídico** pagam uma taxa de 5% a cada transação exeto deposito;
* Conta **Pessoa jurídica Investimento** rende **2%** a mais do que **Conta Pessoa física Investimento**, a taxa de 
rendimento foi fixada em **8%**.

