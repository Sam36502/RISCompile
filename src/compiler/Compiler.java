package compiler;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;

public class Compiler {
	
	public static final String VERSION = "1.2.6";
	
	//Read whole file into a string
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	//Write String to file
	public static void writeFile(String path, byte[] data) throws IOException {
		OutputStream out = new FileOutputStream(new File(path));
		out.write(data);
		out.close();
	}
	
	public static void main(String[] arguments) throws IOException {
		
		//Displays a help menu
		if (arguments.length < 1 || "--help".equals(arguments[0]) || "-h".equals(arguments[0]) || arguments.length > 2) {
			System.out.println("Usage:\n"
					+ "  java -jar RISComp.jar --help (Opens Help menu)\n"
					+ "  java -jar RISComp.jar -h		(Opens Help menu)\n"
					+ "  java -jar RISComp.jar -v		(Displays the current version)\n"
					+ "  java -jar RISComp.jar <filename> [destination-name] (Compiles the program into destination)");
			System.exit(0);
		} else if ("-v".equals(arguments[0])) { 
			System.out.println("Current Compiler version: "+VERSION);
			System.exit(0);
		}
		
		//Setting the filenames for start and destination
		String code = readFile(arguments[0], Charset.forName("ASCII"));
		String destFile = arguments[0].replaceAll(".ris", "");
		
		//If there's no destination, then make the starting filename, the destination
		if (arguments.length == 2) {
			destFile = arguments[1];
		}
		
		System.out.println("Compiling "+arguments[0]+" to "+destFile+".mem/ass...");
		
		//Create a .mem File with the preset RAM state
		if (code.contains("{")) {
			String values = code.substring(code.indexOf("{")+1, code.indexOf("}")-1);
			String[] temp = new String[64];
			temp = values.split(",");
			byte[] result = new byte[64];
			
			//Goes through the array of Strings converting them to byte
			for (int i=0; i<temp.length; i++) {
				if (temp[i].charAt(0) == '#') {
					result[i]=(byte) Integer.parseInt(temp[i].trim().substring(1, temp[i].length()), 2);
				} else if ("0x".equals(temp[i].substring(0, 1))) {
					result[i]=(byte) Integer.parseInt(temp[i].trim().substring(2, temp[i].length()), 16);
				} else {
					result[i]=(byte) Integer.parseInt(temp[i].trim());
				}
			}
			writeFile(destFile+".mem", result);
		}
		
		//Reading the File, line by line and interpreting
		Operation[] compiled = new Operation[64];
		int lineNum = 0;
		int Errcount = 0;
		String[] prog = code.split("\n");
		for (int i=0; i<prog.length; i++) {
			if (prog[i].contains("//") || prog[i].contains("{") || prog[i].contains("}") || prog[i].contains(",") || prog[i].length()<2) {
				continue;
			}
			String opc = prog[i].substring(0, prog[i].indexOf(' '));
			String arg = prog[i].substring(prog[i].indexOf(' '), prog[i].length());
			compiled[lineNum] = new Operation();
			try {
				compiled[lineNum].setOpcode(opc);
			} catch (InvalidOpcodeException e) {
				System.out.println("Line "+(i+1)+": Syntax Error; Invalid Opcode.");
				Errcount++;
			}
			try {
				compiled[lineNum].setArg(arg);
			} catch (AddressOutOfRangeException e) {
				System.out.println("Line "+(i+1)+": Address Out of Range. (1-255)");
				Errcount++;
			}
			lineNum++;
		}
		
		//Filling the Rest of program memory with, effectively, NOPs
		for (int i=lineNum; i<64; i++) {
			compiled[i] = new Operation((byte) 0);
		}
		
		//Writing Compiled code to file
		byte[] finProduct = new byte[64];
		for (int i=0; i<64; i++) {
			finProduct[i] = (byte) compiled[i].op;
		}
		System.out.println(finProduct);
		writeFile(destFile+".ass", finProduct);
		System.out.println("Build Finished with "+Errcount+" Errors.");
	}
	
}
