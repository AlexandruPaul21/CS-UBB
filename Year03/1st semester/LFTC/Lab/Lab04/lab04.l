%{

#include "lab04.tab.h"
int currentLine = 1;

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
	{">>", 36},
	{"<<", 37},
	{"/", 38},
    {"main()", 39},
    {"return", 40},
    {"while", 41},
    {"&&", 42},
    {"||", 43}
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

void printTs() {
    for(int i = 0; i < count; i++){
		printf("%s %d\n", ts[i].name, ts[i].code);
    }
}

%}

%option noyywrap

DIGIT_0 [1-9]
DIGIT       [0-9]
BINARY_DIGIT [01]
OCTAL_DIGIT [0-7]
HEX_DIGIT   [0-9A-Fa-f]
ID          [a-zA-Z_][a-zA-Z0-9_]*

%%

include { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return INCLUDE; }
struct { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return STRUCT; }
main { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return MAIN; }
using { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return USING; }
namespace { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return NAMESPACE; }
std { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return STD; }
cin { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return ISTREAM; }
cout { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return OSTREAM; }
if { return IF; }
int|char|float { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return DATA_TYPE; }
iostream { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return LIBRARY; }
">" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return GT; }
"<" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return LT; }
"<=" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return LTE; }
">=" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return GTE; }
"==" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return EQ; }
"!=" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return NEQ; }
"+" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return PLUS; }
"-" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return MINUS; }
"*" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return OR; }
"/" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return DIVIDE; }
"=" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return ASSIGN; }
"," { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return COMMA; }
";" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return SEMICOLON; }
"{" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return LAQ; }
"}" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return RAQ; }
">>" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return SIN; }
"<<" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return SOUT; }
"[" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return RRP; }
"]" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return LRP; }
"#" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return HASHTAG; }
"(" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return LPR; }
")" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return RPR; }
"main()" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return MAIN; }
"return" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return RETURN; }
"&&" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return AND; }
"||" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return OR; }
"while" { printf("Atom: %s cod: %d\n", yytext, getAtomsCode(yytext)); return WHILE; }

{ID} { 
    int i = getCodeTs(yytext);
	if (i == -1) {
		addToTs(yytext);
	}
    printf("Atom: %s cod: %d poz: %d\n", yytext, getAtomsCode("ID"), getCodeTs(yytext));
    return ID; 
}

0|{DIGIT_0}{DIGIT}*(\.{DIGIT}+)?|0[bB]1{BINARY_DIGIT}*|0[xX][1-9A-Fa-f]{HEX_DIGIT}+|0[1-7]{OCTAL_DIGIT}+ { 
    addToTs(yytext);
	printf("Atom: %s cod: %d poz: %d\n", yytext,getAtomsCode("CONST"), getCodeTs(yytext));
    return CONST; 
}

[\n] { currentLine++; }

[ \t]+ ;

%%
