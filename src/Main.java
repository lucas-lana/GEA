package src;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<String> estado_inicial;
        List<String> estado_final;
        List<String> alfabeto = new ArrayList<>();
        Map<String, List<String>> transicoes;

        Menus menu = new Menus(scanner);
        Input_Terminal input = new Input_Terminal(scanner);

        // Parte inicial do programa com tratamento de erros
        System.out.println("\n\n--------------------------------------");
        System.out.println("Bem vindo ao gerador de esquemas de autômatos finitos!\nPara começar, registre o autômato.");
        System.out.println("--------------------------------------");
        menu.Menu_Instruções();

        input.get_estados();;
        input.get_alfabeto();
        estado_inicial = input.get_estados_inicias();
        estado_final = input.get_estados_finais();
        alfabeto = input.alfabeto;
        transicoes = input.get_transicoes();

        System.out.println("--------------------------------------");
        System.out.println("Autômato registrado com sucesso!\n");
        input.print_all(alfabeto, estado_inicial, estado_final, transicoes);

        Gerador automato = new Gerador(estado_inicial,estado_final,input.estados,alfabeto,transicoes,scanner,input.determinístico);
        //automato.print_all();
        
        Salvar_Arquivo(scanner,automato.gerador_String_AFD(),menu);
        scanner.close();
    }


    // Função para salvar o arquivo
    public static void Salvar_Arquivo(Scanner leitor, String conteudo, Menus menu) throws IOException {
        String nomeArquivo;
        String salvar;

        // Pergunta ao usuário se deseja salvar o arquivo
        while (true) {
            try {
                System.out.println("Deseja salvar o arquivo com as informações do autômato? (S/N): ");
                salvar = leitor.nextLine().trim().toUpperCase();

                // Valida a entrada
                if (salvar.equals("S") || salvar.equals("N")) {
                    break;
                } else {
                    System.out.println("Opção inválida");
                }
            } catch (Exception e) {
                System.out.println("Erro ao inserir a opção.");
                System.out.println("--------------------------------------");
            }
        }

        // Se o usuário escolher 'N', cancela a operação
        if (salvar.equals("N")) {
            System.out.println("Operação cancelada. O arquivo não foi salvo.");
            menu.Menu_Reinicia();
        }

        // Loop para escolha do nome e verificação de sobrescrita
        while (true) {
            try {
                // Pergunta o nome do arquivo ao usuário
                System.out.println("Digite o nome do arquivo (sem extensão): ");
                nomeArquivo = leitor.nextLine().trim();

                if (nomeArquivo.isEmpty()) {
                    System.out.println("Nome do arquivo inválido");
                } else {
                    nomeArquivo += ".txt";
                    System.out.println("Nome do arquivo: " + nomeArquivo);
                    System.out.println("--------------------------------------");

                    // Verifica se o arquivo já existe
                    File arquivo = new File(nomeArquivo);
                    if (arquivo.exists()) {
                        while (true) {
                            try {
                                System.out.println("O arquivo '" + nomeArquivo + "' já existe. Deseja sobrescrever? (S/N): ");
                                String sobrescrever = leitor.nextLine().trim().toUpperCase();

                                // Valida a opção de sobrescrever
                                if (sobrescrever.equals("S")) {
                                    break; // Sai do loop de sobrescrita
                                } else if (sobrescrever.equals("N")) {
                                    System.out.println("Escolha outro nome para o arquivo.");
                                    break; // Sai do loop de sobrescrita e volta para pedir o nome do arquivo
                                } else {
                                    System.out.println("Opção inválida");
                                }
                            } catch (Exception e) {
                                System.out.println("Erro ao inserir a opção.");
                                System.out.println("--------------------------------------");
                            }
                        }
                        if (arquivo.exists()) {
                            continue; // Volta para o loop de nome de arquivo se o arquivo já existir e não for sobrescrito
                        }
                    } else {
                        break; // Sai do loop de nome de arquivo se não houver conflito
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro ao inserir o nome do arquivo.");
                System.out.println("--------------------------------------");
            }
        }

        // Chama a função para salvar no arquivo
        salvarArquivo(nomeArquivo, conteudo);
    }

    // Função para salvar o arquivo
    public static void salvarArquivo(String nomeArquivo, String conteudo) {
        try (FileWriter escritor = new FileWriter(nomeArquivo)) {
            escritor.write(conteudo);
            System.out.println("Arquivo '" + nomeArquivo + "' foi salvo com sucesso!");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao salvar o arquivo: " + e.getMessage());
        }
    }
}