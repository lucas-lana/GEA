package src;
import java.util.List;
import java.util.Map;

public class Maquina {
    private String Estado_inicial;
    private String Estado_final;
    private List<String> estados;
    private List<String> alfabeto;
    private Map<String,List<String>> transicoes;

    public Maquina(String Estado_inicial, String Estado_final,List<String> estados, List<String> alfabeto, Map<String,List<String>> transicoes) {
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.Estado_inicial = Estado_inicial;
        this.Estado_final = Estado_final;
        this.transicoes = transicoes;
    }

    public String get_estado_inicial() {
        return this.Estado_inicial;
    }

    public String get_estado_final() {
        return this.Estado_final;
    }

    public List<String> get_estados() {
        return this.estados;
    }

    public List<String> get_alfabeto() {
        return this.alfabeto;
    }

    public Map<String,List<String>> get_transicoes() {
        return this.transicoes;
    }

    public void print_all() {
        System.out.println("------ Informações do autômato ------");
        System.out.println("Estado inicial: " + this.Estado_inicial);
        System.out.println("Estado final: " + this.Estado_final);
        System.out.println("Estados: " + this.estados);
        System.out.println("Alfabeto: " + this.alfabeto);
        for (Map.Entry<String, List<String>> entry : transicoes.entrySet()) {
            System.out.println("Transições do estado " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("--------------------------------------");
    }
}
