package com.subtitlor.dao;

import java.util.ArrayList;

import com.subtitlor.beans.Block;

public interface BlockDao {
	void creer( ArrayList<Block> blocks ) throws DaoException;
	void enregistrer( ArrayList<Block> blocks ) throws DaoException;
    ArrayList<Block> recuperer( String fileName ) throws DaoException;
    int compte( String fileName ) throws DaoException;
}
