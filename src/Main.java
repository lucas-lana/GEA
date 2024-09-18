package src;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int num_estados;
        String estado_inicial;
        String estado_final;
        List<String> alfabeto = new ArrayList<>();
        Map<String, List<String>> transicoes;

        Processos_Terminal processo = new Processos_Terminal(scanner);

        // Parte inicial do programa com tratamento de erros
        System.out.println("Bem vindo ao gerador de esquemas de autômatos finitos!");
        num_estados = processo.num_estados();

        int qtd_simbolos = processo.num_simbolos();

        for (int i = 0; i < qtd_simbolos; i++) {
            System.out.print("Digite o símbolo " + (i + 1) + ": ");
            alfabeto.add(scanner.nextLine());
        }

        transicoes = processo.transicoes(num_estados,alfabeto);
        
        estado_inicial = processo.estado_inicial();
        estado_final = processo.estado_final();
        
        System.out.println("Autômato registrado com sucesso!");
        scanner.close();
        processo.print_all(num_estados, alfabeto, estado_inicial, estado_final, transicoes);

        Maquina maquina = new Maquina(estado_inicial,estado_final,processo.get_estados(),alfabeto,transicoes);
        maquina.print_all();
    }
}