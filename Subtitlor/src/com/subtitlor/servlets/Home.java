package com.subtitlor.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.subtitlor.utilities.FileHandler;

@WebServlet("/Home")
public class Home extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public Home() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	displayFiles(request);
        this.getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
    }
    
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        Part part = request.getPart("fichier");
        FileHandler fileHandler = new FileHandler(part);
        String fileName = fileHandler.writeFile();
        request.setAttribute("fichier", fileName);
        
        displayFiles(request);
    	this.getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
    }
    
    protected void displayFiles( HttpServletRequest request ){
    	ArrayList<String> fileNames = FileHandler.getFileNames();
    	request.setAttribute("numberFiles", fileNames.size());
    	request.setAttribute("fileNames", fileNames);
    }
    
}
