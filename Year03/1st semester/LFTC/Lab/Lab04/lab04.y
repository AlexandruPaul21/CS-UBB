%{

#include <string.h>
#include <stdio.h>
#include <stdlib.h>

extern int yylex();
extern int yyparse();
extern void printTs();
extern FILE* yyin;
extern int currentLine;
void yyerror();

%}

%token INCLUDE
%token STRUCT
%token MAIN
%token USING
%token NAMESPACE
%token STD
%token ISTREAM
%token OSTREAM
%token IF
%token DATA_TYPE
%token LIBRARY
%token LT
%token GT
%token LTE
%token GTE
%token EQ
%token NEQ
%token PLUS
%token MINUS
%token OR
%token DIVIDE
%token ASSIGN
%token COMMA
%token SEMICOLON
%token LAQ
%token RAQ
%token SIN
%token SOUT
%token RRP
%token LRP
%token HASHTAG
%token LPR
%token RPR
%token ID
%token CONST
%token RETURN
%token AND
%token WHILE

%%

program: header namespace DATA_TYPE MAIN LAQ code_block RAQ
	| header namespace user_defined_type DATA_TYPE MAIN LAQ code_block RAQ
	;

header: HASHTAG INCLUDE LT LIBRARY GT
	;

namespace: USING NAMESPACE STD SEMICOLON
	;

user_defined_type: STRUCT ID LAQ declarations RAQ SEMICOLON
	;

declarations: declare_instruction
	| declare_instruction declarations
	;

code_block: instruction_list RETURN CONST SEMICOLON
	;

instruction_list: instruction
	| instruction instruction_list
	;

instruction: io_instruction
	| declare_instruction
	| assign_instruction
	| if_instruction
	| while_instruction
	;

io_instruction: input_instruction
	| output_instruction
	;

input_instruction: ISTREAM in_operation
	;

in_operation: SIN ID SEMICOLON
	| SIN ID in_operation
	;

output_instruction: OSTREAM out_operation
	;

out_operation: SOUT any SEMICOLON
	| SOUT any out_operation
	;

declare_instruction: DATA_TYPE declaration
	;

declaration: ID SEMICOLON
	| ID COMMA declaration
	| ID RRP CONST LRP SEMICOLON
	| ID RRP CONST LRP COMMA declaration
	;

assign_instruction: ID ASSIGN operation SEMICOLON
	;

operation: any
	| any operator operation
	;

operator: PLUS
	| MINUS
	| OR
	| DIVIDE
	;

if_instruction: IF LPR condition RPR LAQ instruction_list RAQ

condition: boolean_expression
	| boolean_expression bool_op condition
	;

boolean_expression: any bool_op any
	;

any: ID
	| CONST
	;

bool_op: AND
	| OR
	;

while_instruction: WHILE LPR condition RPR LAQ instruction_list RAQ 

%%

int main(int argc, char* argv[]) {
	++argv, --argc;
    
    if (argc > 0) 
        yyin = fopen(argv[0], "r"); 
    else 
        yyin = stdin; 
    
    while (!feof(yyin)) {
		printf("---------------------------FIP--------------------------\n");
        yyparse();
    }
	printf("----------------------------TS----------------------------\n");
	printTs();
    printf("The file is sintactically correct!\n");
    return 0;
}

void yyerror() {
	extern char* yytext;
	printTs();
	printf("Error for symbol %s on line: %d\n", yytext, currentLine);
	exit(1);
}

