package src;

import java.util.Scanner;

public class Menus {
    public Scanner scanner;

    public Menus(Scanner scanner) {
        this.scanner = scanner;
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
}
