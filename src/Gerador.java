package src;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Gerador {
    public Scanner scanner;
    private boolean determinístico;
    private List<String> Estados_iniciais;
    private List<String> Estados_finais;
    private List<String> estados;
    private List<String> alfabeto;
    private Map<String,List<String>> transicoes;

    public Gerador(List<String> Estado_inicial, List<String> Estado_final,List<String> estados, List<String> alfabeto, Map<String,List<String>> transicoes, Scanner scanner, boolean determinístico) {
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.Estados_iniciais = Estado_inicial;
        this.Estados_finais = Estado_final;
        this.transicoes = transicoes;
        this.scanner = scanner;
        this.determinístico = determinístico;
    }

    public String Agrupar(List<String> arquivoStringAuxList) {
        
        Map<String, Map<String, List<String>>> agrupamento = new LinkedHashMap<>();
        StringBuilder resultado = new StringBuilder(); // StringBuilder para armazenar o resultado

        // Agrupar as transições por estado de origem e destino
        for (String transicao : arquivoStringAuxList) {
            // Dividindo a string na parte "estadoOrigem -> estadoDestino" e "símbolo"
            transicao = transicao.trim();
            String[] partes = transicao.replace("[","").replace("]","").replace(",","").split(" ");
            String estadoOrigem = partes[0];
            List<String> estadosDestino = new ArrayList<>();

            int i = 2;

            while (!partes[i].equals("|")) {
                estadosDestino.add(partes[i]);
                i++;
            }
            String simbolo = partes[i+1];

            // Agrupar os símbolos pelo estado de origem e destino
            String estadoDestino = String.join(" ", estadosDestino);
            agrupamento
                    .computeIfAbsent(estadoOrigem, k -> new LinkedHashMap<>())
                    .computeIfAbsent(estadoDestino, k -> new ArrayList<>())
                    .add(simbolo);
        }

        // Construir a string com as transições agrupadas
        for (Map.Entry<String, Map<String, List<String>>> entry : agrupamento.entrySet()) {
            String estadoOrigem = entry.getKey();
            for (Map.Entry<String, List<String>> destinos : entry.getValue().entrySet()) {
                String estadoDestino = destinos.getKey();
                List<String> simbolos = destinos.getValue();
                resultado.append(estadoOrigem)
                         .append(" -> ")
                         .append(estadoDestino)
                         .append(" | ")
                         .append(String.join(" ", simbolos))
                         .append("\n"); // Adiciona uma nova linha ao final de cada transição
            }
        }

        // Armazena o resultado final em uma String
        String transicoesAgrupadas = resultado.toString();
        return transicoesAgrupadas;
    }

    public String gerador_String() {
        String arquivoString = "";
        String aux = "";
        arquivoString += "Q:";
        for (String estado : this.estados) {
            arquivoString += " " + estado;
        }

        arquivoString += "\nI:";
        for (String estado_inicial : this.Estados_iniciais) {
            arquivoString += " " + estado_inicial;
        }

        arquivoString += "\nF:";
        for (String estado_final : this.Estados_finais) {
            arquivoString += " " + estado_final;
        }
        arquivoString += "\n";
        
        for (Map.Entry<String, List<String>> entry : transicoes.entrySet()) {
            for (String transicao : entry.getValue()) {
                aux += entry.getKey()+ " ->";
                String [] transicaoSplit = transicao.split("->");
                aux += transicaoSplit[1] + " | " + transicaoSplit[0] + "\n";
            }
        }

        Menus menu = new Menus(scanner);
        String escolha = menu.Menu_Agrupa();
        
        if (escolha.equals("S")) {
            List<String> arquivoStringList = List.of(aux.split("\n"));
            arquivoString += Agrupar(arquivoStringList);
        }
        else {
            arquivoString += aux;
        }

        arquivoString += "---\n";

        menu.atraso(500);
        System.out.println(arquivoString);
        System.out.println("--------------------------------------");
        return arquivoString;
    }

    public void print_all() {
        System.out.println("------ Informações do autômato ------");
        System.out.println("Estado inicial: " + this.Estados_iniciais);
        System.out.println("Estado final: " + this.Estados_finais);
        System.out.println("Estados: " + this.estados);
        System.out.println("Alfabeto: " + this.alfabeto);
        for (Map.Entry<String, List<String>> entry : transicoes.entrySet()) {
            System.out.println("Transições do estado " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("--------------------------------------\n");
    }

}
