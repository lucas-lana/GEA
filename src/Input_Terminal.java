package src;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Input_Terminal {
    private Scanner scanner;
    public List<String> alfabeto = new ArrayList<>();
    public List<String> estados = new ArrayList<>();
    public boolean determinístico;

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

    public void get_alfabeto(){
        this.alfabeto = get_lista("símbolos do alfabeto");
        if (this.alfabeto.contains("")) {
            this.alfabeto.remove(alfabeto.indexOf(""));
        }

    }

    public void get_estados() {
        List<String> aux = new ArrayList<>();
        aux = get_lista("nomes dos estados");
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

    public List<String> get_lista(String tipoLista){
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
        
        return tratamento_lista(string_bruta);
    }

    public Map<String, List<String>> get_transicoes() {
        Map<String, List<String>> transicoes = new HashMap<>();
        
        String[] lista_estado_erro = {"ERRO", "NÃO", "NULL", "N", " ","NÃO TEM", "ERROR",
        "NÃO TEM ESTADO", "NÃO TEM ESTADO DE DESTINO, ESTADO DE ERRO","NAO",
        "NAO TEM","NAO TEM ESTADO","NAO TEM ESTADO DE DESTINO, ESTADO DE ERRO",
        "NÃO TÊM ESTADO DE DESTINO","NÃO TÊM ESTADO DE DESTINO, ESTADO DE ERRO",
        "NÃO TÊM, NÃO TÊM ESTADO DE DESTINO"};
        
        System.out.println("--------------------------------------");
        System.out.println("Instruções para as transições:\nCaso o estado de destino de uma transição não exista,");
        System.out.println("pode ser digitado 'null', 'não', 'n', ' ', 'não tem', 'error', 'erro', 'não tem estado''.");
        System.out.println("Dessa forma, o programa irá criar um estado de erro.");

        for (int i = 0; i < this.estados.size(); i++) {

            transicoes.put(this.estados.get(i), new ArrayList<>());

            // Loop para as transições
            for (int j = 0; j < alfabeto.size(); j++) {

                if (this.estados.get(i).equals("ERRO")) {
                    transicoes.get(this.estados.get(i)).add(alfabeto.get(j) + " -> ERRO");
                }

                else{ 
                    
                    System.out.println("--------------------------------------");
                    System.out.println("Estado atual: " + this.estados.get(i));
                           
                    String destino = "";
                    // Loop para o estado
                    while (true) {
                        try {
                            System.out.print("Símbolo: " + alfabeto.get(j) + "\nEstado de Destino: ");
                            destino = scanner.nextLine().toUpperCase();
                            
                            if (!destino.isEmpty() && this.estados.contains(destino)) {
                                break; // Sai do loop se o destino for válido
                            } else if (List.of(lista_estado_erro).contains(destino) || destino.isEmpty()) {
                                destino = "ERRO";
                                if (!this.estados.contains(destino)) {
                                    this.estados.add("ERRO");
                                }
                                break;
                            } else {
                                System.out.println("Entrada inválida.");
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
        }
        return transicoes;
    }

    public List<String> get_estados_inicias() {
        List<String> estados_iniciais = new ArrayList<>();
        // Loop para o estado inicial
        while (true) {
            try {
                System.out.print("Digite o(s) estado(s) inicial(ais) do autômato, separados por vírgula ou espaço: ");
                estados_iniciais = tratamento_lista(scanner.nextLine());
                if (!estados_iniciais.isEmpty() && this.estados.containsAll(estados_iniciais)) {
                    break; // Sai do loop se o estado inicial for válido
                } else if (!this.estados.containsAll(estados_iniciais)) {
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
        return estados_iniciais;
    }

    public List<String> get_estados_finais() {
        List<String> estados_finais = new ArrayList<>();
        // Loop para o estado final
        while (true) {
            try {
                System.out.print("Digite o(s) estado(s) final(ais) do autômato: ");
                estados_finais = tratamento_lista(scanner.nextLine());
                if (!estados_finais.isEmpty() && this.estados.containsAll(estados_finais)) {
                    break; // Sai do loop se o estado final for válido
                } else if (!this.estados.containsAll(estados_finais)) {
                    System.out.println("O estado final deve estar na lista de estados.");
                    System.out.println("Estados: " + this.estados);
                    System.out.println("--------------------------------------");
                    atraso(250);
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
        return estados_finais;
    }

    public void print_all(List<String> alfabeto, List<String> estado_inicial, List<String> estado_final, Map<String,List<String>> transicoes) {
        System.out.println("------ Informações do autômato ------");
        System.out.println("Quantidade de estados: " + this.estados.size());
        System.out.println("Estado(s): " + this.estados);
        System.out.println("Alfabeto: " + alfabeto);
        System.out.println("Estado inicial: " + estado_inicial);
        System.out.println("Estado final: " + estado_final);
        for (Map.Entry<String, List<String>> entry : transicoes.entrySet()) {
            System.out.println("Transições do estado " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("--------------------------------------\n");
        atraso(1000);
    }

    private List<String> tratamento_lista(String lista_bruta) {
        List <String> lista_tratada = new ArrayList<>();

        lista_bruta = lista_bruta.replace(" ", ",");
        lista_bruta = lista_bruta.replace(",,", ",");
        lista_tratada = List.of(lista_bruta.split(","));

        if (lista_tratada.contains("")) {
            lista_tratada.remove(lista_tratada.indexOf(""));
        }

        Set <String> lista_final = new HashSet<>(lista_tratada);
        return new ArrayList<>(lista_final);
    }

    public void get_determinismo(List<String> Incias, List<String> Finais) {
        //Criar mais formas para determinar se o automato é deterministico
        
        if ((Incias.size() > 1) || (Finais.size() > 1)) { // O autômato é não determinístico
            this.determinístico = false;
        }
        else { // O autômato é determinístico
            this.determinístico = true;
        }
        
    }
}
