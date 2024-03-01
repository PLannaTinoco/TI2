package app;

import java.util.List;
import java.util.Scanner;

import dao.UsuarioDAO;
import model.Usuario;

public class Aplicacao {
    
   
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main( String[] args ) throws Exception 
    {
    	
        // variaveis
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = new Usuario();
 
        int opcao = 0;

        do
        {
            // mostrar o menu 
        	System.out.println("\n\n===== Menu =====");
            System.out.println("1. Listar usuários");
            System.out.println("2. Inserir usuário");
            System.out.println("3. Atualizar usuário");
            System.out.println("4. Excluir usuário");
            System.out.println("5. Sair");
            

            // leitura de dados
            System.out.print( "Digite uma opcao: " );
            opcao = scanner.nextInt( );

            switch( opcao )
            {
            case 1:
                listarUsuarios( usuarioDAO );
                break;
            case 2:
                inserirUsuario( usuarioDAO );
                break;
            case 3:
            	atualizarUsuario(usuarioDAO );
                break;
            case 4:
                excluirUsuario( usuarioDAO );
                break;
            case 5:
                System.out.printf( "\n%s\n" , "Programa encerrado." );
                break;

            default:
                System.out.printf( "\n%s\n" , "ERRO: Opcao Invalida" );
                break;
            } // end switch case
        }while ( opcao != 5 ); // end do while

        scanner.close();
    } // end main ( )
    
    private static void listarUsuarios(UsuarioDAO usuarioDAO) {
        List<Usuario> usuarios = usuarioDAO.get();
        for (Usuario u : usuarios) {
            System.out.println(u.toString());
        }
    }
    
    private static void inserirUsuario(UsuarioDAO usuarioDAO) {
        System.out.println("\n==== Inserir usuário ====");
        System.out.print("Informe o código: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Informe o login: ");
        String login = scanner.nextLine();
        System.out.print("Informe a senha: ");
        String senha = scanner.nextLine();
        System.out.print("Informe o sexo (M/F): ");
        char sexo = scanner.next().charAt(0);
        
        Usuario usuario = new Usuario(codigo, login, senha, sexo);
        if (usuarioDAO.insert(usuario)) {
            System.out.println("Inserção com sucesso -> " + usuario.toString());
        } else {
            System.out.println("Falha ao inserir usuário.");
        }
    }
    
    private static void atualizarUsuario(UsuarioDAO usuarioDAO) {
        System.out.println("\n==== Atualizar usuário ====");
        System.out.print("Informe o código do usuário a ser atualizado: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        Usuario usuario = usuarioDAO.get(codigo);
        if (usuario != null) {
            System.out.print("Informe o novo login: ");
            usuario.setLogin(scanner.nextLine());
            System.out.print("Informe a nova senha: ");
            usuario.setSenha(scanner.nextLine());
            System.out.print("Informe o novo sexo (M/F): ");
            usuario.setSexo(scanner.next().charAt(0));
            
            if (usuarioDAO.update(usuario)) {
                System.out.println("Usuário atualizado com sucesso.");
            } else {
                System.out.println("Falha ao atualizar usuário.");
            }
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }
    
    private static void excluirUsuario(UsuarioDAO usuarioDAO) {
        System.out.println("\n==== Excluir usuário ====");
        System.out.print("Informe o código do usuário a ser excluído: ");
        int codigo = scanner.nextInt();
        if (usuarioDAO.delete(codigo)) {
            System.out.println("Usuário excluído com sucesso.");
        } else {
            System.out.println("Falha ao excluir usuário.");
        }
    }
}