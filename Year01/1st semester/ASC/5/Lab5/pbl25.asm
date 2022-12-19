bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    S1 db '+','4','2','a','8','4','X','5' ; definim sirul S1
    l equ $-S1 ; determinam lungimea sirului S1
    S2 db 'a','4','5'
    r equ $-S2 ; determinam lungimea sirului S2
    D times 20 db 0 ;D: '+', '2', '8', 'X'
    ap resb 1
    cont resb 1

; our code starts here
segment code use32 class=code
    start:
        mov ECX, l  ; punem in registrul contor dimensiunea primului sir
        mov ESI, 0  ; indicele de la care incepem iterarea
        mov EDI, 0  ; indicele din sirul destinatie (D)
        jecxz sf    ; tratam cazul in care ECX e 0
        loop_start:
        
            mov AL,[S1+ESI]     ;punem in AL elementul curent 0
            mov byte[ap],0      ;initializam [ap] cu 0, intrucat ea va contoriza numarul de aparitii a lui AL in S2
            mov byte[cont],r    ;ne luam o variabila contor, intrucat ECX este ocupat, cu ea implementand o varianta proprie de loop 
            mov EBX,0           ;intializam EBX, pentru al folosi pe post de contor in noul sir 
            strt:
                
                mov DL,[S2+EBX] ;punem in DL elemntul curent
                
                cmp AL,DL       ; verificam egalitatea cu DL
                jne et
                inc byte[ap]    ; daca cele doua sunt egale, incrementam variabila ce retine numarul de aparitii
                et:
                
                inc EBX         ;incrementam variabila contor pentru sirul S2
                dec byte[cont]  ;decrementam contorul loop-ului
                cmp byte[cont],0;comparam contorul loop-ului cu 0 pentru a vedea daca mai avem de executat pasi
            jne strt
            
            cmp byte[ap],0      ;comparam numarul de aparitii cu 0 pentru a vedea daca exista caracterul cautat in S2
            jne eth
            mov [D+EDI],AL      ;in cazul in care nu exista punem caracterul in noul sir, cel destinatie, adica D
            inc EDI             ;incrementam registrul index pentru sirul D
            eth:
            inc ESI             ;incrementam registrul index pentru sirul S1
            
        loop loop_start
        sf:
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
