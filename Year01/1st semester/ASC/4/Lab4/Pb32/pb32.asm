bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dw 1111101110101110b
    b dw 1011011100011101b
    c dw 0111011110010001b
    d resb 1
    e resb 1
    f resb 1

;D=01110b+11000b=100110b
;E=11101b
;F=D-E=1001b
; our code starts here
segment code use32 class=code
    start:
        ;formam in AX nr format cu biti 0-4 din [a] apoi il adaugam la [d]
        mov AX,[a]
        and AX,0000000000011111b ;izolam bitii 0-4 ai nr [a] AX=0000 0000 0000 1110b=000Dh
        mov [d],AL ;[d]=AX=01110b=0Eh
        
        ;formam in AX nr format cu biti 5-9 din [b] apoi il adaugam la [d]
        mov AX,[b] ;AX=[b]
        and AX,0000001111100000b ;izolam biti 5-9 ai nr [b]   AX=00000001100000000b
        shr AX,5 ;shiftam spre dreapta astfel obtinand nr format din bitii 5-9 ai lui [b]
        add [d],AL ;adaugam valoarealui AL in variabila [d] astfel obtinand valoarea dorita  [d]=01110+11000=100110b=26h
        
        ;formam in AX nr format cu biti 10-14 ai lui [c] acest nr reprezentand f-ul 
        mov AX,[c] ; AX=[c]
        and AX,0111110000000000b ;izolam biti 10-14 ai nr [c] AX=0111010000000000b
        shr AX,10;shiftam spre dreapta astfel obtinand nr format din bitii 10-14 ai lui [c]
        mov [e],AL ;obtinem in [e] valoarea dorita   [e]=11101b=1Dh
        
        ;realizam scaderea dintre cele doua variabile anterior construite
        mov AL,[d] ;AL=[d]
        sub AL,[e] ;AL=[d]-[e]
        
        mov [f],AL ; AL=[f]=[d]-[e]=1001b=9h
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
