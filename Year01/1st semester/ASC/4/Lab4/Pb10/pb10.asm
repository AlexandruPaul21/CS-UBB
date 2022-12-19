bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
        ;FEDCBA9876543210
    a dw 0111100101110011b ;=7973h 1001- biti 8-11
    b db 01111111b ;=7Fh
; [b]=0111 1001=79h
; our code starts here
segment code use32 class=code
    start:
        mov AL,[b];AL=[b]
        and AL,11110000b ;zerorizam ultimii 4 biti ai lui b, ceilalti raman neschimbati AL=0111 0000b =70h
        
        shr word[a],8        ;shiftam spre drepata cu 8 pozitii continutul lui a, astfel aliniem pozitiile ce trebuie schimbate [a]=0000 0000 0111 1001= 0079H
        mov BL,byte[a]   ;selectam primul octet din a BL=[a]=0111 1001
        and BL,00001111b ;zerorizam restul continutului astfel incat raman doar cu bitii pe care vrem sa ii modificam BL=0000 1001 =09h
        
        or AL,BL         ;inlocuim bitii AL=0111 1001=79h 
        mov [b],AL
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
