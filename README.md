# RISCompile
*A Compiler to write Assembly programs for my RISCI-64 VM*

**WARNING:**
Read my code at your own risk! This is all just hobby code that's mostly [bodged](https://en.oxforddictionaries.com/definition/bodge)
together with no thought of maintenance. I am also still learning about some of the better methods of coding. If you notice some
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
This is the Syntax for All Comments, but Block Comments don't work.

## Opcode Keywords
All RISCI-64 Opcodes have a "verbose" version

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
