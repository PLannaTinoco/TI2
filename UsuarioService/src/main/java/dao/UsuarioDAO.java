package dao;

// dependencias
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;

public class UsuarioDAO extends DAO 
{	
	public UsuarioDAO( ) 
	{
		super( );
		conectar( );
	} // end jogadorDAO ( )

	public void finalize( ) 
	{
		close( );
	} // end finalize
	
	public boolean insert( Usuario Usuario ) 
	{
		boolean status = false;
		try 
		{  
			Statement st = conexao.createStatement();
			String sql = "INSERT INTO Usuario (login, senha, sexo) "
				       + "VALUES ('" + Usuario.getLogin() + "', '" + Usuario.getSenha() + "', '" + Usuario.getSexo() + "');";
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch ( SQLException u ) 
		{  
			throw new RuntimeException(u);
		} // end try catch
		return ( status );
	} // end insert ( )

	public Usuario get( int codigo ) 
	{
		Usuario Usuario = null;
		
		try 
		{
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM Usuario WHERE codigo=" + codigo;
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 Usuario = new Usuario(rs.getInt("codigo"), rs.getString("login"), rs.getString("senha"), rs.getString("sexo").charAt(0));
	        }
	        st.close();
		} catch (Exception e) 
		{
			System.err.println(e.getMessage());
		} // end try catch 
		return ( Usuario );
	} // end get ( )
	
	public List<Usuario> get( ) 
	{
		return get("");
	} // end get ( )

	public List<Usuario> getOrderByCodigo( ) 
	{
		return get("codigo");		
	} // end getOrderByCodigo ( )
	
	public List<Usuario> getOrderByLogin( ) 
	{
		return get("login");		
	} // end getOrderByLogin ( )
	
	public List<Usuario> getOrderBySexo( ) 
	{
		return get("sexo");		
	} // end getOrderBySexo ( )
	
	private List<Usuario> get( String orderBy ) 
	{	
		List<Usuario> jogadores = new ArrayList<Usuario>();
		
		try 
		{
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM Usuario" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while( rs.next( ) ) 
			{	            	
	        	Usuario u = new Usuario(rs.getInt("codigo"), rs.getString("login"), rs.getString("senha"), rs.getString("sexo").charAt(0));
	            jogadores.add(u);
	        } // end whiile
	        st.close();
		} catch ( Exception e ) 
		{
			System.err.println(e.getMessage());
		} // end try catch
		return ( jogadores );
	} // end get ( )

	public List<Usuario> getSexoMasculino( ) 
	{
		List<Usuario> jogadores = new ArrayList<Usuario>();
		
		try 
		{
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM Usuario WHERE Usuario.sexo LIKE 'M'";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while( rs.next( ) ) 
			{	            	
	        	Usuario u = new Usuario( rs.getInt("codigo"), rs.getString("login"), 
							rs.getString("senha"), rs.getString("sexo").charAt(0) );
	            jogadores.add(u);
	        } // end while
	        st.close();
		} catch ( Exception e ) 
		{
			System.err.println(e.getMessage());
		} // end try catch
		return ( jogadores );
	} // end getSexoMasculino ( )
	
	public boolean update( Usuario Usuario ) 
	{
		boolean status = false;
		try 
		{  
			Statement st = conexao.createStatement();
			String sql = "UPDATE Usuario SET login = '" + Usuario.getLogin() + "', senha = '"  
				       + Usuario.getSenha() + "', sexo = '" + Usuario.getSexo() + "'"
					   + " WHERE codigo = " + Usuario.getCodigo();
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch ( SQLException u ) 
		{  
			throw new RuntimeException(u);
		} // end try catch
		return status;
	} // end update ( )
	
	public boolean delete( int codigo ) 
	{
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM Usuario WHERE codigo = " + codigo;
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) 
		{  
			throw new RuntimeException(u);
		} // end try catch 
		return ( status );
	} // end delete ( )
	
	public boolean autenticar( String login, String senha ) 
	{
		boolean resp = false;
		
		try 
		{
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM Usuario WHERE login LIKE '" + login + "' AND senha LIKE '" + senha  + "'";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			resp = rs.next();
	        st.close();
		} catch ( Exception e ) 
		{
			System.err.println(e.getMessage());
		} // end try catch 
		return resp;
	} // end autenticar ( )

} // end class