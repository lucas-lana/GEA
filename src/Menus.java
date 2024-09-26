package src;

import java.io.IOException;
import java.util.Scanner;

public class Menus {
    public Scanner scanner;

    public Menus(Scanner scanner) {
        this.scanner = scanner;
    }

    public void atraso(int tempo) {
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
        System.out.println("--------------------------------------\n");
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
                    atraso(250);
                }
            } catch (Exception e) {
                System.out.println("Erro ao inserir a opção.");
                this.scanner.nextLine(); // Limpa a entrada inválida do buffer
                System.out.println("--------------------------------------");
            }
        }
        Main.main("-r, -i".split(", "));
    }

    public void Menu_Instruções() {
        while (true) {
            try {
                System.out.print("Deseja ver as instruções? (S/N): ");
                String instrucoes = this.scanner.nextLine();
                instrucoes = instrucoes.toUpperCase();
                if (instrucoes.equals("S") || instrucoes.equals("N") && !instrucoes.isEmpty()) {
                    if (instrucoes.equals("S")) {
                        System.out.println("Instruções:");
                        System.out.println("--------------------------------------");
                        System.out.println("1. Sempre que precisar passar uma lista de elementos, separe-os por vírgula ou espaço.");
                        System.out.println("2. O programa desconsidera estados e simbolos do alfabeto duplicados.");
                        System.out.println("3. Para transições que não existem no autômato, digite no campo de estado de destino 'null', 'não' e 'n'");
                        System.out.println("   Dessa forma, se a máquina de estados for determinística, o programa irá criar um estado de erro.");
                        System.out.println("   Se a máquina de estados for não determinística, o programa irá ignorar a transição.");
                        System.out.println("4. Para transições que vão para mais de um estado, separe os estados por vírgula ou espaço.");
                        System.out.println("5. Transições vazias, transições Épsilon, serão contempladas se digitar '@' ou 'λ' ao infomar os símbolos do alfabeto da máquina.");
                        //POSSÍVEL IMPLEMENTAÇÃO FUTURA
                        //System.out.println("N. Para transições que vão para todos os estados, digite 'all' no campo de estado de destino.");
                        System.out.println("--------------------------------------");
                        while (true) {
                            try{
                                System.out.print("Deseja continuar? (S/N): ");
                                String continuar = this.scanner.nextLine();
                                continuar = continuar.toUpperCase();
                                if (continuar.equals("S") || continuar.equals("N") && !continuar.isEmpty()) {
                                    if (continuar.equals("S")) {
                                        break;
                                    } else {
                                        System.out.println("Encerrando o programa...");
                                        System.out.println("--------------------------------------");
                                        System.exit(0);
                                    }
                                } else {
                                    System.out.println("Opção inválida.");
                                    System.out.println("--------------------------------------");
                                    atraso(250);
                                }
                            } catch (Exception e) {
                                System.out.println("Erro ao inserir a opção.");
                                this.scanner.nextLine(); // Limpa a entrada inválida do buffer
                                System.out.println("--------------------------------------");
                            }
                        }
                        break;
                    } else {
                        System.out.println("--------------------------------------");
                        break;
                    }
                } else {
                    System.out.println("Opção inválida.");
                    System.out.println("--------------------------------------");
                    atraso(250);
                }
            } catch (Exception e) {
                System.out.println("Erro ao inserir a opção.");
                this.scanner.nextLine(); // Limpa a entrada inválida do buffer
                System.out.println("--------------------------------------");
            }
            
        }
    }
}
