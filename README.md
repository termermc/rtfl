# rtfl
A not-so-helpful functional programming language

Rtfl Programming Language
by Michael Termer

Licensed under the GNU Public License v3
(http://www.gnu.org/licenses/gpl-3.0.html)

# Table of Contents
 1. General Syntax
 2. Variables
 3. Functions
 4. Built-in Functions



## 1. General Syntax

Rtfl's syntax is somewhat like that of JavaScript.

Comments can be expressed by beginning a line with //.

Calling functions is a simple as typing function_name().

Operations such as while loops can be achieved by typing

`while some_expression_that_returns_a_boolean {
  // Code
}`

The same can be done for if statements, just replace "while" with "if".

Expressions can be literals like true, false, null, undefined, void, "some string", 1, 1.0.

There are 5 types of variables in Rtfl, those being boolean, string, number, array, and null.

Boolean values can be expressed using either true or false, or a reference to a variable that contains a boolean.

String values can be expressed by some text enclosed in quotes, or a reference to a variable.

Quotes can be escaped using \, and \ can be escaped using the same character.

Numbers can be expressed either in integers such as 1 and -1, or decimals such as 1.0 and -1.0, or of course a reference to a
variable.

Null values can be expressed with null, undefined, and void, as well as a reference to a variable.


## 2. Variables

Rtfl can store any value inside of a variable. Like in JavaScript, variables are dynamically typed. To declare a
variable, simply type

 `def var_name = "some value or expression"`
 
To set a variable after it's been defined, simply use the statement above, without the "def".

To clear a variable, simply use "undef"

`undef var_name`

Doing such will clear the variable definition.

To reference a variable, all you have to do is type the name of it. For exaple:

`def a_boolean = true
if a_boolean {
	
}`

## 3. Functions

In Rtfl, you can define functions to do certain actions.

Functions can receive and return values, just like in JavaScript.

An example function declaration:

`func some_function {
	return "hello world"
}`

This function, when called, will return a string with the value "hell world". Note, returning a value is optional.

To get a parameter, simply reference the variable arg*.

So referencing arg1 will allow you to get the value of the first argument. To get the amount of parameters passed, reference arglen, which is a number.

Just like with variables, functions can be cleared by typing

`unfunc some_function`


## 4. Built-in Functions

Rtfl comes with a set of built-in functions. Below are their usage and descriptions.


print(<string>) - prints the specified string to the console

println(<string>) - same as print, except adds a newline

inc(<string>) - increments the specified variable by 1

dec(<string>) - decrements the specified variable by 1

equals(<value>, <value>) - returns whether the two parameters are equal

more_than(<number>, <number>) - returns whether the first parameter is more than the second one

less_than(<number>, <number>) - returns whether the first parameter is less than the second one

concat(<string>, <string>) - returns both provided strings joined together

var(<string>) - returns the value of the specified variable by name

to_string(<value>) - returns the string representation of the provided parameter

eval(<string>) - runs the provided code

load(<string>) - runs the specified file

add(<number>, <number>) - adds the second number to the first

sub(<number>, <number>) - subtracts the second number from the first

mul(<number>, <number>) - multiplies the first number by the second

div(<number>, <number>) - divides the first number by the second

read_file(<string>) - reads the specified file and returns its contents as a string

write_file(<string>, <string>) - writes the specified content (2nd param) to the specified file (1st param)

file_exists(<string>) - returns whether the specified file exists

not(<boolean>) - returns the opposite of the provided boolean

gc() - runs the Java garbage collector

open_terminal() - opens the terminal reader

close_terminal() - closes the terminal reader

read_terminal() - reads a line from the terminal and returns it

terminal_open() - returns whether the terminal reader is open

exit() - terminates the program

array - returns a new array

array_add(<array>, <value>) - adds the provided value to the specified array

array_remove(<array>, <number>) - removes an item in the specified array at the provided index

array_get(<array>, <number>) - gets an item in the specified array at the provided index

array_length(<array>) - returns the length of the specified array

and(<boolean>, <boolean>) - returns whether both parameters are true

or(<boolean>, <boolean>) - returns whether either of the parameters are true

split(<string>, <string>) - splits the first string into an array, splitting at the every instance of the second string

starts_with(<string>, <string>) - returns whether the first string starts with the second string

ends_with(<string>, <string>) - returns whether the first string ends with the second string

substring(<string>, <number>, <number>) - returns a sub-string of the provided string using the two provided coordinates

char_at(<string>, <number>) - returns the character at the specified coordinate

string_length(<string>) - returns the length of the specified string

array_set(<array>, <number>, <value>) - sets the value of a specific index on the provided array

type(<value>) - returns the name of the type of the provided value in string form

to_number(<string>) - converts the specified string to a number

string_replace(<string>, <string>, <string>) - replaces the string specified in the second argument with the string specified in the third, inside of the string specified in the first argument

## 4. Known Bugs

No more than two function calls can be nested into eachother.

For instance, if you were to type

`println(add(1,1))`

it would work, but if you were to type println(add(1,sub(2,1))) the interpreter would encounter an error.
