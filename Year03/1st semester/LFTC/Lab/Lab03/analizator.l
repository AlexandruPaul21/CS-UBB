%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
	char* name;
	int code;
} AtomCode;

int count = 0;
int lineNumber = 0;
AtomCode ts[100];

AtomCode atomsCode[] = {
	{"ID", 0},
	{"CONST", 1},
	{"#include<iostream>", 2},
	{"struct", 3},
	{"main", 4},
	{"using", 5},
	{"namespace", 6},
	{"std", 7},
	{"return", 8},
	{"cin", 9},
	{"cout", 10},
	{"if", 11},
	{"int", 12},
	{"char", 13},
	{"float", 14},
	{"+", 15},
    {"-", 16},
    {"!", 17},
    {"=", 18},
    {"*", 19},
    {",", 20},
    {"(", 21},
    {")", 22},
    {"{", 23},
    {"}", 24},
    {"<", 25},
    {">", 26},
    {"#", 28},
    {";", 29},
	{"include", 30},
	{"iostream", 31},
	{"[", 32},
	{"cattimp", 33},
	{"executa", 34},
	{"sfcattimp", 35},
	{">>", 36},
	{"<<", 37},
	{"/", 38},
};

int getAtomsCode(char* key) {
	for (int i = 0; i < 39; ++i) {
		if (strcmp(atomsCode[i].name, key) == 0) {
			return atomsCode[i].code;
		}
	}
}

int getCodeTs(char* key) {
	for (int i = 0; i < count; ++i) {
		if (strcmp(ts[i].name, key) == 0) {
			return ts[i].code;
		}
	}

	return -1;
}

void addToTs(char* atom) {
	int index = 0;
	
	while (index < count && strcmp(ts[index].name, atom) < 0) {
		++index;
	}

	if (index < count && strcmp(ts[index].name, atom) == 0) {
		return;
	}

	for (int i = count; i > index; --i) {
		ts[i] = ts[i - 1];
	}

	ts[index].name = malloc(strlen(atom) + 1);
	strcpy(ts[index].name, atom);
	ts[index].code = count;

	count++;
}
%}

DIGIT_0 [1-9]
DIGIT 		[0-9]
BINARY_DIGIT [01]
OCTAL_DIGIT [0-7]
HEX_DIGIT 	[0-9A-Fa-f] 
ID 			[a-zA-Z_][a-zA-Z0-9_]*
KEYWORD		"#include<iostream>"|"struct"|"main"|"using"|"namespace"|"std"|"return"|"cin"|"cout"|"if"|"int"|"char"|"float"|"include"|"iostream"|"cattimp"|"executa"|"sfcattimp"
OPERATOR	"<"|">"|"<="|">="|"=="|"!="|"+"|"-"|"*"|"/"|"="
SEPARATOR	","|";"|"{"|"}"|"<<"|">>"|"["|"]"|"<"|">"|"#"|"("|")"

%%

"\n" ++lineNumber;
" "|"\r"|"\t" ;
{KEYWORD}|{OPERATOR}|{SEPARATOR} {
	printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext));
}

0|{DIGIT_0}{DIGIT}*(\.{DIGIT}+)?|0[bB]1{BINARY_DIGIT}*|0[xX][1-9A-Fa-f]{HEX_DIGIT}+|0[1-7]{OCTAL_DIGIT}+ {
	addToTs(yytext);
	printf("Atom: %s cod: %d poz: %d\n", yytext,getAtomsCode("CONST"), getCodeTs(yytext));
}

{ID} {
	int i = getCodeTs(yytext);
	if (i == -1) {
		addToTs(yytext);
	}
	printf("Atom: %s cod: %d poz: %d\n", yytext, getAtomsCode("ID"), getCodeTs(yytext));
}

. {
	fprintf(stderr, "Error on line %d: Not accepted by MLP: %s\n", lineNumber, yytext);
	yyterminate();
}
%%

int yywrap() {}
int main(int argc, char** argv) {
	if (argc != 2) {
		fprintf(stderr, "Usage: %s <input_fike>\n", argv[0]);
		return 1;
	}

	FILE *yyin, *yyout;

	yyin = fopen(argv[1], "r");
	if (yyin == NULL) {
        perror("Error opening input file");
        return 1;
    }

	yyout = fopen("result.txt", "w");
	if (yyout == NULL) {
		perror("Error opening output file");
		return 1;
	}
	yyrestart(yyin);

    printf("---------------FIP---------------------\n");
    yylex();

    printf("---------------TS----------------------\n");
    for(int i = 0; i < count; i++){
		printf("%s %d\n", ts[i].name, ts[i].code);
    }

    fclose(yyin);
    fclose(yyout);
	return 0;
}

