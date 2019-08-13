package com.subtitlor.utilities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.subtitlor.beans.Block;
import com.subtitlor.conf.SubtitlorConfiguration;

public class SubtitlesHandler {
	
	private ArrayList<Block> blocks = null;
	private boolean fileExists;

	public SubtitlesHandler(String fileName) {
		blocks = new ArrayList<Block>();
		boolean writingLines = false;
		boolean previousLineWritten = false;
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(SubtitlorConfiguration.CHEMIN_FICHIERS + fileName), "UTF8"));
			String line;
			while ((line = br.readLine()) != null) {
				if ( !writingLines ) {
					if ( isInteger(line) ) {
						Block block = new Block();
						block.setFileName(fileName);
						block.setId(Integer.parseInt(line));
						block.setIdLine(0);
						blocks.add(block);
					} else {
						writingLines = true;
						blocks.get(blocks.size() - 1).setTimeInterval(line);
					}
				} else {
					if ( line.length() != 0 && !previousLineWritten ) {
						blocks.get(blocks.size() - 1).setSubtitles(line);
						previousLineWritten = true;
					} else if ( line.length() != 0 && previousLineWritten ) {
						Block block = new Block();
						block.setFileName(fileName);
						block.setId(blocks.get(blocks.size() - 1).getId());
						block.setTimeInterval(blocks.get(blocks.size() - 1).getTimeInterval());
						block.setIdLine(blocks.get(blocks.size() - 1).getIdLine() + 1);
						block.setSubtitles(line);
						blocks.add(block);
					} else {
						writingLines = false;
						previousLineWritten = false;
					}
				}
			}
			br.close();
			fileExists = true;
		} catch (IOException e) {
			fileExists = false;
		}
	}

	public ArrayList<Block> getFileBlocks() {
		return blocks;
	}
	
	public boolean isFileExists() {
		return fileExists;
	}

	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(Exception e) { 
	        return false; 
	    }
	    return true;
	}

}
