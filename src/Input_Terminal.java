package src;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

public class Input_Terminal {
    private Scanner scanner;
    public List<String> alfabeto = new ArrayList<>();
    public List<String> estados = new ArrayList<>();

    public Input_Terminal(Scanner scanner) {
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

    public void alfabeto(){
        this.alfabeto = receber_lista("símbolos do alfabeto");
        if (this.alfabeto.contains("")) {
            this.alfabeto.remove(alfabeto.indexOf(""));
        }

    }

    public void estados() {
        List<String> aux = new ArrayList<>();
        aux = receber_lista("nomes dos estados");
        for (String estado : aux) {
            if (!this.estados.contains(estado))
                this.estados.add(estado);
            else {
                System.out.println("O estado " + estado + " foi digitado mais de uma vez.");
                System.out.println("Não é permitido estados duplicados. Deletando a duplicata.");
                System.out.println("--------------------------------------");
                atraso(250);
            }
        }
        if (this.estados.contains("")) {
            this.estados.remove(this.estados.indexOf(""));
        }
    }       

    public List<String> receber_lista(String tipoLista){
        String string_bruta;
        while (true) {
            try {
                System.out.print("Digite os(as) "+ tipoLista +", separados(das) por vírgula ou espaço: ");
                string_bruta = scanner.nextLine();
                if (!string_bruta.isEmpty()) {
                    break; // Sai do loop se os estados forem válidos
                } else {
                    System.out.println("Os(As) " + tipoLista + "não podem ser vazios(as).");
                    System.out.println("--------------------------------------");
                    atraso(250);
                }
            } catch (Exception e) {
                System.out.println("Erro ao inserir os(as) "+ tipoLista +".");
                scanner.nextLine(); // Limpa a entrada inválida do buffer
                System.out.println("--------------------------------------");
                atraso(250);
            }
        }
        string_bruta = string_bruta.replace(" ", ",");
        return List.of(string_bruta.split(","));
    }

    public Map<String, List<String>> transicoes() {
        Map<String, List<String>> transicoes = new HashMap<>();
        
        for (int i = 0; i < this.estados.size(); i++) {
            int num_transicoes = 0;

            transicoes.put(this.estados.get(i), new ArrayList<>());

            // Loop para o número de transições
            while (true) {
                try {
                    System.out.print("Quantas transições o estado " + this.estados.get(i) + " possui: ");
                    num_transicoes = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer após nextInt()
                    if (num_transicoes >= 0 && num_transicoes == this.alfabeto.size()) {
                        break; // Sai do loop se o número de transições for válido
                    } else if (num_transicoes != this.alfabeto.size()) {
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
                String destino = "";
                System.out.println("--------------------------------------");
                System.out.println("Estado atual: " + this.estados.get(i));

                // Loop para o estado de destino da transição
                while (true) {
                    try {
                        System.out.print("Símbolo: " + alfabeto.get(j) + "\nEstado de Destino: ");
                        destino = scanner.nextLine();
                        if (!destino.isEmpty() && this.estados.contains(destino)) {
                            break; // Sai do loop se o destino for válido
                        } else if (!this.estados.contains(destino)) {
                            System.out.println("O estado de destino deve estar na lista de estados.");
                            System.out.println("Estados: " + this.estados);
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

                transicoes.get(this.estados.get(i)).add(alfabeto.get(j) + " -> " + destino);
            }
            System.out.println("--------------------------------------");
        }
        return transicoes;
    }

    public String estado_inicial() {
        String estado_inicial = "";
        // Loop para o estado inicial
        while (true) {
            try {
                System.out.print("Digite o estado inicial do autômato: ");
                estado_inicial = scanner.nextLine();
                if (!estado_inicial.isEmpty() && this.estados.contains(estado_inicial)) {
                    break; // Sai do loop se o estado inicial for válido
                } else if (!this.estados.contains(estado_inicial)) {
                    System.out.println("O estado inicial deve estar na lista de estados.");
                    System.out.println("Estados: " + this.estados);
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
                if (!estado_final.isEmpty() && this.estados.contains(estado_final)) {
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

    public void print_all(List<String> alfabeto, String estado_inicial, String estado_final, Map<String,List<String>> transicoes) {
        System.out.println("------ Informações do autômato ------");
        System.out.println("Quantidade de estados: " + this.estados.size());
        System.out.println("Alfabeto: " + alfabeto);
        System.out.println("Estado inicial: " + estado_inicial);
        System.out.println("Estado final: " + estado_final);
        for (Map.Entry<String, List<String>> entry : transicoes.entrySet()) {
            System.out.println("Transições do estado " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("--------------------------------------\n");
        atraso(1000);
    }

    public List<String> get_estados() {
        return this.estados;
    }
}