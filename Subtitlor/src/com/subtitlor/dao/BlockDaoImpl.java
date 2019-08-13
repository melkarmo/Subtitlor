package com.subtitlor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.subtitlor.beans.Block;
import com.subtitlor.conf.SubtitlorConfiguration;

public class BlockDaoImpl implements BlockDao{

	private DaoFactory daoFactory;

    BlockDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    @Override
    public void creer( ArrayList<Block> blocks ) throws DaoException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        
        try {
        	connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO " + SubtitlorConfiguration.DATABASE_TABLE + "(file_name, id_block, time_interval, id_line, subtitles) VALUES(?, ?, ?, ?, ?);");
            
            for (Block block : blocks) {
            	preparedStatement.setString(1, block.getFileName());
                preparedStatement.setInt(2, block.getId());
                preparedStatement.setString(3, block.getTimeInterval());
                preparedStatement.setInt(4, block.getIdLine());
                preparedStatement.setString(5, block.getSubtitles());
                preparedStatement.addBatch();
            }
            
            preparedStatement.executeBatch();
            connexion.commit();
        } catch (SQLException e) {
            try {
                if (connexion != null) {
                    connexion.rollback();
                }
            } catch (SQLException e2) {
            }
            throw new DaoException("Impossible de communiquer avec la base de données");
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données");
            }
        }
    }
    
	@Override
	public void enregistrer(ArrayList<Block> blocks) throws DaoException {
		Connection connexion = null;
        PreparedStatement preparedStatement = null;
        
        try {
        	connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("UPDATE " + SubtitlorConfiguration.DATABASE_TABLE + " SET subtitles = ? WHERE id = ?");
            
            for (Block block : blocks) {
            	preparedStatement.setString(1, block.getSubtitles());
                preparedStatement.setInt(2, block.getIdDb());
                preparedStatement.addBatch();
            }
            
            preparedStatement.executeBatch();
            connexion.commit();
        } catch (SQLException e) {
            try {
                if (connexion != null) {
                    connexion.rollback();
                }
            } catch (SQLException e2) {
            }
            throw new DaoException("Impossible de communiquer avec la base de données");
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données");
            }
        }
	}

    @Override
    public ArrayList<Block> recuperer(String fileName) throws DaoException {
        ArrayList<Block> blocks = new ArrayList<Block>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT id, file_name, id_block, time_interval, id_line, subtitles "
            								+ "FROM " + SubtitlorConfiguration.DATABASE_TABLE + " "
            								+ "WHERE file_name = '" + fileName + "' "
            								+ "ORDER BY id_block, id_line;");

            while (resultat.next()) {
            	int idDb = resultat.getInt("id");
            	String fileNameBis = resultat.getString("file_name");
                int idBlock = resultat.getInt("id_block");
            	String timeInterval = resultat.getString("time_interval");
                int idLine = resultat.getInt("id_line");
                String subtitles = resultat.getString("subtitles");
                
                Block block = new Block();
                block.setIdDb(idDb);
                block.setFileName(fileNameBis);
                block.setId(idBlock);
                block.setTimeInterval(timeInterval);
                block.setIdLine(idLine);
                block.setSubtitles(subtitles);
                blocks.add(block);
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la base de données");
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données");
            }
        }
        return blocks;
    }

    @Override
	public int compte(String fileName) throws DaoException {
		int compte = -1;
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT COUNT(*) AS compte FROM " + SubtitlorConfiguration.DATABASE_TABLE + " WHERE file_name = '" + fileName + "';");

            while (resultat.next()) {
            	compte = resultat.getInt("compte");
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la base de données");
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données");
            }
        }
        return compte;
	}
    
    

}
