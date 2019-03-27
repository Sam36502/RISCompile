package compiler;

public class Operation {
	
	public byte op = 0;
	
	public Operation() {
		this((byte) 0);
	}
	public Operation(byte c) {
		op = c;
	}
	
	public void setOpcode(String in) throws InvalidOpcodeException {
		//Adding the opcode to the top of the Byte
		in = in.toLowerCase();
		switch (in) {
			case "perform":
				break;
			case "popto":
				op+=64;
				break;
			case "pushfrom":
				op+=128;
				break;
			case "jumpif":
				op+=(64+128);
				break;
			default:
				throw new InvalidOpcodeException();
		}
	}
	
	public void setArg(String arg) throws AddressOutOfRangeException {
		
		//Converting  any other number systems to Decimal and any keywords to their correct numbers
		arg = arg.toLowerCase().trim();
		int toAdd = 0;
		if (arg.charAt(0) == '#') {
			toAdd=Integer.parseInt(arg.substring(1), 2);
		} else if ("0x".equals(arg.substring(0, 1))) {
			toAdd = Integer.parseInt(arg.substring(2), 2);
		} else if ("nothing".equals(arg) || "nop".equals(arg)) {
			toAdd = 0;
		} else if ("addition".equals(arg) || "add".equals(arg)) {
			toAdd = 1;
		} else if ("subtraction".equals(arg) || "sub".equals(arg)) {
			toAdd = 2;
		} else if ("multiplication".equals(arg) || "mul".equals(arg)) {
			toAdd = 3;
		} else if ("division".equals(arg) || "div".equals(arg)) {
			toAdd = 4;
		} else if ("modulo".equals(arg) || "mod".equals(arg)) {
			toAdd = 5;
		} else if ("increment".equals(arg) || "inc".equals(arg)) {
			toAdd = 6;
		} else if ("decrement".equals(arg) || "dec".equals(arg)) {
			toAdd = 7;
		} else if ("top".equals(arg)) {
			toAdd = 26; //This address will be read by the computer as "top of stack"
		} else if ("stdin".equals(arg)) {
			toAdd = 27; //This is the Standard Input byte
		} else if ("stdout".equals(arg)) {
			toAdd = 28; //This is standard output byte
		} else if ("stderr".equals(arg)) {
			toAdd = 29; //This is the standard error byte
		} else if ("soundout".equals(arg)) {
			toAdd = 29; //This is the sound output bytes
		} else {
			toAdd=Integer.parseInt(arg);
		}
		
		//Checking the Input number is within address range
		if (toAdd < 0 || toAdd > 63) {
			throw new AddressOutOfRangeException();
		} else {
			op+=toAdd;
		}
	}
	
}
