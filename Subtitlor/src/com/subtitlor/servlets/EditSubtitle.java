package com.subtitlor.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.subtitlor.beans.Block;
import com.subtitlor.dao.BlockDao;
import com.subtitlor.dao.DaoFactory;
import com.subtitlor.utilities.SubtitlesHandler;

@WebServlet("/EditSubtitle")
public class EditSubtitle extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private BlockDao blockDao;

    public void init() throws ServletException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.blockDao = daoFactory.getBlockDao();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String fileName;
		ArrayList<Block> blocks;
		ArrayList<Block> savedBlocks;
		
		fileName = request.getParameter("fileName");
		request.setAttribute("fileName", fileName); session.setAttribute("fileName", fileName);
		
		SubtitlesHandler subtitles = new SubtitlesHandler(fileName);
		if ( subtitles.isFileExists() ) {
			
			blocks = subtitles.getFileBlocks();
			request.setAttribute("blocks", blocks); session.setAttribute("blocks", blocks);
			
			try {
				boolean newFile = (blockDao.compte(fileName) == 0);
				if ( newFile ) {
					blockDao.creer(blocks);
				}
				savedBlocks = blockDao.recuperer( fileName );
				request.setAttribute("savedBlocks", savedBlocks); session.setAttribute("savedBlocks", savedBlocks);
			} catch (Exception e) {
				request.setAttribute("erreur", e.getMessage());
			}
			
		} else {
			request.setAttribute("erreur", "Fichier non trouvé");
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/edit_subtitle.jsp").forward(request, response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String fileName = (String) session.getAttribute("fileName");
		ArrayList<Block> blocks = (ArrayList<Block>) session.getAttribute("blocks");
		ArrayList<Block> savedBlocks = (ArrayList<Block>) session.getAttribute("savedBlocks");
		
		try {
			updateBlocks(savedBlocks, request);
			blockDao.enregistrer(savedBlocks);
			request.setAttribute("success", "Votre traduction a bien été enregistrée !");
		} catch (Exception e) {
			request.setAttribute("erreur", e.getMessage());
		}
		
		request.setAttribute("blocks", blocks);
		request.setAttribute("savedBlocks", savedBlocks);
		request.setAttribute("fileName", fileName);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/edit_subtitle.jsp").forward(request, response);
	}
	
	protected void updateBlocks( ArrayList<Block> savedBlocks, HttpServletRequest request ) {
		try {
			request.setCharacterEncoding("UTF-8");
			for (int i = 0; i < savedBlocks.size(); i++) {
				String traduction = request.getParameter(savedBlocks.get(i).getId() + "_" + savedBlocks.get(i).getIdLine());
				savedBlocks.get(i).setSubtitles(traduction);
			}
		} catch (UnsupportedEncodingException e) {
			request.setAttribute("erreur", e.getMessage());
		}
	}

}
