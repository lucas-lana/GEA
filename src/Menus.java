package src;

import java.io.IOException;
import java.util.Scanner;

public class Menus {
    public Scanner scanner;

    public Menus(Scanner scanner) {
        this.scanner = scanner;
    }

    private void atraso(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String Menu_Agrupa() {
        String agrupaString = "";
        while (true) {
            try {
                System.out.println("Desja agrupar as transições por estado de origem (S/N):");
                agrupaString = this.scanner.nextLine();
                agrupaString = agrupaString.toUpperCase();
                if (!agrupaString.isEmpty()) {
                    break;
                } else {
                    System.out.println("Opção inválida.");
                    System.out.println("--------------------------------------");
                }
            } catch (Exception e) {
                System.out.println("Erro ao inserir a opção.");
                this.scanner.nextLine(); // Limpa a entrada inválida do buffer
                System.out.println("--------------------------------------");
            }
        }
        System.out.println("--------------------------------------");
        return agrupaString;
    }   

    public void Menu_Reinicia() throws IOException {
        while (true) {
            try {
                System.out.println("Deseja reiniciar o programa? (S/N): ");
                String reiniciar = this.scanner.nextLine();
                reiniciar = reiniciar.toUpperCase();
                if (reiniciar.equals("S") || reiniciar.equals("N") && !reiniciar.isEmpty()) {
                    if (reiniciar.equals("S")) {
                        System.out.println("Reiniciando o programa...");
                        System.out.println("--------------------------------------\n\n\n\n\n");
                        atraso(1000);
                        break;
                    } else {
                        System.out.println("Encerrando o programa...");
                        System.out.println("--------------------------------------");
                        System.exit(0);
                    }
                } else {
                    System.out.println("Opção inválida.");
                    System.out.println("--------------------------------------");
                    atraso(500);
                }
            } catch (Exception e) {
                System.out.println("Erro ao inserir a opção.");
                this.scanner.nextLine(); // Limpa a entrada inválida do buffer
                System.out.println("--------------------------------------");
            }
        }
        Main.main("-r, -i".split(", "));
    }
}
