%{

#include <string.h>
#include <stdio.h>
#include <stdlib.h>

extern int yylex();
extern int yyparse();
extern FILE* yyin;
void yyerror();

%}

%token NUMBER
%token LPR
%token RPR
%token PLUS

%%

program: program PLUS program
	| program PLUS NUMBER
	| NUMBER PLUS program
	| LPR program RPR
	| NUMBER PLUS NUMBER
	| NUMBER
	;

%%

int main(int argc, char* argv[]) {
        ++argv, --argc;

    if (argc > 0)
        yyin = fopen(argv[0], "r");
    else
        yyin = stdin;

	while (!feof(yyin)) {
		yyparse();
	}

	printf("Se inchid corect\n");
	return 0;
}

void yyerror() {
	printf("Nu se inchid corect\n");
	exit(1);
}


