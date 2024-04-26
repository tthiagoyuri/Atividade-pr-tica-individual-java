import java.util.Random;

class Cliente extends Thread {
    private static final double SALDO_INICIAL = 1000.00;
    private static final double[] VALORES_COMPRA = {100.00, 200.00};
    private String nome;
    private Conta conta;
    private Loja[] lojas;
    private Random random;

    public Cliente(String nome, Loja[] lojas) {
        this.nome = nome;
        this.conta = new Conta(SALDO_INICIAL);
        this.lojas = lojas;
        this.random = new Random();
    }

    @Override
    public void run() {
     boolean comprou = false;
      while (true) {
     double valorCompra = VALORES_COMPRA[random.nextInt(VALORES_COMPRA.length)]; 
     if (conta.getSaldo() >= valorCompra) {
       comprou = true;
      for (Loja loja : lojas) {
            synchronized (loja.getConta()) {
         if (conta.getSaldo() >= valorCompra) {
          Banco.transferir(conta, loja.getConta(), valorCompra);
                System.out.println(nome + " realizou uma compra de R$" + valorCompra + " na loja " + loja.getNome());
                        }
                    }
                }
            } else {
                break; 
            }
        }
        conta.debitar(conta.getSaldo());
        System.out.println(nome + " encerrou suas compras. Saldo final: R$" + conta.getSaldo());
    }

    public String getNome() {
        return nome;
    }

    public Conta getConta() {
        return conta;
    }
}
