package com.subtitlor.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.subtitlor.beans.Block;
import com.subtitlor.conf.SubtitlorConfiguration;
import com.subtitlor.dao.BlockDao;
import com.subtitlor.dao.DaoException;
import com.subtitlor.dao.DaoFactory;

@WebServlet("/ExportSubtitle")
public class ExportSubtitle extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	BlockDao blockDao;

	public void init() throws ServletException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.blockDao = daoFactory.getBlockDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("fileName");
		try {
			boolean fileExists = (blockDao.compte(fileName) != 0);
			if (fileExists) {
				ArrayList<Block> blocks = blockDao.recuperer(fileName);
				createFile(blocks, fileName, response);
			}
		} catch (DaoException e) {
			request.setAttribute("erreur", e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	protected void createFile(ArrayList<Block> blocks, String fileName, HttpServletResponse response) {
		response.reset();
        response.setBufferSize( SubtitlorConfiguration.TAILLE_TAMPON );
		response.setContentType("text/plain");
	    response.setHeader("Content-Disposition","attachment; filename=\"traduction_" + fileName + "\"");
	    try {
			response.getOutputStream().println(blocks.get(0).getId());
			response.getOutputStream().println(blocks.get(0).getTimeInterval());
			response.getOutputStream().println(blocks.get(0).getSubtitles());
			for (int i = 1; i < blocks.size(); i++) {
				if (blocks.get(i).getIdLine() == 0) {
					response.getOutputStream().println();
					response.getOutputStream().println(blocks.get(i).getId());
					response.getOutputStream().println(blocks.get(i).getTimeInterval());
					response.getOutputStream().println(blocks.get(i).getSubtitles());
				} else {
					response.getOutputStream().println(blocks.get(i).getSubtitles());
				}
			}
		} catch (Exception e) {
		}
	}

}
