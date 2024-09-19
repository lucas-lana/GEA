package src;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String estado_inicial;
        String estado_final;
        List<String> alfabeto = new ArrayList<>();
        Map<String, List<String>> transicoes;

        Processos_Terminal processo = new Processos_Terminal(scanner);

        // Parte inicial do programa com tratamento de erros
        System.out.println("Bem vindo ao gerador de esquemas de autômatos finitos!\nPara começar, registre o autômato.");
        System.out.println("--------------------------------------");

        processo.estados();
        processo.alfabeto();
        alfabeto = processo.alfabeto;
        transicoes = processo.transicoes();
        estado_inicial = processo.estado_inicial();
        estado_final = processo.estado_final();
        scanner.close();
        System.out.println("--------------------------------------");
        System.out.println("Autômato registrado com sucesso!\n");
        processo.print_all(alfabeto, estado_inicial, estado_final, transicoes);

        Maquina maquina = new Maquina(estado_inicial,estado_final,processo.get_estados(),alfabeto,transicoes);
        maquina.print_all();
    }
}