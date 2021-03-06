# RISCompile
*A Compiler to write Assembly programs for my RISCI-64 VM*

**WARNING:**
Read my code at your own risk! This is all just hobby code that's mostly [bodged](https://en.oxforddictionaries.com/definition/bodge)
together with no thought of maintenance. If you notice some
odd or inefficient code, then just let me know, how I could have done it better. Thanks.

## Using RISCompile

To Compile a .ris program (These use a custom language defined below) you simple type in console:
`java -jar RISComp <filename> [destination-name]`
Having a destination name is optional, the compiler will otherwise just use the starting
filename as the compiled filenames.
If you need anymore help on using the Compiler just type:
`java -jar RISComp -h` or `java -jar RISComp --help`

## Header
Every RIS program starts with a RAM header that lets you preset values to be loaded into
RAM when the program starts. The header is simply formatted like an array, for example 
`{1, 2, 3}` loads 1, 2, and 3 into memory at addresses 0, 1, and 2
The Header can also span multiple lines to help break up large blocks of Data:
```
//ASCII values for "Hello, World!"
{72, 101, 108, 108, 111, 44, 32,
119, 111, 114, 108, 100, 21}
```
You probably also noticed The Comment.
Comments are made with a simple double slash `//`.
There are no block comments.

## Opcode Keywords
Here are the opcodes that can be used on the
RISCI-64. These are the only commands available
to be run in a ris program.

Binary | Decimal | Verbose
--- | --- | ---
00 | 0 | perform
01 | 1 | popto
10 | 2 | pushfrom
11 | 3 | jumpif

## Arguments
The arguments can be written in Decimal, Hex and Binary.
The compiler will automatically assume a number is decimal, so
to make it read them as Hex or Binary you have to give the number
a prefix:\
`0x` for Hexadecimal.\
`#` for Binary.\
E.g: `pushfrom 36`, `pushfrom 0x24`, and `pushfrom #100100` All yield the same result.

## Non-opcode Keywords
There are many non-opcode keywords for things like perform operations and
common RAM locations.\
These are all perform operations as of version 1.2.6:

Number | Keyword | Operation
--- | --- | ---
0 | nothing | Does nothing.
1 | addition | Pops top 2 values off the stack and pushes their sum back onto the stack.
2 | subtraction | Pops top 2 values off the stack and pushes their difference onto the stack.
3 | multiplication | Pops top 2 values off the stack and pushes their product onto the stack.
4 | division | Pops top 2 values off stack and pushes their quotient onto the stack.
5 | modulo | Pops top 2 values off stack and pushes the remainder onto the stack.
6 | increment | Pops top value off stack and pushes the value plus 1.
7 | decrement | Pops top value off stack and pushes the value minus 1.

And these are the system variables with keywords

Address | Keyword | Purpose
--- | --- | ---
0x2A | top | The value of the top of the stack.
0x2B | stdin | Standard input from the console.
0x2C | stdout | Standard output to the console.
0x2D | stderr | Standard error output for runtime errors, etc.
0x2F | soundout | An output byte for simple tones.
