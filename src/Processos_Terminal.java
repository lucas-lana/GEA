package src;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

public class Processos_Terminal {
    private Scanner scanner;
    private List<String> estados = new ArrayList<>();

    public Processos_Terminal(Scanner scanner) {
        this.scanner = scanner;
    }

    public void atraso(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            System.out.println("Erro ao dormir a thread.");
            e.printStackTrace();
        }
    }

    public int num_estados() {
        int num_estados = 0;
        // Loop para garantir que o número de estados seja válido
        while (true) {
            try {
                System.out.print("Para começar, digite o número de estados do autômato: ");
                num_estados = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer após nextInt()
                if (num_estados > 0) {
                    break; // Sai do loop se o número de estados for válido
                } else {
                    System.out.println("Número de estados deve ser maior que zero.");
                    System.out.println("--------------------------------------");
                    atraso(250);
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, digite um número inteiro.");
                scanner.nextLine(); // Limpa a entrada inválida do buffer
                System.out.println("--------------------------------------");
                atraso(250);
            }
        }
        return num_estados;
    }

    public int num_simbolos() {
        int num_simbolos = 0;
        // Loop para garantir que o número de símbolos seja válido
        while (true) {
            try {
                System.out.print("Digite a quantidade de símbolos do alfabeto: ");
                num_simbolos = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer após nextInt()
                if (num_simbolos > 0) {
                    break; // Sai do loop se o número de símbolos for válido
                } else {
                    System.out.println("Número de símbolos deve ser maior que zero.");
                    System.out.println("--------------------------------------");
                    atraso(250);
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, digite um número inteiro.");
                scanner.nextLine(); // Limpa a entrada inválida do buffer
                System.out.println("--------------------------------------");
                atraso(250);
            }
        }
        return num_simbolos;
    }

    public String estado_inicial() {
        String estado_inicial = "";
        // Loop para o estado inicial
        while (true) {
            try {
                System.out.print("Digite o estado inicial do autômato: ");
                estado_inicial = scanner.nextLine();
                if (!estado_inicial.isEmpty() && estados.contains(estado_inicial)) {
                    break; // Sai do loop se o estado inicial for válido
                } else if (!estados.contains(estado_inicial)) {
                    System.out.println("O estado inicial deve estar na lista de estados.");
                    System.out.println("Estados: " + estados);
                    System.out.println("--------------------------------------");
                    atraso(250);
                } else {
                    System.out.println("O estado inicial não pode ser vazio.");
                    System.out.println("--------------------------------------");
                    atraso(250);
                }
            } catch (Exception e) {
                System.out.println("Erro ao inserir o estado inicial.");
                scanner.nextLine(); // Limpa a entrada inválida do buffer
                System.out.println("--------------------------------------");
                atraso(250);
            }
        }
        return estado_inicial;
    }

    public String estado_final() {
        String estado_final = "";
        // Loop para o estado final
        while (true) {
            try {
                System.out.print("Digite o estado final do autômato: ");
                estado_final = scanner.nextLine();
                if (!estado_final.isEmpty() && estados.contains(estado_final)) {
                    break; // Sai do loop se o estado final for válido
                } else {
                    System.out.println("O estado final não pode ser vazio.");
                    System.out.println("--------------------------------------");
                    atraso(250);
                }
            } catch (Exception e) {
                System.out.println("Erro ao inserir o estado final.");
                scanner.nextLine(); // Limpa a entrada inválida do buffer
                System.out.println("--------------------------------------");
                atraso(250);
            }
        }
        return estado_final;
    }

    public void nome_estados(int num_estados) {
        for (int i = 1; i <= num_estados; i++) {
            String estado = "";
            // Loop para o nome do estado
            while (true) {
                try {
                    System.out.print("Digite o nome do estado " + i + ": ");
                    estado = scanner.nextLine();
                    if (!estado.isEmpty()) {
                        estados.add(estado);
                        break; // Sai do loop se o estado for válido
                    } else {
                        System.out.println("O nome do estado não pode ser vazio.");
                        System.out.println("--------------------------------------");
                        atraso(250);
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao inserir o nome do estado.");
                    scanner.nextLine(); // Limpa a entrada inválida do buffer
                    System.out.println("--------------------------------------");
                    atraso(250);
                }
            }
        }
    }       

    public Map<String, List<String>> transicoes(int num_estados, List<String> alfabeto) {
        Map<String, List<String>> transicoes = new HashMap<>();
         // Processa os estados e transições com tratamento de erros

        nome_estados(num_estados);
        
        for (int i = 0; i < num_estados; i++) {
            int num_transicoes = 0;

            transicoes.put(estados.get(i), new ArrayList<>());

            // Loop para o número de transições
            while (true) {
                try {
                    System.out.print("Quantas transições o estado " + estados.get(i) + " possui: ");
                    num_transicoes = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer após nextInt()
                    if (num_transicoes >= 0 && num_transicoes == alfabeto.size()) {
                        break; // Sai do loop se o número de transições for válido
                    } else if (num_transicoes != alfabeto.size()) {
                        System.out.println("O número de transições deve ser igual ao tamanho do alfabeto.");
                        System.out.println("--------------------------------------");
                        atraso(250);
                    } else {
                        System.out.println("O número de transições não pode ser negativo ou zero.");
                        System.out.println("--------------------------------------");
                        atraso(250);
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida! Por favor, digite um número inteiro.");
                    scanner.nextLine(); // Limpa a entrada inválida do buffer
                    System.out.println("--------------------------------------");
                    atraso(250);
                }
            }

            // Loop para as transições
            for (int j = 0; j < num_transicoes; j++) {
                String simbolo = "";
                String destino = "";

                // Loop para o símbolo da transição
                while (true) {
                    try {
                        System.out.print("Digite o símbolo da transição " + (j + 1) + ": ");
                        simbolo = scanner.nextLine();
                        if (!simbolo.isEmpty() && alfabeto.contains(simbolo)) {
                            break; // Sai do loop se o símbolo for válido
                        } else if (!alfabeto.contains(simbolo)) {
                            System.out.println("O símbolo da transição deve estar no alfabeto.");
                            System.out.println("Alfabeto: " + alfabeto);
                            System.out.println("--------------------------------------");
                            atraso(250);
                        } else {
                            System.out.println("O símbolo da transição não pode ser vazio.");
                            System.out.println("--------------------------------------");
                            atraso(250);
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao inserir o símbolo da transição.");
                        scanner.nextLine(); // Limpa a entrada inválida do buffer
                        System.out.println("--------------------------------------");
                        atraso(250);
                    }
                }

                // Loop para o estado de destino da transição
                while (true) {
                    try {
                        System.out.print("Digite o estado de destino da transição " + (j + 1) + ": ");
                        destino = scanner.nextLine();
                        if (!destino.isEmpty() && estados.contains(destino)) {
                            break; // Sai do loop se o destino for válido
                        } else if (!estados.contains(destino)) {
                            System.out.println("O estado de destino deve estar na lista de estados.");
                            System.out.println("Estados: " + estados);
                            System.out.println("--------------------------------------");
                            atraso(250);
                        } else {
                            System.out.println("O estado de destino não pode ser vazio.");
                            System.out.println("--------------------------------------");
                            atraso(250);
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao inserir o estado de destino.");
                        scanner.nextLine(); // Limpa a entrada inválida do buffer
                        System.out.println("--------------------------------------");
                        atraso(250);
                    }
                }

                transicoes.get(estados.get(i)).add(simbolo + " -> " + destino);
            }
        }
        return transicoes;
    }

    public void print_all(int quantidade_estados, List<String> alfabeto, String estado_inicial, String estado_final, Map<String,List<String>> transicoes) {
        System.out.println("------ Informações do autômato ------");
        System.out.println("Quantidade de estados: " + quantidade_estados);
        System.out.println("Alfabeto: " + alfabeto);
        System.out.println("Estado inicial: " + estado_inicial);
        System.out.println("Estado final: " + estado_final);
        for (Map.Entry<String, List<String>> entry : transicoes.entrySet()) {
            System.out.println("Transições do estado " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("--------------------------------------");
        atraso(1000);
    }

    public List<String> get_estados() {
        return this.estados;
    }
}