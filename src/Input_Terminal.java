package src;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Input_Terminal {
    private Scanner scanner;
    public List<String> alfabeto = new ArrayList<>();
    public List<String> estados = new ArrayList<>();
    public boolean determinístico = true;
    private String[] lista_estado_erro = {"ERRO", "NÃO", "NULL", "N","NAO", "ERROR"};    

    public Input_Terminal(Scanner scanner) {
        this.scanner = scanner;
    }

    private void atraso(int milisegundos) {
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

    private List<String> get_lista(String tipoLista){
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
        
        return tratamento_lista(string_bruta,"N");
    }

    private List<String> remove_estados_lista(String entrada, List<String> lista) {
        lista = tratamento_lista(entrada,"S");
        lista.removeAll(this.estados);
        lista.replaceAll(String::toUpperCase);
        return lista;
    }

    public Map<String, List<String>> get_transicoes() {
        Map<String, List<String>> transicoes = new HashMap<>();
        System.out.println("--------------------------------------");
        System.out.println("Instruções para as transições:\nCaso o estado de destino de uma transição não exista,");
        System.out.println("pode ser digitado" + Arrays.toString(this.lista_estado_erro) + "\n.Dessa forma, o programa irá criar um estado de erro.");
        //atraso(500);

        for (int i = 0; i < this.estados.size(); i++) {

            transicoes.put(this.estados.get(i), new ArrayList<>());

            // Loop para as transições
            for (int j = 0; j < alfabeto.size(); j++) {

                if (this.estados.get(i).equals("ERRO")) {
                    transicoes.get(this.estados.get(i)).add(alfabeto.get(j) + " -> ERRO");
                }

                else {        
                    String entrada = "";
                    List <String> mul_destino = new ArrayList<>();
                    List <String> match_elemento = new ArrayList<>();
                    List <String> invalido_elemento = new ArrayList<>();
                    // Loop para o estado
                    while (true) {
                        try {
                            System.out.println("--------------------------------------");
                            System.out.println("Estado atual: " + this.estados.get(i));
                            System.out.print("Símbolo: " + alfabeto.get(j) + "\nEstado(s) de Destino: ");
                            entrada = scanner.nextLine();

                            //Tratamento correto da entrada
                            mul_destino = tratamento_lista(entrada,"S"); //Tratamento da lista de estados de destino para evitar duplicatas e erros de digitação
                            
                            match_elemento = remove_estados_lista(entrada, match_elemento);
                            match_elemento.retainAll(List.of(this.lista_estado_erro)); // Comparar com a lista de estados de erro
                            
                            invalido_elemento = remove_estados_lista(entrada, invalido_elemento);
                            invalido_elemento.removeAll(match_elemento); // Verificar se há elementos inválidos
                        
                            
                            if ((!mul_destino.isEmpty()) && this.estados.containsAll(mul_destino)) { //Verifica se há elementos e se os elementos estão na lista de estados
                                break; // Sai do loop se o destino for válido
                            } 
                            
                            else if (((!match_elemento.isEmpty()) || entrada.isEmpty() || mul_destino.contains("") || contemPeloMenosUmElemento(mul_destino, this.estados)) && invalido_elemento.isEmpty()) {
                                if (!this.estados.contains("ERRO")) {
                                    this.estados.add("ERRO");
                                    mul_destino.add("ERRO");
                                } else {
                                    mul_destino.add("ERRO");
                                }
                                mul_destino.retainAll(this.estados);
                                Set <String> destino_final = new HashSet<>(mul_destino);
                                mul_destino = new ArrayList<>(destino_final);
                                break;
                            } 
                            
                            else {
                                System.out.println("--------------------------------------");
                                System.out.println("Entrada inválida.");
                                System.out.println("Estados: " + this.estados);
                                System.out.println("Estados de erro: " + Arrays.stream(this.lista_estado_erro).map(String::toLowerCase).toList());
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
                    if (mul_destino.size() == 1) {
                        transicoes.get(this.estados.get(i)).add(alfabeto.get(j) + " -> " + mul_destino.get(0));
                    } else {
                        this.determinístico = false;
                        transicoes.get(this.estados.get(i)).add(alfabeto.get(j) + " -> " + mul_destino);
                    }
                }
            }
            System.out.println("--------------------------------------");
        }
        return transicoes;
    }

    public List<String> get_estados_inicias() {
        List<String> estados_iniciais = new ArrayList<>();
        // Loop para o estado inicial
        while (true) {
            try {
                System.out.print("Digite o(s) estado(s) inicial(ais) do autômato, separados por vírgula ou espaço: ");
                estados_iniciais = tratamento_lista(scanner.nextLine(),"N");
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
                estados_finais = tratamento_lista(scanner.nextLine(),"N");
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

    private List<String> tratamento_lista(String lista_bruta,  String lista_estado_erro) {
        List <String> lista_tratada = new ArrayList<>();

        if (lista_estado_erro.equals("S")){
            
            lista_bruta = lista_bruta.replace(",,", ",");
            lista_tratada = List.of(lista_bruta.split("[, ]+"));

        } else {
            lista_bruta = lista_bruta.replace(" ", ",");
            lista_bruta = lista_bruta.replace(",,", ",");
            lista_tratada = List.of(lista_bruta.split(","));
            
            if (lista_tratada.contains("")) {
                lista_tratada.remove(lista_tratada.indexOf(""));
            }
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

    public boolean contemPeloMenosUmElemento(List<String> lista1, List<String> lista2) {
        for (String item : lista1) {
            if (lista2.contains(item)) {
                return true; // Se ao menos um item de lista1 está em lista2, retorna true
            }
        }
        return false; // Se nenhum item de lista1 está em lista2, retorna false
    }  
}